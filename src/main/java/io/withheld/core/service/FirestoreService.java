/* Licensed under Apache-2.0 */
package io.withheld.core.service;

import io.withheld.core.domain.FirebaseUser;
import java.util.List;
import java.util.concurrent.ExecutionException;

/** @author Chris Turner (chris@forloop.space) */
public interface FirestoreService {

  List<FirebaseUser> findAllWithReviewGroups(List<String> groups)
      throws ExecutionException, InterruptedException;
}
