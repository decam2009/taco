package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.TacoOrder;

public interface OrderRepository {
  TacoOrder save (TacoOrder order);
}
