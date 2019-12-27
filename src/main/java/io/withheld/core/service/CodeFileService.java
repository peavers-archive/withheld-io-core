/* Licensed under Apache-2.0 */
package io.withheld.core.service;

import reactor.core.publisher.Mono;
import io.withheld.core.domain.CodeFile;
import io.withheld.core.domain.Comment;

/** @author Chris Turner (chris@forloop.space) */
public interface CodeFileService {

  Mono<CodeFile> findById(String codeFileId);

  Mono<CodeFile> findByIdFilterComments(String codeFileId);

  Mono<CodeFile> addComment(Comment comment);
}
