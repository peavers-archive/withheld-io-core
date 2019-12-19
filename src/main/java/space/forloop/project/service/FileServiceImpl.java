package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.domain.CodeLine;
import space.forloop.project.domain.Project;
import space.forloop.project.repositories.CodeFileRepository;
import space.forloop.project.repositories.ProjectRepository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link FileService}.
 *
 * @author Chris Turner
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final ProjectRepository projectRepository;

  private final CodeFileRepository codeFileRepository;

  @Override
  public Mono<Project> importFiles(final Project project) {

      final String directory = project.getWorkingDirectory();
      final Path dir = Paths.get(directory);

      getFilePaths(dir)
              .forEach(
                      path -> {
                          try {
                              createCodeFile(path.toPath(), project).subscribe();
                          } catch (final IOException e) {
                              e.printStackTrace();
                          }
                      });

      return projectRepository.save(project);
  }

  private Collection<File> getFilePaths(final Path directory) {

    final Collection<File> fileCollection = FileUtils.listFiles(directory.toFile(), null, true);

    fileCollection.removeIf(file -> file.getAbsolutePath().contains(".git"));
    fileCollection.removeIf(file -> file.getAbsolutePath().contains(".idea"));

    return fileCollection;
  }

    private Mono<CodeFile> createCodeFile(final Path path, final Project project) throws IOException {

        final CodeFile codeFile =
                CodeFile.builder()
                        .projectId(project.getId())
                        .location(Paths.get(project.getWorkingDirectory()).relativize(path).toString())
                        .size(Files.size(path))
                        .build();

        try {
            codeFile
                    .getCodeLines()
                    .addAll(
                            Files.readAllLines(path, StandardCharsets.UTF_8).stream()
                                    .map(line -> CodeLine.builder().body(line).build())
                                    .collect(Collectors.toList()));

            return codeFileRepository.save(codeFile);

        } catch (final IOException e) {
            log.info("code line import failed {}", e.getMessage());

      return Mono.empty();
    }
  }
}
