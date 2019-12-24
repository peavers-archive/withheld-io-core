/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class CloudStorageServiceImpl implements CloudStorageService {

  private final Storage storage;

  @Override
  public String uploadFile(
      final InputStream inputStream, final String name, final String bucketName) {

    //    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //    final byte[] readBuf = new byte[4096];
    //
    //    while (inputStream.available() > 0) {
    //      final int bytesRead = inputStream.read(readBuf);
    //      byteArrayOutputStream.write(readBuf, 0, bytesRead);
    //    }

    final BlobInfo blobInfo =
        storage.create(
            BlobInfo.newBuilder(bucketName, name)
                .setAcl(
                    new ArrayList<>(
                        Collections.singletonList(Acl.of(User.ofAllUsers(), Role.READER))))
                .build(),
            inputStream);

    return blobInfo.getMediaLink();
  }
}
