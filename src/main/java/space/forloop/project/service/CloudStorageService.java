/* Licensed under Apache-2.0 */
package space.forloop.project.service;

import java.io.File;
import java.io.IOException;

/** @author Chris Turner (chris@forloop.space) */
public interface CloudStorageService {

  String uploadFile(File file, String name, String bucketName) throws IOException;
}
