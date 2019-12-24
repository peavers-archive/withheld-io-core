/* Licensed under Apache-2.0 */
package space.forloop.project.utils;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import reactor.core.publisher.Mono;
import space.forloop.project.domain.FirebaseUser;
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

  /**
   * Checks if a project is under review. It is considered NOT under review only when all the
   * reviewers assigned to the project have left feedback. This is checked by collection the IDs of
   * the assigned reviews and checking if they are also present in the feedback left.
   *
   * @return true if the project is under review, otherwise false
   */
  public static boolean isUnderReview(final Project project) {

    if (CollectionUtils.isEmpty(project.getFeedback())) {
      return true;
    }

    final ArrayList<String> reviews =
        project.getReviewers().stream()
            .map(FirebaseUser::getUid)
            .collect(Collectors.toCollection(ArrayList::new));

    final ArrayList<String> feedback =
        project.getFeedback().stream()
            .map(f -> f.getFirebaseUser().getUid())
            .collect(Collectors.toCollection(ArrayList::new));

    // If arrays are different size, feedback can't be complete.
    if (reviews.size() != feedback.size()) {
      return true;
    }

    return !CollectionUtils.isEqualCollection(reviews, feedback);
  }
}
