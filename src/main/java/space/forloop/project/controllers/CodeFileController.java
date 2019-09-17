package space.forloop.project.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.repositories.CodeFileRepository;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CodeFileController {

  public final CodeFileRepository codeFileRepository;

  @GetMapping("/v1/files/{challengeId}")
  public Flux<CodeFile> findAllByProjectIdIs(@PathVariable final String challengeId) {
    return codeFileRepository.findAllByProjectIdOrderByLocationAsc(challengeId);
  }

  @PatchMapping("/v1/files/{challengeId}")
  public Mono<CodeFile> update(@RequestBody final CodeFile codeFile) {
    return codeFileRepository.save(codeFile);
  }
}
