package edu.miu.cse.drivewise.client;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobStorageException;
import edu.miu.cse.drivewise.exception.storageblob.CustomBlobStorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AzureImageStorageClient implements ImageStorageClient {
    private final BlobServiceClient blobServiceClient;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @Override
    public String uploadImage(String originalImageName, InputStream data, long length) throws IOException {
        try {
            //Get the BlobContainerClient object to interact with the container
            BlobContainerClient blobContainerClient =blobServiceClient.getBlobContainerClient(containerName);
            //rename the image file to a unique name
            String newImageName=UUID.randomUUID().toString()+originalImageName.substring(originalImageName.lastIndexOf("."));
            //Get the blobClient object to interact with the specified blob
            BlobClient blobClient=blobContainerClient.getBlobClient(newImageName);
            //upload the image file to the blob
            blobClient.upload(data, length,true);
            return blobClient.getBlobUrl();
        }catch (BlobStorageException e) {
            throw new CustomBlobStorageException("Failed to upload image to Azure blob storage");
        }
    }
}
