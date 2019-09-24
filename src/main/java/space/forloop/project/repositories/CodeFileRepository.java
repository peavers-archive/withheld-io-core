package space.forloop.project.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;

/** @author Chris Turner (chris@forloop.space) */
@Repository
public interface CodeFileRepository extends ReactiveMongoRepository<CodeFile, String> {

  Flux<CodeFile> findAllByProjectIdOrderByLocationAsc(String projectId);

  Mono<Void> deleteAllByProjectId(String projectId);

}
