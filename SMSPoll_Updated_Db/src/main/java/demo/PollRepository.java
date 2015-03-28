package demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import entities.Moderator;
import entities.Poll;

@Component
public interface PollRepository extends MongoRepository<Poll, String> {

}
