package com.taco.tacocloud_spring.web.api;

import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.taco.tacocloud_spring.Taco;
import com.taco.tacocloud_spring.TacoOrder;
import com.taco.tacocloud_spring.data.OrderRepository;
import com.taco.tacocloud_spring.data.TacoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/tacos", produces = {"application/json", "text/xml"}) //Обрабатывает запросы по пути
@CrossOrigin(origins = "http://tacocloud:9090") // разрешает обработку межсайтовых запросов
public class TacoController {

  private TacoRepository tacoRep;
  private OrderRepository orderRepo;

  public TacoController(TacoRepository tacoRep, OrderRepository orderRepo) {
    this.tacoRep = tacoRep;
    this.orderRepo = orderRepo;
  }

  @GetMapping(params = "recent")
  public Iterable<Taco> recentTacos() {
    PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
    return tacoRep.findAll(page).getContent();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRep.findById(id);
    if (optTaco.isPresent()) {
      return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @PostMapping(consumes = "application/json")
//Атрибут consumes определяет формат входных данных в запросе, а produces – формат возвращаемых клиенту данных.
  @ResponseStatus(HttpStatus.CREATED)
  public Taco postTaco(@RequestBody Taco taco) {
    return tacoRep.save(taco);
  }

  @PatchMapping(path = "/{orderId}", consumes = "application/json")
  public TacoOrder patchOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrder patch) {
    TacoOrder order = orderRepo.findById(orderId).get();
    if (patch.getDeliveryName() != null) {
      order.setDeliveryName(patch.getDeliveryName());
    }
    if (patch.getDeliveryStreet() != null) {
      order.setDeliveryStreet(patch.getDeliveryStreet());
    }
    if (patch.getDeliveryCity() != null) {
      order.setDeliveryCity(patch.getDeliveryCity());
    }
    if (patch.getDeliveryStreet() != null) {
      order.setDeliveryStreet(patch.getDeliveryStreet());
    }
    if (patch.getDeliveryZip() != null) {
      order.setDeliveryZip(patch.getDeliveryZip());
    }
    if (patch.getCcNumber() != null) {
      order.setCcNumber(patch.getCcNumber());
    }
    if (patch.getCcExpiration() != null) {
      order.setCcExpiration(patch.getCcExpiration());
    }
    if (patch.getCcCVV() != null) {
      order.setCcCVV(patch.getCcCVV());
    }
    return orderRepo.save(order);
  }

  @DeleteMapping("/{orderId}")
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void deleteOrder(@PathVariable("orderId") Long id) {
    try {
      orderRepo.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
    }
  }
}
