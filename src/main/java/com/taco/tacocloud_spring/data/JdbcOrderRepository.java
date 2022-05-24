package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.IngredientRef;
import com.taco.tacocloud_spring.Taco;
import com.taco.tacocloud_spring.TacoOrder;
import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JdbcOrderRepository implements OrderRepository {
  JdbcOperations jdbcOperations;

  public JdbcOrderRepository(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  @Override
  @Transactional
  public TacoOrder save(TacoOrder order) {
    PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
            new StringBuilder().append("insert into TACO_ORDER (delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, ").append("cc_number, cc_expiration, cc_cvv, placed_at)").append("values (?,?,?,?,?,?,?,?,?)").toString(),
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
    pscf.setReturnGeneratedKeys(true);
    order.setPlacedAt(new Date());
    PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(order.getDeliveryName(), order.getDeliveryStreet(),
            order.getDeliveryCity(), order.getDeliveryState(), order.getDeliveryZip(),
            order.getCcNumber(), order.getCcExpiration(), order.getCcCVV(), order.getPlacedAt()));
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcOperations.update(psc, keyHolder);
    long orderId = keyHolder.getKey().longValue();
    order.setId(orderId);
    List<Taco> tacos = order.getTacos();
    int i = 0;
    for (Taco taco : tacos) {
      saveTaco(orderId, i++, taco);
    }
    return order;
  }

  private long saveTaco(long orderId, int orderKey, Taco taco) {

    taco.setCreatedAt(new Date());
    PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("insert into TACO (name, created_at, taco_order, " +
            "taco_order_key) values (?,?,?,?)", Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);
    pscf.setReturnGeneratedKeys(true);
    PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(taco.getName(),
            taco.getCreatedAt(), orderId, orderKey));
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcOperations.update(psc, keyHolder);
    long tacoId = keyHolder.getKey().longValue();
    taco.setId(tacoId);
    saveIngredientsRefs(tacoId, taco.getIngredients());
    return tacoId;
  }

  private void saveIngredientsRefs(long tacoId, List<IngredientRef> ingredientsRefs) {
    int key = 0;
    for (IngredientRef ingredientRef : ingredientsRefs) {
      jdbcOperations.update("insert into INGREDIENT_REF (ingredient, taco, taco_key) " +
              "values (?,?,?)", ingredientRef.getIngredient(), tacoId, key++);
    }
  }
}
