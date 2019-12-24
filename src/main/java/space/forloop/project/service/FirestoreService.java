/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import space.forloop.project.domain.FirebaseUser;

/** @author Chris Turner (chris@forloop.space) */
public interface FirestoreService {

  List<FirebaseUser> findAllWithReviewGroups(List<String> groups)
      throws ExecutionException, InterruptedException;
}
