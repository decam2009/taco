package com.taco.tacocloud_spring;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class IngredientRef {
  private final String ingredient;
}
