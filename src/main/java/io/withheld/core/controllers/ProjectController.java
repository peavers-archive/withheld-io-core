/* Licensed under Apache-2.0 */
package io.withheld.core.controllers;

import io.withheld.core.domain.Project;
import io.withheld.core.service.ProjectService;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/challenges")
@RequiredArgsConstructor
public class ProjectController {

  public final ProjectService projectService;

  @PostMapping
  public Mono<Project> create(@RequestBody final Project project)
      throws ExecutionException, InterruptedException {
    return projectService.create(project);
  }

  @PatchMapping
  public Mono<Project> patch(@RequestBody final Project project) {
    return projectService.patch(project);
  }

  @GetMapping("/{challengeId}")
  public Mono<Project> findById(@PathVariable final String challengeId) {
    return projectService.findById(challengeId);
  }

  @GetMapping
  public Flux<Project> findAll() {
    return projectService.findAll();
  }

  @DeleteMapping("/{challengeId}")
  public Mono<Void> delete(@PathVariable final String challengeId) {
    return projectService.delete(challengeId);
  }
}
