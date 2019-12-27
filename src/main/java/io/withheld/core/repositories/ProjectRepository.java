/* Licensed under Apache-2.0 */
package io.withheld.core.repositories;

import io.withheld.core.domain.Project;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {

  @Query("{'reviewers.uid': ?0}")
  Flux<Project> findAllReviewersByUid(String uid);
}
