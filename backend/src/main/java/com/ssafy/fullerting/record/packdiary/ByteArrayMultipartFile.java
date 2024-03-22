import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ByteArrayMultipartFile implements MultipartFile, Serializable {

    private final byte[] content;
    private final String name;
    private final String originalFilename;
    private final String contentType;

    public ByteArrayMultipartFile(byte[] content, String name, String originalFilename, String contentType) {
        this.content = content;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return this.content.length == 0;
    }

    @Override
    public long getSize() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(this.content);
    }
}
