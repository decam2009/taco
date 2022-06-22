package com.taco.tacocloud_spring.data;

import com.taco.tacocloud_spring.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
