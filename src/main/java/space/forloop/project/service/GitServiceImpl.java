/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Project;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

  private final FileService fileService;

  @Override
  public Mono<Project> clone(final Project project) {

    try {
      final File workDirectory = Files.createTempDirectory(Instant.now().toString()).toFile();
      workDirectory.deleteOnExit();

      Git.cloneRepository().setURI(project.getSource()).setDirectory(workDirectory).call().close();

      project.setWorkingDirectory(workDirectory.getAbsolutePath());

      return fileService.importFiles(project);

    } catch (final GitAPIException | IOException e) {
      log.error("issue cloning repository {}", e.getMessage());
      return Mono.error(e);
    }
  }
}
