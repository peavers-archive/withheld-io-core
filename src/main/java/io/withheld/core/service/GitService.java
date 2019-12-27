/* Licensed under Apache-2.0 */
package io.withheld.core.service;

import io.withheld.core.domain.Project;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
public interface GitService {

  Mono<Project> clone(Project url);
}
