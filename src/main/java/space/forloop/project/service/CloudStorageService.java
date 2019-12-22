/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import java.io.IOException;
import java.io.InputStream;

/** @author Chris Turner (chris@forloop.space) */
public interface CloudStorageService {

  String uploadFile(InputStream inputStream, String name, String bucketName) throws IOException;
}
