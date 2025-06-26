package com.example.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

public class FileToDbUtil {

    /**
     * 文件转字符串
     * @param file
     * @return
     */
    public static String fileToStr(MultipartFile file){
        try {
            return Base64.getEncoder().encodeToString(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转文件
     * @param str
     * @return
     */
    public static byte[] strToFile(String str){
        return Base64.getDecoder().decode(str);
    }

    public static void retFile(String str, HttpServletResponse response) throws IOException{
        // 设置响应头（关键点：Content-Disposition 为 attachment）
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;");

        byte[] fileContent = strToFile(str);
        // 写入响应流
        try (OutputStream os = response.getOutputStream()) {
            os.write(fileContent);
            os.flush();
        }
    }

}
