package postsea.repository;

import postsea.entity.PsFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<PsFile, Long> {

    List<PsFile> findAllByOwner(String owner);
}
