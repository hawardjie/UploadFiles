package postsea.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PsFileInfo {
    private String filename;
    private String url;
    private String type;
    private long size;
}
