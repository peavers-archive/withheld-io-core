package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Author;
import space.forloop.project.domain.Project;
import space.forloop.project.utils.MD5Util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Objects;

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

      final Author author = getAuthorDetails();
      final Repository repositoryDetails = getRepositoryDetails(project);

      return fileService.importFiles(
          Project.builder()
              .author(author)
              .repository(repositoryDetails)
              .source(project.getSource())
              .level(project.getLevel())
              .position(project.getPosition())
              .lever(project.getLever())
              .workingDirectory(tempDirectory.getAbsolutePath())
              .build());

    } catch (final GitAPIException e) {
      log.error("issue cloning repository {}", e.getMessage());
      return Mono.error(e);
    }
  }

  private Repository getRepositoryDetails(final Project project) {

    final String[] details =
        Objects.requireNonNull(formatGithubUrlForApi(project.getSource())).split("/");

    final RepositoryService repositoryService = new RepositoryService();

    try {
      return repositoryService.getRepository(details[0], details[1]);

    } catch (final IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  private Author getAuthorDetails() {
    final String name = RandomStringUtils.random(6, "123456789");
    final String avatar =
        "https://www.gravatar.com/avatar/" + MD5Util.md5Hex(name) + "?s=64&d=identicon&r=PG";

    return Author.builder().avatarUrl(avatar).name(name).build();
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

  private String formatGithubUrlForApi(final String url) {
    try {
      final String path = new URI(url).getPath();

      return StringUtils.trimLeadingCharacter(path, '/');
    } catch (final URISyntaxException e) {
      log.warn("invalid string ({}). Should be full Github url.", url);
    }

    return null;
  }
}
