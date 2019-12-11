package space.forloop.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.CodeFile;
import space.forloop.project.repositories.CodeFileRepository;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeFileServiceImpl implements CodeFileService {

    private final CodeFileRepository codeFileRepository;

    private final ProjectService projectService;

    @Override
    public Mono<CodeFile> findById(
            final String fileId, final String challengeId, final String reviewerId) {

        return projectService
                .findById(challengeId)
                .flatMap(
                        project ->
                                project.isUnderReview()
                                        ? showOnlyCallerComments(fileId, reviewerId)
                                        : codeFileRepository.findById(fileId));
    }

    private Mono<? extends CodeFile> showOnlyCallerComments(
            final String fileId, final String reviewerId) {

        return this.codeFileRepository
                .findById(fileId)
                .flatMap(
                        codeFile -> {
                            codeFile
                                    .getCodeLines()
                                    .forEach(
                                            codeLine ->
                                                    codeLine.setComments(
                                                            new ArrayList<>(
                                                                    codeLine.getComments().stream()
                                                                            .filter(
                                                                                    comment ->
                                                                                            comment
                                                                                                    .getReviewer()
                                                                                                    .getId()
                                                                                                    .equalsIgnoreCase(reviewerId))
                                                                            .collect(Collectors.toList()))));

                            return Mono.just(codeFile);
                        });
    }

}
