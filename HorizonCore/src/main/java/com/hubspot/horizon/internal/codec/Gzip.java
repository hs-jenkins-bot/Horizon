package com.hubspot.zenith.internal.codec;

import com.google.common.io.ByteStreams;
import com.google.common.net.HttpHeaders;
import com.hubspot.horizon.Codec;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@ParametersAreNonnullByDefault
public enum Gzip implements Codec {
  INSTANCE;

  @Override
  public @Nonnull byte[] compress(byte[] data) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OutputStream gzip = new GZIPOutputStream(baos);

      gzip.write(data);
      gzip.close();

      return baos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public @Nonnull byte[] decompress(byte[] bytes) {
    try {
      return ByteStreams.toByteArray(new GZIPInputStream(new ByteArrayInputStream(bytes)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void addHeader(Map<String, List<String>> headers) {
    headers.put(HttpHeaders.CONTENT_ENCODING, Collections.singletonList("gzip"));
  }
}
