package com.taco.tacocloud_spring.web;

import com.taco.tacocloud_spring.data.IngredientRepository;
import com.taco.tacocloud_spring.Ingredient;
import com.taco.tacocloud_spring.Taco;
import com.taco.tacocloud_spring.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.taco.tacocloud_spring.Ingredient;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j //Lombok автоматически генерирует стат свойство типа Logger (простой интерфейс журналирования)
@Controller //идентифицирует класс как Controller
@RequestMapping("/design") //определяет тип запросов, которые обрабатывает контроллер
@SessionAttributes("tacoOrder") //Она указывает, что объект TacoOrder, объявленный в классе
// чуть ниже, должен поддерживаться на уровне сеанса.
// Это важно, потому что создание тако также является первым шагом в создании заказа,
// и созданный нами заказ необходимо будет перенести в сеанс, охватывающий несколько запросов.

public class DesignTacoController{

  private final IngredientRepository ingredientRepo;
  @Autowired
  public DesignTacoController(IngredientRepository ingredientRepo) {
    this.ingredientRepo = ingredientRepo;
  }

  @ModelAttribute
  public void addIngredientsToModel(Model model)
  //Model – это объект, в котором данные пересылаются между контроллером и любым
  // представлением, ответственным за преобразование этих данных в размет- ку HTML.
  {
    Iterable<Ingredient> ingredients = ingredientRepo.findAll();
   //  List<Ingredient> ingredients = new ArrayList<>();
   // ingredientRepo.findAll().forEach(ingredients::add);

    Ingredient.Type[] types = Ingredient.Type.values();
    for (Ingredient.Type type : types) {
      model.addAttribute(type.toString().toLowerCase(), filterByType((List<Ingredient>) ingredients, type));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @GetMapping  //Аннотация @GetMapping в сочетании с @RequestMapping на уровне
  // который должен вызываться для обработки HTTP-запроса GET с путем /design.
  public String showDesignForm() {
    return "design";
  }

  private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
    return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
  }

  @PostMapping
  public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
    if (errors.hasErrors()) {
      return "design";
    }
    tacoOrder.addTaco(taco);
    log.info("Processing taco {}: ", taco);
    return "redirect:/orders/current";
  }
}
