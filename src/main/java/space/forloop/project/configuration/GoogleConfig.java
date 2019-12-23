/* Licensed under Apache-2.0 */
package space.forloop.project.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
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

    final FileInputStream serviceAccount = new FileInputStream(firebaseKey);

    FirebaseApp.initializeApp(
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build());

    return FirebaseAuth.getInstance();
  }

  @Bean
  public Storage storage() throws IOException {

    final FileInputStream serviceAccount = new FileInputStream(firebaseKey);

    return StorageOptions.newBuilder()
        .setCredentials(ServiceAccountCredentials.fromStream(serviceAccount))
        .build()
        .getService();
  }
}
