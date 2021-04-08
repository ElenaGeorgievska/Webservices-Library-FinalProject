package com.example.library;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

//class used in MemberController in the webservice  @PostMapping("/members/upload") for uploading file/image for members
//class used in BookController in the webservice  @PostMapping("/books/upload") for uploading file/image for books
public class FileUploadUtil {
	   public static void uploadFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
	        Path uploadPath = Paths.get(uploadDir);
	         
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	         
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {        
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }      
	    }

}
