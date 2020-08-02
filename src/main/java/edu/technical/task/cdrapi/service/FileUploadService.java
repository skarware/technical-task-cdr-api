package edu.technical.task.cdrapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    void parseCSVFileAndSaveDataIntoDB(MultipartFile file);

}
