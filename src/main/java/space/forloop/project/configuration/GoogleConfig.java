/* Licensed under Apache-2.0 */
package space.forloop.project.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleConfig {

  @Value("${firebase.key}")
  public String firebaseKey;

  @Bean
  public FirebaseAuth firebaseAuth() throws IOException {

    FirebaseApp.initializeApp(
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(new FileInputStream(firebaseKey)))
            .build());

    return FirebaseAuth.getInstance();
  }

  @Bean
  public Firestore firestore() throws IOException {

    return FirestoreOptions.newBuilder()
        .setCredentials(GoogleCredentials.fromStream(new FileInputStream(firebaseKey)))
        .build()
        .getService();
  }

  @Bean
  public Storage cloudStorage() throws IOException {

    return StorageOptions.newBuilder()
        .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(firebaseKey)))
        .build()
        .getService();
  }
}
