package space.forloop.project.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Project;
import space.forloop.project.service.ProjectService;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ProjectController {

  public final ProjectService projectService;

  @PostMapping("/v1/challenges")
  public Mono<Project> create(@RequestBody final Project project) {
    return projectService.create(project);
  }

  @GetMapping("/v1/challenges/{challengeId}")
  public Mono<Project> findById(@PathVariable final String challengeId) {
    return projectService.findById(challengeId);
  }

  @GetMapping("/v1/challenges")
  public Flux<Project> findAll() {
    return projectService.findAll();
  }

  @DeleteMapping("/v1/challenges/{challengeId}")
  public Mono<Void> delete(@PathVariable final String challengeId) {
    return projectService.delete(challengeId);
  }
}
