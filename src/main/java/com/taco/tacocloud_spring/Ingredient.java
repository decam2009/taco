package com.taco.tacocloud_spring;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PUBLIC, force = true)
public class Ingredient {
  @Id
  private String id;
  private String name;
  @Enumerated (EnumType.STRING)
  private Type type;

  public enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }
}
