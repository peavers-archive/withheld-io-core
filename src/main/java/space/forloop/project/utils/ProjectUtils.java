package space.forloop.project.utils;

import lombok.experimental.UtilityClass;
import space.forloop.project.domain.Project;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@UtilityClass
public class ProjectUtils {

    public Project underReview(final Project project) {

        if (project.getFeedback().size() >= 2) {
            project.setUnderReview(false);
        } else {
            project.setUnderReview(true);
        }

        return project;
    }

}
