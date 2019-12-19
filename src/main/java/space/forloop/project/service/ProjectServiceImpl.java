/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Project;
import space.forloop.project.repositories.CodeFileRepository;
import space.forloop.project.repositories.ProjectRepository;
import space.forloop.project.utils.AuthUtils;
import space.forloop.project.utils.ProjectUtils;

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

    return gitService.clone(project);
  }

  @Override
  public Mono<Project> patch(final Project project) {

    return projectRepository.save(project);
  }

  @Override
  public Mono<Project> findById(final String challengeId) {

    return projectRepository
        .findById(challengeId)
        .flatMap(
            project -> {
              if (ProjectUtils.isUnderReview(project)) {
                project.setUnderReview(true);
              } else {
                project.setUnderReview(false);
              }

              return Mono.just(project);
            });
  }

  @Override
  public Flux<Project> findAll() {

    return AuthUtils.getAuthentication()
        .flux()
        .flatMap(
            authentication ->
                projectRepository.findAllByReviewerEmail(authentication.getPrincipal().toString()))
        .flatMap(
            project -> {
              if (ProjectUtils.isUnderReview(project)) {
                project.setUnderReview(true);
              } else {
                project.setUnderReview(false);
              }

              return Mono.just(project);
            });
  }

  @Override
  public Mono<Void> delete(final String projectId) {

    return codeFileRepository
        .deleteAllByProjectId(projectId)
        .then(projectRepository.deleteById(projectId));
  }
}
