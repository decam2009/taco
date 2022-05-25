package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
  //extends Repository<Ingredient, String> Первый параметр – это тип объектов,
  // которые будут храниться в репозитории, в данном случае Ingredient.
  // Второй параметр – это тип поля идентификатора хранимого объекта.
  //Помимо Repository, наследования которого вполне достаточно для нормальной работы
  // IngredientRepository, Spring Data предлагает также интерфейс CrudRepository,
  // реализующий, кроме всего прочего, три метода, которые мы определили в IngredientRepository
  //Более того, поскольку Spring Data автоматически создает реализации интерфейсов
  // во время выполнения, вам больше не нужны явные реализации в JdbcIngredientRepository
  // и JdbcOrderRepository. Вы мо- жете удалить эти два класса и навсегда забыть про них!

 /* Iterable<Ingredient> findAll();

  Optional<Ingredient> findById(String id);

  Ingredient save(Ingredient ingredient);
*/
}
