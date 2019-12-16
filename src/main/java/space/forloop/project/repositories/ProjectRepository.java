package space.forloop.project.repositories;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import space.forloop.project.domain.Project;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {

  @Query("{'reviewers.email': ?0}")
  Flux<Project> findAllByReviewerEmail(String email);
}
