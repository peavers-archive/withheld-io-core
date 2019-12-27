/* Licensed under Apache-2.0 */
package io.withheld.core.service;

import io.withheld.core.domain.Project;
import java.util.concurrent.ExecutionException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
public interface ProjectService {

  Mono<Project> create(Project project) throws ExecutionException, InterruptedException;

  Mono<Project> patch(Project project);

  Mono<Project> findById(String projectId);

  Flux<Project> findAll();

  Mono<Void> delete(String id);
}
