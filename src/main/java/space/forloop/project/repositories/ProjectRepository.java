package space.forloop.project.repositories;

import space.forloop.project.domain.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {

  Flux<Project> findAllByOrderByCreatedDesc();
}
