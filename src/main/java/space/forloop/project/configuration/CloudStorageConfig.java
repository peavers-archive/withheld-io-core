/* Licensed under Apache-2.0 */
package space.forloop.project.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author Chris Turner (chris@forloop.space) */
@Configuration
public class CloudStorageConfig {

  @Value("${firebase.key}")
  public String firebaseKey;

  @Bean
  public Storage storage() throws IOException {

    final FileInputStream serviceAccount = new FileInputStream(firebaseKey);

    final GoogleCredentials credentials = ServiceAccountCredentials.fromStream(serviceAccount);

    return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
  }
}
