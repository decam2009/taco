package com.taco.tacocloud_spring.web.api;

import com.taco.tacocloud_spring.Ingredient;
import com.taco.tacocloud_spring.data.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin (origins = "http://localhost:9090")

public class IngredientController {

  private IngredientRepository repo;

  @Autowired
  public IngredientController(IngredientRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public Iterable<Ingredient> allIngredients(){
    return repo.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Ingredient saveIngredient (@RequestBody Ingredient ingredient){
    return repo.save(ingredient);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void deleteingredient (@PathVariable ("id") String ingredientId){
    repo.deleteById(ingredientId);
  }

}
