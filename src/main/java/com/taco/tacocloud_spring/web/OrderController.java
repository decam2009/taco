package com.taco.tacocloud_spring.web;

import com.taco.tacocloud_spring.TacoOrder;
import com.taco.tacocloud_spring.data.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
  private final OrderRepository orderRepo;
  @Autowired
  public OrderController(OrderRepository orderRepo) {
    this.orderRepo = orderRepo;
  }

  @GetMapping("/current")
  public String orderForm() {
    return "orderForm";
  }

  @PostMapping
  public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus) {
    if (errors.hasErrors()){
      return "orderForm";
    }
    orderRepo.save(tacoOrder);
    log.info("Order submitted{}: ", tacoOrder);
    sessionStatus.setComplete();
    return "redirect:/";
  }
}
