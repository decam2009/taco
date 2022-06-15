package com.taco.tacocloud_spring.web;

import com.taco.tacocloud_spring.TacoOrder;
import com.taco.tacocloud_spring.User;
import com.taco.tacocloud_spring.data.OrderRepository;
import com.taco.tacocloud_spring.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

  private final OrderRepository orderRepo;
  private final UserRepository userRepository;

  public OrderController(OrderRepository orderRepo, UserRepository userRepository) {
    this.orderRepo = orderRepo;
    this.userRepository = userRepository;
  }

  @GetMapping("/current")
  public String orderForm() {
    return "orderForm";
  }

  @PostMapping
  public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus,
                             @AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
      return "orderForm";
    }
    //tacoOrder.setUser(user);
    orderRepo.save(tacoOrder);
    log.info("Save complete" + tacoOrder);
    sessionStatus.setComplete();
    return "redirect:/";
  }
}
