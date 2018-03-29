package com.moon.admin.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;

/**
 * Created by szz on 2018/3/29 23:43.
 * Email szhz186@gmail.com
 */
public class FileUtils {
    public static String saveFile(MultipartFile file,String pathname){
        File targetFile=new File(pathname);
        if (targetFile.exists()){
            return pathname;
        }

        if (!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(targetFile);
            return pathname;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //代码简洁之道中建议尽量不要返回hull,这里我就返回一个空字符串?
        return "";
    }

    public static boolean deleteFile(String pathname) {

        File file=new File(pathname);
        if (file.exists()){
            boolean flag=file.delete();
            if (flag){
                File[] files = file.getParentFile().listFiles();
                if (files==null||files.length==0){
                    file.getParentFile().delete();
                }
            }
            return flag;
        }
        return false;
    }

    public static String fileMd5(InputStream inputStream){
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPath(){
        return "/"+ LocalDate.now().toString().replace("-","/")+"/";
    }

    /**
     * 将文本写入文件中
     * @param value
     * @param path
     */
    public static void saveTextFile(String value,String path){
        FileWriter writer=null;
        try {
            File file=new File(path);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }

            writer=new FileWriter(file);
            writer.write(value);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer!=null){
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getText(String path){
        File file=new File(path);
        if (!file.exists()) {
            return null;
        }

        try {
            return getText(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getText(InputStream inputStream) {
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;
        try {
            inputStreamReader=new InputStreamReader(inputStream, "utf-8");
            bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder builder=new StringBuilder();
            String string;
            while ((string=bufferedReader.readLine())!=null){
                string=string+"\n";
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
