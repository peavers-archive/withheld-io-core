/* Licensed under Apache-2.0 */
package space.forloop.project.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.Project;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@UtilityClass
public class ProjectUtils {

  public static Mono<? extends Project> setReviewStatus(final Project project) {

    if (ProjectUtils.isUnderReview(project)) {
      project.setUnderReview(true);
    } else {
      project.setUnderReview(false);
    }

    return Mono.just(project);
  }

  public boolean isUnderReview(final Project project) {

    return project.getReviewers().size()
        != project.getReviewers().stream()
            .mapToInt(
                reviewer ->
                    Math.toIntExact(
                        project.getFeedback().stream()
                            .filter(
                                feedback ->
                                    feedback
                                        .getReviewer()
                                        .getEmail()
                                        .equalsIgnoreCase(reviewer.getEmail()))
                            .count()))
            .sum();
  }

}
