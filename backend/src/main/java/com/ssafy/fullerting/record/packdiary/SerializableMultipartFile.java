package com.ssafy.fullerting.record.packdiary;

import java.io.*;

import org.springframework.web.multipart.MultipartFile;

public class SerializableMultipartFile implements MultipartFile, Serializable {

    private static final long serialVersionUID = 1L;

    private transient MultipartFile multipartFile;

    public SerializableMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public String getName() {
        return multipartFile.getName();
    }

    @Override
    public String getOriginalFilename() {
        return multipartFile.getOriginalFilename();
    }

    @Override
    public String getContentType() {
        return multipartFile.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return multipartFile.isEmpty();
    }

    @Override
    public long getSize() {
        return multipartFile.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return multipartFile.getBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return multipartFile.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        multipartFile.transferTo(dest);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(multipartFile.getBytes());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        byte[] bytes = (byte[]) in.readObject();
//        this.multipartFile = new ByteArrayMultipartFile(bytes);
        /////////////////////////////수정필요
    }
}
