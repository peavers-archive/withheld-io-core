/* Licensed under Apache-2.0 */
package space.forloop.project.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirestoreConfig {

  @Value("${firebase.key}")
  public String firebaseKey;

  @Bean
  public FirebaseAuth firebaseAuth() throws IOException {

    final FileInputStream serviceAccount = new FileInputStream(firebaseKey);

    final FirebaseOptions options =
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    FirebaseApp.initializeApp(options);

    return FirebaseAuth.getInstance();
  }
}
