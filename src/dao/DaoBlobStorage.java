package dao;


import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.*;

import java.io.FileInputStream;
import java.io.InputStream; // <-- ESTE ES EL QUE TE FALTA
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

public class DaoBlobStorage {
    private static final String storageConnectionString = "DefaultEndpointsProtocol=http;"
            + "AccountName=boletastorage;"
            + "AccountKey=P2Qw8tgP0O8jPEHvv6QKgJHxLKsT48befHfBzBWO6HUlkqOSZLP+8k0RgPzwj87GTd4cNpZzh+vZzIuIwz3UHA==";

    public static JSONObject subirZipARespuestas(Path zipFilePath, String blobName) throws Exception {
        if (zipFilePath == null || !Files.exists(zipFilePath)) {
            throw new IllegalArgumentException("ZIP no encontrado: " + zipFilePath);
        }
        if (blobName == null || blobName.trim().isEmpty()) {
            blobName = "form.zip";
        }

        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

        // Contenedor destino
        CloudBlobContainer zipContainer = blobClient.getContainerReference("respuestas");
        zipContainer.createIfNotExists();

        // Subida
        CloudBlockBlob zipBlob = zipContainer.getBlockBlobReference(blobName);
        try (InputStream in = new FileInputStream(zipFilePath.toFile())) {
            zipBlob.upload(in, Files.size(zipFilePath));
        }

        String fileUrl = zipBlob.getUri().toString();

        JSONObject result = new JSONObject();
        result.put("fileName", blobName);
        result.put("fileUrl", fileUrl);
        return result;
    }


}
