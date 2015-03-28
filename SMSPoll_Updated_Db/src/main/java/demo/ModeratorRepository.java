package demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import entities.Moderator;

@Component
public interface ModeratorRepository extends MongoRepository<Moderator, String> {

}
