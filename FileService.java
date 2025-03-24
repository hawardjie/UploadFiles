package postsea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import postsea.entity.PsFile;
import postsea.repository.FileRepository;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public PsFile saveFile(MultipartFile file) throws IOException {
        PsFile psFile = new PsFile();
        psFile.setFilename(file.getOriginalFilename());
        psFile.setOwner("your_username");
        psFile.setContent(file.getBytes());
        psFile.setType(file.getContentType());
        return fileRepository.save(psFile);
    }

    public PsFile getFile(Long id) {
        return fileRepository.findById(id).get();
    }

    public Stream<PsFile> getFileInfoList(String owner) {
        return fileRepository.findAllByOwner(owner).stream();
    }
}
