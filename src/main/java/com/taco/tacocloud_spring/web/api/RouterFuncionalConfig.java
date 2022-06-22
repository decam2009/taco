package com.taco.tacocloud_spring.web.api;

import com.taco.tacocloud_spring.Taco;
import com.taco.tacocloud_spring.data.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFuncionalConfig {

  @Autowired
  private TacoRepository tacoRepository;

  @Bean
  public RouterFunction<?> routerFunction() {
    return route(GET("/api/tacos").
            and(queryParam("recent", t -> t != null)), this::recent)
            .andRoute(POST("/api/tacos"), this::postTaco);
  }

  private Mono<ServerResponse> postTaco(ServerRequest request) {
    return request.bodyToMono(Taco.class).flatMap(taco -> tacoRepository.save(taco))
            .flatMap(savedTaco ->
            {
              return ServerResponse.created(URI.create("http://localhost:8080/api/tacos/" + savedTaco.getId())).body(savedTaco, Taco.class);
            });
  }

  private Mono<ServerResponse> recent(ServerRequest request) {
    return ServerResponse.ok().body(tacoRepository.findAll().take(12), Taco.class);
  }


}
