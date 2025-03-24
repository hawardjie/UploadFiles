package postsea.controller;

import jdk.jfr.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import postsea.entity.PsFile;
import postsea.response.PsFileInfo;
import postsea.response.PsMessage;
import postsea.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// @CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/v1/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PsMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PsMessage("File is empty"));
            }
            fileService.saveFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(new PsMessage("File uploaded successfully"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PsMessage("Error uploading file: " + e.getMessage()));
        }
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) {
        if (fileId.isEmpty() || !fileId.matches("[0-9]+")) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");
            String errorMsg = "{ \"message\": \"Invalid file id " + fileId + "\" }";
            return new ResponseEntity<>(errorMsg.getBytes(), headers, HttpStatus.BAD_REQUEST);
        }
        Long id = Long.parseLong(fileId);
        PsFile psFile = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + psFile.getFilename() + "\"")
                .body(psFile.getContent());
    }

    @GetMapping("/owners/{ownerId}")
    public ResponseEntity<List<PsFileInfo>> getDownloadList(@PathVariable String ownerId) {
         List<PsFileInfo> fileInfoList = fileService.getFileInfoList(ownerId).map(psFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(psFile.getId()))
                    .path("/download")
                    .toUriString();
            return new PsFileInfo(psFile.getFilename(), fileDownloadUri, psFile.getType(), psFile.getContent().length);
         }).collect(Collectors.toList());

         return ResponseEntity.status(HttpStatus.OK).body(fileInfoList);
    }
}
