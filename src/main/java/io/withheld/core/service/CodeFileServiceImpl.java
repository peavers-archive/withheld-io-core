/* Licensed under Apache-2.0 */
package io.withheld.core.service;

import io.withheld.core.consumers.CommentConsumer;
import io.withheld.core.domain.CodeFile;
import io.withheld.core.domain.Comment;
import io.withheld.core.repositories.CodeFileRepository;
import io.withheld.core.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
