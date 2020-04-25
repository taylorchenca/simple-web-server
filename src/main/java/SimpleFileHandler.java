import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class SimpleFileHandler {
    private final String INDEX = "index.html";
    private String documentRoot;
    private String filePath;
    private String contentType;
    private int length;
    private byte[] byteArray;
    private File file;
    private FileStatus.Status fileStatus;

    public FileStatus.Status getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(FileStatus.Status fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public SimpleFileHandler(String documentRoot, String filePath) throws IOException {
        this.documentRoot = documentRoot;
        this.filePath = filePath;
        if (this.filePath.equals("/")) this.filePath += INDEX;
        try {
            this.file = new File(Main.documentRoot, this.filePath);
            this.byteArray = Files.readAllBytes(Paths.get(this.documentRoot, this.filePath));
        } catch (NoSuchFileException e) {
            this.fileStatus = FileStatus.Status.NOT_FOUND;
            this.contentType = "text/plain";
            this.length = 0;
            System.out.println(e);
            return;
        } catch (AccessDeniedException e) {
            this.fileStatus = FileStatus.Status.DENIED;
            this.contentType = "text/plain";
            this.length = 0;
            System.out.println(e);
            return;
        }
        this.contentType = extensionToContentType(FilenameUtils.getExtension(this.filePath));
        this.length = (int) file.length();
        this.fileStatus = FileStatus.Status.SUCCESS;
    }

    private String extensionToContentType(String ext) {
        switch (ext) {
            case "html": return "text/html";
            case "css": return "text/css";
            case "jpg": return "image/jpeg";
            case "gif": return "image/gif";
            default: return "text/plain";
        }
    }
}
