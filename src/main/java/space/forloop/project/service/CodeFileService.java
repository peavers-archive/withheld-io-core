package space.forloop.project.service;

import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;

/**
 * @author Chris Turner (chris@forloop.space)
 */
public interface CodeFileService {

    Mono<CodeFile> findById(String fileId, String challengeId, String reviewerId);

}
