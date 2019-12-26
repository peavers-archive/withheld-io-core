/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.project.consumers.CommentConsumer;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.repositories.CodeFileRepository;
import space.forloop.project.utils.AuthUtils;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeFileServiceImpl implements CodeFileService {

  private final CodeFileRepository codeFileRepository;

  private final ProjectService projectService;

  @Override
  public Mono<CodeFile> findById(final String challengeId, final String fileId) {

    return AuthUtils.getAuthentication()
        .flatMap(
            authentication ->
                projectService
                    .findById(challengeId)
                    .flatMap(project -> codeFileRepository.findById(fileId))
                    .doOnNext(
                        codeFile ->
                            codeFile.getCodeLines().forEach(new CommentConsumer(authentication))));
  }
}
