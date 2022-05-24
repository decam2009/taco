package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.Ingredient;
import com.taco.tacocloud_spring.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
  private final JdbcTemplate jdbcTemplate;

  public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Iterable<Ingredient> findAll() {
    return jdbcTemplate.query("select id, name, type from Ingredient", this::mapRowToIngredient);
  }

  private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
    return new Ingredient(row.getString("id"),
            row.getString("name"),
            Type.valueOf(row.getString("type")));
  }

  @Override
  public Optional<Ingredient> findById(String id) {
    List<Ingredient> result = jdbcTemplate.query("select id, name, type from Ingredient where id=?",
            this::mapRowToIngredient, id);
    return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
  }

  @Override
  public Ingredient save(Ingredient ingredient) {
    jdbcTemplate.update("insert into Ingredient (id, name, type) values (?,?,?)",
            ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
    return ingredient;
  }
}
