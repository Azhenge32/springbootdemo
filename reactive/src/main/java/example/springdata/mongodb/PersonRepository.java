package example.springdata.mongodb;

import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface PersonRepository extends ReactiveMongoRepository<Person, Long> {

}
