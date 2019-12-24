/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@Service
@RequiredArgsConstructor
public class CloudStorageServiceImpl implements CloudStorageService {

  private final Storage storage;

  @Override
  public String uploadFile(
      final InputStream inputStream, final String name, final String bucketName)
      throws IOException {

    final ByteArrayInputStream initialStream = new ByteArrayInputStream(new byte[] {0, 1, 2});

    final BlobInfo blobInfo =
        BlobInfo.newBuilder(bucketName, name)
            .setAcl(
                new ArrayList<>(Collections.singletonList(Acl.of(User.ofAllUsers(), Role.READER))))
            .build();

    return storage.create(blobInfo, IOUtils.toByteArray(initialStream)).getMediaLink();
  }
}
