/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.project.consumers.CommentConsumer;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.domain.Comment;
import space.forloop.project.repositories.CodeFileRepository;
import space.forloop.project.utils.AuthUtils;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeFileServiceImpl implements CodeFileService {

  private final CodeFileRepository codeFileRepository;

  @Override
  public Mono<CodeFile> findById(final String codeFileId) {
    return this.codeFileRepository.findById(codeFileId);
  }

  @Override
  public Mono<CodeFile> findByIdFilterComments(final String codeFileId) {

    return AuthUtils.getAuthentication()
        .flatMap(
            authentication ->
                codeFileRepository
                    .findById(codeFileId)
                    .doOnNext(
                        codeFile ->
                            codeFile.getCodeLines().forEach(new CommentConsumer(authentication))));
  }

  @Override
  public Mono<CodeFile> addComment(final Comment comment) {

    return this.codeFileRepository
        .findByCodeLineId(comment.getCodeLineId())
        .flatMap(
            codeFile -> {
              codeFile.getCodeLines().stream()
                  .filter(codeLine -> codeLine.getId().equalsIgnoreCase(comment.getCodeLineId()))
                  .findFirst()
                  .ifPresent(codeLine -> codeLine.getComments().add(comment));

              return codeFileRepository.save(codeFile);
            });
  }
}
