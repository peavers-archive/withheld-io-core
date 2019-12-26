/* Licensed under Apache-2.0 */
package space.forloop.project.controllers;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.domain.Comment;
import space.forloop.project.repositories.CodeFileRepository;
import space.forloop.project.service.CodeFileService;
import space.forloop.project.utils.AuthUtils;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class CodeFileController {

  public final CodeFileRepository codeFileRepository;

  private final CodeFileService codeFileService;

  @GetMapping("/{challengeId}")
  public Flux<CodeFile> findAllByProjectIdIs(@PathVariable final String challengeId) {

    return codeFileRepository.findAllByProjectIdOrderByLocationAsc(challengeId);
  }

  @GetMapping("/{challengeId}/{fileId}")
  public Mono<CodeFile> findById(
      @PathVariable final String challengeId, @PathVariable final String fileId) {

    return AuthUtils.getAuthentication()
        .flatMap(
            authentication ->
                codeFileService
                    .findById(fileId, challengeId)
                    .flatMap(
                        codeFile -> {
                          codeFile
                              .getCodeLines()
                              .forEach(
                                  codeLine -> {
                                    final ArrayList<Comment> comments =
                                        codeLine.getComments().stream()
                                            .filter(
                                                comment ->
                                                    comment
                                                        .getFirebaseUser()
                                                        .getUid()
                                                        .equalsIgnoreCase(
                                                            authentication
                                                                .getPrincipal()
                                                                .toString()))
                                            .collect(Collectors.toCollection(ArrayList::new));
                                    codeLine.setComments(comments);
                                  });

                          return Mono.justOrEmpty(codeFile);
                        }));
  }

  @PatchMapping("/{challengeId}")
  public Mono<CodeFile> update(
      @RequestBody final CodeFile codeFile, @PathVariable final String challengeId) {

    return codeFileRepository.save(codeFile);
  }

  @GetMapping("/{challengeId}/toc")
  public Flux<CodeFile> tableOfContent(@PathVariable final String challengeId) {

    return this.codeFileRepository.tableOfContents(challengeId);
  }
}
