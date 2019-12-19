package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.repositories.CodeFileRepository;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeFileServiceImpl implements CodeFileService {

  private final CodeFileRepository codeFileRepository;

  private final ProjectService projectService;

  @Override
  public Mono<CodeFile> findById(final String fileId, final String challengeId) {

    return projectService
            .findById(challengeId)
            .flatMap(project -> codeFileRepository.findById(fileId));
  }
}
