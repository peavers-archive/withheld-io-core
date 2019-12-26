/* Licensed under Apache-2.0 */
package space.forloop.project.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.domain.Comment;
import space.forloop.project.service.CodeFileService;

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
