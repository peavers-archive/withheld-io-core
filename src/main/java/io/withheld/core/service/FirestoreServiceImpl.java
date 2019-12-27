/* Licensed under Apache-2.0 */
package io.withheld.core.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.withheld.core.domain.FirebaseUser;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class FirestoreServiceImpl implements FirestoreService {

  private final Firestore firestore;

  @Override
  public List<FirebaseUser> findAllWithReviewGroups(final List<String> groups)
      throws ExecutionException, InterruptedException {

    final CollectionReference collectionReference = firestore.collection("users");
    final ApiFuture<QuerySnapshot> reviewGroup =
        collectionReference.whereArrayContainsAny("reviewGroup", groups).get();

    return reviewGroup.get().getDocuments().stream()
        .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(FirebaseUser.class))
        .collect(Collectors.toList());
  }
}
