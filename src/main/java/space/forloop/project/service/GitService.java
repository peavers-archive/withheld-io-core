/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import reactor.core.publisher.Mono;
import space.forloop.project.domain.Project;

/** @author Chris Turner (chris@forloop.space) */
public interface GitService {

  Mono<Project> clone(Project url);
}
