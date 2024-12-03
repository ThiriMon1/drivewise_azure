package edu.miu.cse.drivewise.client;

import java.io.IOException;
import java.io.InputStream;

public interface ImageStorageClient {
    String uploadImage(String originalImageName,
                       InputStream data, long length) throws IOException;
}
