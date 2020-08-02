package edu.technical.task.cdrapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.technical.task.cdrapi.service.FileUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    private final FileUploadService fileUploadService;

    private final ObjectMapper mapper;

    public FileUploadController(FileUploadService fileUploadService, ObjectMapper mapper) {
        this.fileUploadService = fileUploadService;
        this.mapper = mapper;
    }

    @PostMapping("/api/cdrs/uploadFile")
    public ObjectNode uploadCSVFile(@RequestParam("file") MultipartFile file) {

        // Pass MultipartFile for parsing and saving into Database
        fileUploadService.parseCSVFileAndSaveDataIntoDB(file);

        // Create and return JSON on the fly
        ObjectNode methodObjectNode = mapper.createObjectNode();
        ObjectNode fileObjectNode = mapper.createObjectNode();
        fileObjectNode.put("name", file.getOriginalFilename());
        fileObjectNode.put("type", file.getContentType());
        fileObjectNode.put("size", file.getSize());
        methodObjectNode.set("file", fileObjectNode);

        return methodObjectNode;
    }

}
