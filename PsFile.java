package postsea.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "files")
public class PsFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Lob
    @Column(name = "content", length = 16_777_215, nullable = false)
    private byte[] content;

    private String type;
}
