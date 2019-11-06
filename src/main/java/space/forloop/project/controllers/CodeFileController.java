package space.forloop.project.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.domain.Comment;
import space.forloop.project.repositories.CodeFileRepository;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CodeFileController {

  public final CodeFileRepository codeFileRepository;

  @GetMapping("/v1/files/{challengeId}/{fileId}")
  public Mono<CodeFile> findById(
      @PathVariable final String challengeId, @PathVariable final String fileId) {
    return codeFileRepository.findById(fileId);
  }

  @GetMapping("/v1/files/{challengeId}/{fileId}/reviewers")
  public Flux<Comment> reviewers(
      @PathVariable final String challengeId, @PathVariable final String fileId) {

    return codeFileRepository
        .findById(fileId)
        .flux()
        .flatMap(codeFile -> Flux.fromIterable(codeFile.getCodeLines()))
        .flatMap(codeLine -> Flux.fromIterable(codeLine.getComments()))
        .groupBy(Comment::getAuthor)
        .flatMap(
            commentGroupedFlux ->
                commentGroupedFlux.reduce(
                    (a, b) ->
                        a.getAuthor().getEmail().compareTo(b.getAuthor().getEmail()) > 0 ? a : b));
  }

  @GetMapping("/v1/files/{challengeId}/toc")
  public Flux<CodeFile> tableOfContent(@PathVariable final String challengeId) {
    return codeFileRepository.tableOfContents(challengeId);
  }

  @PatchMapping("/v1/files/{challengeId}")
  public Mono<CodeFile> update(@RequestBody final CodeFile codeFile) {
    return codeFileRepository.save(codeFile);
  }
}
