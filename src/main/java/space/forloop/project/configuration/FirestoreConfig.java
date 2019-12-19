package space.forloop.project.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirestoreConfig {

  @Bean
  public FirebaseAuth firebaseAuth() throws IOException {

    final File file = ResourceUtils.getFile("classpath:serviceAccount.json");
    final FileInputStream serviceAccount = new FileInputStream(file);

    final FirebaseOptions options =
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

    FirebaseApp.initializeApp(options);

    return FirebaseAuth.getInstance();
  }
}
