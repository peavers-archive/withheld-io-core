package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Comment;
import space.forloop.project.domain.Project;
import space.forloop.project.domain.Reviewer;
import space.forloop.project.repositories.CodeFileRepository;
import space.forloop.project.repositories.ProjectRepository;

import java.io.File;
import java.io.IOException;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  private final CodeFileRepository codeFileRepository;

  private final GitService gitService;

  @Override
  public Mono<Project> create(final Project project) {

    log.info("creating {}", project);

    return gitService
        .clone(project)
        .doOnNext(
            p -> {
              try {
                log.info("deleting working directory {}", p.getWorkingDirectory());
                FileUtils.deleteDirectory(new File(p.getWorkingDirectory()));
              } catch (final IOException e) {
                log.error("deleting working directory failed {}", e.getMessage());
              }
            });
  }

  @Override
  public Mono<Project> patch(final Project project) {

    return projectRepository.save(underReview(project));
  }

  @Override
  public Mono<Project> findById(final String id) {

    return projectRepository.findById(id).flatMap(project -> Mono.just(underReview(project)));
  }

  @Override
  public Flux<Comment> getProjectComments(final String id) {
    return codeFileRepository
        .findAllByProjectIdOrderByLocationAsc(id)
        .flatMap(codeFile -> Flux.fromIterable(codeFile.getCodeLines()))
        .flatMap(codeLine -> Flux.fromIterable(codeLine.getComments()));
  }

  @Override
  public Flux<Reviewer> getProjectReviewers(final String id) {

    return getProjectComments(id)
            .groupBy(Comment::getReviewer)
            .flatMap(
                    commentGroupedFlux ->
                            commentGroupedFlux.reduce(
                                    (a, b) ->
                                            a.getReviewer().getEmail().compareTo(b.getReviewer().getEmail()) > 0
                                                    ? a
                                                    : b))
            .map(Comment::getReviewer);
  }

  @Override
  public Flux<Project> findAll() {

    return projectRepository.findAllByOrderByCreatedDesc();
  }

  @Override
  public Mono<Void> delete(final String projectId) {

    return codeFileRepository
            .deleteAllByProjectId(projectId)
            .then(projectRepository.deleteById(projectId));
  }

  private Project underReview(final Project project) {

    if (project.getFeedback().size() >= 2) {
      project.setUnderReview(false);
    } else {
      project.setUnderReview(true);
    }

    return project;
  }

}
