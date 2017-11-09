package com.brewery.services.fileFolder;

import com.brewery.utils.ConnectionUtils;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

/**
 * Service for managing stored files and folders.
 * It provides an ability for any CRUD operations for items with specified types.
 */
@Service(value = "fileFolderService")
public class FileFolderService {

    private static final Logger LOGGER = Logger.getLogger(FileFolderService.class);

    /**
     * Method for saving file. It saves items into specified store path.
     * If store doesn't exist, it will create new one with specified name.
     *
     * @param file multipart file for saving.
     * @param path saving path.
     * @return String returns absolute path for saved file.
     */
    public String saveFile(MultipartFile file, String path) throws IOException {

        String storagePath = ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR + path;
        String fileName = file.getOriginalFilename();

        try {
            if (!new File(ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR).exists()) {
                createFolders(ConstantParams.ROOT_PROJECT_DIR, ConstantParams.TEMP_FOLDER_PATH);
            }

            if (!new File(storagePath).exists())
                createFolders(path, ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR);

            byte[] bytes = file.getBytes();
            Path filePath = Paths.get(storagePath + "/" + fileName);
            Files.write(filePath, bytes);
            return filePath.toString();

        } catch (IOException e) {
            String message = "File saving failed! " + fileName;
            LOGGER.error(message, e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * A method for removing the file from the store.
     * Uses file name and store path to build full path for deleting.
     *
     * @param fileName name of the removing file.
     * @param filePath path to the store.
     */
    public void removeFile(String fileName, String filePath) {
        String pathForRemove = ConstantParams.TEMP_FOLDER_PATH + filePath + "/" + fileName;
        Path path = Paths.get(pathForRemove);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * A method for checking is a file has a valid mimetype for the current context.
     * As an example, if context equals to "IMAGES", the mimetype for a file must be image/jpg, image/png, etc.
     *
     * @param type    the mimetype for current file.
     * @param context current runtime context.
     */
    public boolean checkIsValidDataType(String type, String context) {
        return ConstantParams.CONTENT_MIME_TYPES.get(context).contains(type);
    }

    /**
     * A Method for encoding files, obtained from the store to the base64 encoding.
     * Uses absolute path for obtaining a stored file.
     *
     * @param fullPath an absolute path to the file.
     * @return an array of encoded bytes.
     */
    public String getBase64StringEncoded(String fullPath) throws IOException {
        File file = new File(fullPath);
        FileInputStream fileInputStream = null;

        Utils.notNullHandler(file, "File not found!");

        try {
            String mimeType = Files.probeContentType(file.toPath());
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            String sb = "data:" + mimeType + ";base64," + new String(Base64.encodeBase64(bytes));
            fileInputStream.close();
            return sb;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            ConnectionUtils.close(fileInputStream);
            throw e;
        }
    }

    public Map<String, String> getJSonFileContent(String fileName) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        InputStream fileInputStram = new FileInputStream(file);

        String jsonString = IOUtils.toString(fileInputStram, UTF_8.name());
        fileInputStram.close();

        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};

        return mapper.readValue(jsonString, typeRef);
    }

    public void updateJsonFile(Map<String, Object> contactValues, String fileName) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        InputStream fileInputStream = new FileInputStream(file);
        String jsonString = IOUtils.toString(fileInputStream, UTF_8.name());
        fileInputStream.close();
        JSONObject jsonObject = new JSONObject(jsonString);

        for (Map.Entry<String, Object> entry : contactValues.entrySet()) {
            String entryKey = entry.getKey();
            jsonObject.put(entryKey, entry.getValue());
        }

        FileWriter fileWriter = new FileWriter(file.toString());
        fileWriter.write(jsonObject.toString());
        fileWriter.close();
    }

    /**
     * Method for recursive folders creation on the global server store.
     * Uses path for understanding depth of the folder tree,
     * any part of the path will be used as a new folder name.
     *
     * @param path   path for building three.
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
