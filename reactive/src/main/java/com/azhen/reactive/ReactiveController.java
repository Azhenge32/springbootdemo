package com.azhen.reactive;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ReactiveController {
    @Resource
    private PersonRepository personRepository;
    @Resource
    private PersonReactiveRepository personReactiveRepository;

    @GetMapping("/person1")
    public List<Person> person1() {
        System.out.println("Traditional way started");
        List <Person> people = personRepository.findAllBy();
        System.out.println("Traditional way completed");
        return people;
    }

    @GetMapping("/person2")
    public List<Person> person2() {
        System.out.println("Traditional way started");
        List <Person> people = personRepository.findAllBy();
        System.out.println("Traditional way completed");
        return people;
    }


	@GetMapping("/people")
    public Flux<Person> all(){
        return personReactiveRepository.findAll();
    }

    @GetMapping("/people/{id}")
    Mono<Person> findById(@PathVariable("id") Long id){
        return personReactiveRepository.findById(id);
    }

    @PostMapping("/person")
    Mono<Void> create(@RequestBody Publisher<Person> personStream) {
        return personReactiveRepository.saveAll(personStream).then();
    }

}
