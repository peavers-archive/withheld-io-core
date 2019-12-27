/* Licensed under Apache-2.0 */
package io.withheld.core.controllers;

import io.withheld.core.domain.CodeFile;
import io.withheld.core.repositories.CodeFileRepository;
import io.withheld.core.service.CodeFileService;
import io.withheld.core.service.ProjectService;
import io.withheld.core.utils.ProjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class CodeFileController {

  public final CodeFileRepository codeFileRepository;

  private final CodeFileService codeFileService;

  private final ProjectService projectService;

  @GetMapping("/{challengeId}")
  public Flux<CodeFile> findAllByProjectIdIs(@PathVariable final String challengeId) {

    return codeFileRepository.findAllByProjectIdOrderByLocationAsc(challengeId);
  }

  @GetMapping("/{challengeId}/{codeFileId}")
  public Mono<CodeFile> findById(
      @PathVariable final String challengeId, @PathVariable final String codeFileId) {

    return projectService
        .findById(challengeId)
        .flatMap(
            project ->
                ProjectUtils.isUnderReview(project)
                    ? codeFileService.findByIdFilterComments(codeFileId)
                    : codeFileService.findById(codeFileId));
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