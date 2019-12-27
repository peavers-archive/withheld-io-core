/* Licensed under Apache-2.0 */
package io.withheld.core.controllers;

import io.withheld.core.domain.CodeFile;
import io.withheld.core.domain.Comment;
import io.withheld.core.service.CodeFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CodeFileService codeFileService;

  @PostMapping
  public Mono<CodeFile> create(@RequestBody final Comment comment) {
    return codeFileService.addComment(comment);
  }
}
