package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
  Iterable<Ingredient> findAll();

  Optional<Ingredient> findById(String id);

  Ingredient save(Ingredient ingredient);

}
