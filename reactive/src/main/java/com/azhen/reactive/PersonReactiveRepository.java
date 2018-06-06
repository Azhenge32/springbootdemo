package com.azhen.reactive;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface PersonReactiveRepository extends ReactiveCrudRepository<Person, Long> {
	// Flux<Person> findAllBy();
}
