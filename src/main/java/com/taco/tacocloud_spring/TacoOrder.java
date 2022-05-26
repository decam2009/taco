package com.taco.tacocloud_spring;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data //lombok
@Entity

public class TacoOrder implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private long id;
  private Date placedAt = new Date();

  @NotBlank (message = "Delivery name is required")
  private String deliveryName;
  @NotBlank (message = "Delivery street is required")
  private String deliveryStreet;
  @NotBlank (message = "Delivery city is required")
  private String deliveryCity;
  @NotBlank (message = "Delivery state is required")
  private String deliveryState;
  @NotBlank (message = "Delivery zip is required")
  private String deliveryZip;
  @CreditCardNumber (message = "Not a valid credit card number")
  private String ccNumber;
  @Pattern(regexp = "^(0[1-9]|1[0-2])([\\\\/])([2-9][0-9])$", message = "Must be formated as MM/YY")
  private String ccExpiration;
  @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
  private String ccCVV;

  private List<Taco> tacos = new ArrayList<>();

  public void addTaco(Taco taco) {
    this.tacos.add(taco);
  }
}
