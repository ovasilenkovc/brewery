package com.brewery.services.fileFolder;

import com.brewery.content.image.Image;
import com.brewery.utils.ConstantParams;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing stored files and folders.
 * It provides an ability for any CRUD operations for items with specified types.
 */
@Service(value = "fileFolderService")
public class FileFolderService {

    private static final Logger LOGGER = Logger.getLogger(FileFolderService.class);

    /**
     *  Method for saving file. It saves items into specified store path.
     *  If store doesn't exist, it will create new one with specified name.
     *  @param file multipart file for saving.
     *  @param path saving path.
     *
     *  @return String returns absolute path for saved file.
     */
    public String saveFile(MultipartFile file, String path) throws IOException {
        String storagePath = ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR + path;
        String fileName = file.getOriginalFilename();

        if(!new File(ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR).exists()){
            createFolders(ConstantParams.ROOT_PROJECT_DIR, ConstantParams.TEMP_FOLDER_PATH);
        }

        if (!new File(storagePath).exists())
            createFolders(path, ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR);

        try {
            byte[] bytes = file.getBytes();
            Path filePath = Paths.get(storagePath + "/" + fileName);
            Files.write(filePath, bytes);
            return filePath.toString();
        } catch (Exception e) {
            String message = "Files saving failed! " + fileName;
            LOGGER.error(message, e);
            throw new IOException(message, e);
        }
    }

    /**
     * A method for removing the file from the store.
     * Uses file name and store path to build full path for deleting.
     * @param fileName name of the removing file.
     * @param filePath path to the store.
     */
    public void removeFile(String fileName, String filePath) throws IOException {
        String pathForRemove = ConstantParams.TEMP_FOLDER_PATH + filePath + "/" + fileName;
        Path path = Paths.get(pathForRemove);
        Files.deleteIfExists(path);
    }

    /**
     * A method for checking is a file has a valid mimetype for the current context.
     * As an example, if context equals to "IMAGES", the mimetype for a file must be image/jpg, image/png, etc.
     * @param type the mimetype for current file.
     * @param context current runtime context.
     */
    public boolean checkIsValidDataType(String type, String context) {
        return ConstantParams.CONTENT_MIME_TYPES.get(context).contains(type);
    }

    //TO DO wasn't checked
    public List<com.brewery.content.File> getFiles(String folderPath, String context) {
        String fullPath = ConstantParams.TEMP_FOLDER_PATH + folderPath;
        List<com.brewery.content.File> files = new ArrayList<>();
        File folder = new File(fullPath);

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                String name = fileEntry.getName();
                String path = fileEntry.getPath();
                com.brewery.content.File file = ConstantParams.IMAGE_CONTEXT.equals(context) ? new Image(name, path) : null;
                files.add(file);
            }
        }
        return files;
    }

    //TO DO wasn't checked
    public static String getBase64Encoded(String filePath, String type) {
        String imageString = null;
        BufferedImage image = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            image = ImageIO.read(new File(filePath));
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    /**
     * A Method for encoding files, obtained from the store to the base64 encoding.
     * Uses absolute path for a file.
     * @param filePath an absolute file path to the file.
     *
     * @return an array of encoded bytes.
     */
    public byte[] getBase64Encoded(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.isFile()) {
            return getBase64ByteEncoded(file);
        }
        return null;
    }

    /**
     * A Method for encoding files, obtained from the store to the base64 encoding.
     * Uses a java.io.File as object for encoding.
     * @param file file object for encoding.
     *
     * @return an array of encoded bytes.
     */
    public byte[] getBase64ByteEncoded(File file) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            byte[] encoded = Base64.decodeBase64(bytes);
            fileInputStream.close();
            return encoded;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            if(fileInputStream != null){
                fileInputStream.close();
            }
            return null;
        }
    }

    /**
     * A Method for encoding files, obtained from the store to the base64 encoding.
     * Uses absolute path for obtaining a stored file.
     * @param fullPath an absolute path to the file.
     *
     * @return an array of encoded bytes.
     */
    public String getBase64StringEncoded(String fullPath) throws IOException {
        File file = new File(fullPath);
        FileInputStream fileInputStream = null;
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            String sb = ("data:" + mimeType + ";base64,") + Base64.encodeBase64URLSafeString(bytes);
            fileInputStream.close();
            return sb;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            if(fileInputStream != null){
                fileInputStream.close();
            }
            return null;
        }
    }

    /**
     * Method for recursive folders creation on the global server store.
     * Uses path for understanding depth of the folder tree,
     * any part of the path will be used as a new folder name.
     *
     * @param path path for building three.
     * @param parent name of the parent folder where the new folder will be created.
     */
    private void createFolders(String path, String parent) throws IOException {
        String[] items = path.substring(1, path.length()).split("/");

        for (String subFolderName : items) {
            File subDir = new File(parent, subFolderName);
            if (subDir.mkdir()) {
                LOGGER.info("Directory with name:" + subFolderName + "has been created successfully");
                parent += "/" + subFolderName;
            } else {
                LOGGER.info("Directory creation with name:" + subFolderName + "failed");
                throw new IOException("Directory creation with name:" + subFolderName + "failed");
            }
        }
    }
}