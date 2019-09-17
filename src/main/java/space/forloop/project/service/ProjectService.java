package space.forloop.project.service;

import space.forloop.project.domain.Project;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
public interface ProjectService {

  Mono<Project> create(Project project);

  Mono<Project> findById(String id);

  Flux<Project> findAll();

  Mono<Void> delete(String id);
}
