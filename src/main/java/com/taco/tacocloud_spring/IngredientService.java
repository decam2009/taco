package com.taco.tacocloud_spring;

public interface IngredientService {
  public Iterable <Ingredient> findAll();
  public Ingredient addIngredient (Ingredient ingredient);
}
