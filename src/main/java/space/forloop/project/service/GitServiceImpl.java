package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

  private final FileService fileService;

  @Override
  public Mono<Project> clone(final Project project) {

    final File tempDirectory = getTempDirectory();

    try (final Git git =
        Git.cloneRepository().setURI(project.getSource()).setDirectory(tempDirectory).call()) {

      log.info("Git base {}", git.getRepository().getDirectory());

      return fileService.importFiles(
          Project.builder()
                  .source(project.getSource())
                  .position(project.getPosition())
                  .applicant(project.getApplicant())
                  .workingDirectory(tempDirectory.getAbsolutePath())
                  .feedback(project.getFeedback())
              .build());

    } catch (final GitAPIException e) {
      log.error("issue cloning repository {}", e.getMessage());
      return Mono.error(e);
    }
  }

  private File getTempDirectory() {
    try {
      final File file = Files.createTempDirectory(Instant.now().toString()).toFile();

      file.deleteOnExit();

      return file;

    } catch (final IOException e) {
      log.error("issue creating temp directory {}", e.getMessage());

      throw new RuntimeException(e);
    }
  }
}
