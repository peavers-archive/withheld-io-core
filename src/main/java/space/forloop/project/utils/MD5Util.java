package space.forloop.project.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

  private static String hex(final byte[] array) {
    final StringBuilder stringBuilder = new StringBuilder();
    for (final byte b : array) {
      stringBuilder.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
    }
    return stringBuilder.toString();
  }

  public static String md5Hex(final String message) {
    try {
      final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      return hex(messageDigest.digest(message.getBytes("CP1252")));
    } catch (final NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
    }
    return null;
  }
}
