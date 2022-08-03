package com.buyern.inventory.Services;

import com.buyern.inventory.Enums.SimpleMediaType;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@AllArgsConstructor
public class MediaService {
    final FileService fileService;

    /**
     * <h3>Verify that uploaded media type is an image</h3>
     *
     * @param type uploaded media type
     */
    public static SimpleMediaType verifyMediaType(String type) {
        if (Objects.equals(type, MediaType.IMAGE_GIF_VALUE) || Objects.equals(type, MediaType.IMAGE_JPEG_VALUE) || Objects.equals(type, MediaType.IMAGE_PNG_VALUE))
            return SimpleMediaType.IMAGE;
        return null;
//      throw new IllegalArgumentException("Unsupported Image Format: only " + MediaType.IMAGE_JPEG_VALUE + ", " + MediaType.IMAGE_PNG_VALUE + " and " + MediaType.IMAGE_GIF_VALUE + " are supported");
    }

    public static Boolean isImage(String type) {
        return Objects.equals(type, MediaType.IMAGE_GIF_VALUE) || Objects.equals(type, MediaType.IMAGE_JPEG_VALUE) || Objects.equals(type, MediaType.IMAGE_PNG_VALUE);
    }

    public static boolean isVideo(String type) {
//        video = mp4,
        return Objects.equals(type, "video/mp4") || Objects.equals(type, "video/mkv");
    }

    public String uploadInventoryMedia(String entityId, MultipartFile file, String inventoryId, String newName) {
        String[] name = Objects.requireNonNull(file.getContentType()).split("/");
        String uploadedFileUrl = fileService.upload(fileService.getContainerClient(entityId.toString()), file, "inventories/" + inventoryId + "/" + newName + "." + name[name.length - 1]);
        if (uploadedFileUrl == null)
            return fileService.upload(fileService.createContainerClient(entityId.toString()), file, "inventories/" + inventoryId + "/" + newName + "." + name[name.length - 1]);
        return uploadedFileUrl;
    }
    public String uploadInventoryItemMedia(String entityId, MultipartFile file, String inventoryId, String inventoryItemId, String newName) {
        String[] name = Objects.requireNonNull(file.getContentType()).split("/");
        System.out.println("--------------------");
        System.out.println(entityId);
        System.out.println("--------------------");
        System.out.println("inventories/" + inventoryId + "/" + inventoryItemId + "/" + newName + "." + name[name.length - 1]);
        String uploadedFileUrl = fileService.upload(fileService.getContainerClient(entityId), file, "inventories/" + inventoryId + "/" + inventoryItemId + "/" + newName + "." + name[name.length - 1]);
        if (uploadedFileUrl == null)
            return fileService.upload(fileService.createContainerClient(entityId), file, "inventories/" + inventoryId + "/" + inventoryItemId + "/" + newName + "." + name[name.length - 1]);
        return uploadedFileUrl;
    }
}
