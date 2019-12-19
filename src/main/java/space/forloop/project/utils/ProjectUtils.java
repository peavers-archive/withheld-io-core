/* Licensed under Apache-2.0 */
package space.forloop.project.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import space.forloop.project.domain.Project;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@UtilityClass
public class ProjectUtils {

  public boolean isUnderReview(final Project project) {

    final int reviewsComplete =
        project.getReviewers().stream()
            .mapToInt(
                reviewer ->
                    (int)
                        project.getFeedback().stream()
                            .filter(
                                feedback ->
                                    feedback
                                        .getReviewer()
                                        .getEmail()
                                        .equalsIgnoreCase(reviewer.getEmail()))
                            .count())
            .sum();

    return reviewsComplete != project.getFeedback().size();
  }
}
