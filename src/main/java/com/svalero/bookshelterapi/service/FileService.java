package com.svalero.bookshelterapi.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Service para gestión de ficheros
 */
public interface FileService {
    void uploadFile(MultipartFile file);

    void downloadFile(HttpServletResponse response, String listPurchases) throws IOException;
}
