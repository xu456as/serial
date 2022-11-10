package com.serialgroup.serial.manager;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Component
public class FileManager {

    private static final String STORAGE_DIR = "./storage/";

    public byte[] download(String fileHash) throws Exception {
        String filePath = getFilePath(fileHash);
        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[2048];
        ByteBuf byteBuf = Unpooled.buffer();
        int len = -1;
        while ((len = bufferedInputStream.read(buffer)) != -1) {
            byteBuf.writeBytes(buffer, 0, len);
        }
        byte[] result = new byte[byteBuf.readableBytes()];
        System.arraycopy(byteBuf.array(), 0, result, 0, byteBuf.readableBytes());
        return result;
    }

    public void save(String fileHash, byte[] fileBytes) throws Exception {
        String filePath = getFilePath(fileHash);
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(fileBytes);
        fileOutputStream.flush();
        fileOutputStream.close();
    }


    private String getFilePath(String fileHash) {
        return String.format("%s%s", STORAGE_DIR, fileHash);
    }

}
