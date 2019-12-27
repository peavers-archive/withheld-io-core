/* Licensed under Apache-2.0 */
package io.withheld.core.repositories;

import io.withheld.core.domain.CodeFile;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface CodeFileRepository extends ReactiveMongoRepository<CodeFile, String> {

  Flux<CodeFile> findAllByProjectIdOrderByLocationAsc(String projectId);

  Mono<Void> deleteAllByProjectId(String projectId);

  @Query(value = "{ projectId : ?0}", fields = "{ location  : 1, size : 1 }")
  Flux<CodeFile> tableOfContents(String projectId);

  @Query("{'codeLines._id': ?0}")
  Mono<CodeFile> findByCodeLineId(String id);
}
