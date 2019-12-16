package space.forloop.project.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Reviewer;
import space.forloop.project.repositories.ReviewerRepository;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/reviewers")
@RequiredArgsConstructor
public class ReviewerController {

  public final ReviewerRepository reviewerRepository;

  @PostMapping
  public Mono<Reviewer> create(@RequestBody final Reviewer reviewer) {
    return reviewerRepository.save(reviewer);
  }

  @GetMapping
  public Flux<Reviewer> findAll() {
    return reviewerRepository.findAll();
  }

  @GetMapping("/{reviewerId}")
  public Mono<Reviewer> findById(@PathVariable final String reviewerId) {
    return reviewerRepository.findById(reviewerId);
  }

  @DeleteMapping("/{reviewerId}")
  public Mono<Void> deleteById(@PathVariable final String reviewerId) {
    return reviewerRepository.deleteById(reviewerId);
  }
}
