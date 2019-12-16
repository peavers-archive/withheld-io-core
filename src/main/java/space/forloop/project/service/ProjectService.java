package space.forloop.project.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Project;

/** @author Chris Turner (chris@forloop.space) */
public interface ProjectService {

  Mono<Project> create(Project project);

  Mono<Project> patch(Project project);

  Mono<Project> findById(String challengeId);

  Flux<Project> findAll();

  Mono<Void> delete(String id);
}
