package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Author;
import space.forloop.project.domain.Comment;
import space.forloop.project.domain.Project;
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

    if (!project.getSource().startsWith("https://github.com/")) {
      return Mono.error(new UnsupportedOperationException("Only GitHub is supported right now"));
    }

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
    return projectRepository.save(project);
  }

  @Override
  public Mono<Project> findById(final String id) {
    return projectRepository.findById(id);
  }

  @Override
  public Flux<Comment> getProjectComments(final String id) {
    return codeFileRepository
        .findAllByProjectIdOrderByLocationAsc(id)
        .flatMap(codeFile -> Flux.fromIterable(codeFile.getCodeLines()))
        .flatMap(codeLine -> Flux.fromIterable(codeLine.getComments()));
  }

  @Override
  public Flux<Author> getProjectReviewers(final String id) {
    return getProjectComments(id)
        .groupBy(Comment::getAuthor)
        .flatMap(
            commentGroupedFlux ->
                commentGroupedFlux.reduce(
                    (a, b) ->
                        a.getAuthor().getEmail().compareTo(b.getAuthor().getEmail()) > 0 ? a : b))
        .map(Comment::getAuthor);
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
}
