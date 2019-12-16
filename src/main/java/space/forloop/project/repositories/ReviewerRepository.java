package space.forloop.project.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import space.forloop.project.domain.Reviewer;

/** @author Chris Turner (chris@forloop.space) */
public interface ReviewerRepository extends ReactiveMongoRepository<Reviewer, String> {}
