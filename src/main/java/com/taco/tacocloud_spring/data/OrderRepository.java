package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
  /*TacoOrder save (TacoOrder order);*/
}
