/**
 * FileName: FileUtils
 * Author:   嘉平十七
 * Date:     2021/5/12 7:37
 * Description: 文件上传工具类
 */
package com.hunau.competition.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String IMG_PATH_PREFIX = "static/upload/imgs";

    /**
     * 使用这个方法，controller中应该如下写：
     * String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
     *         System.out.println("文件名："+fileName);
     *
     *         File fileDir = FileUtils.getImgDirFile();
     *         System.out.println("文件夹绝对路径"+fileDir.getAbsolutePath());
     *
     *         try {
     *             // 构建真实的文件路径
     *             File newFile = new File(fileDir.getAbsolutePath() + File.separator + fileName);
     *             System.out.println(newFile.getAbsolutePath());
     *
     *             // 上传图片到“绝对路径”
     *             file.transferTo(newFile);
     *             information.setFile(newFile.getAbsolutePath());
     *         } catch (IOException e) {
     *             e.printStackTrace();
     *         }
     * 这里提出工具方法：uploadFile
     * @return
     */
    public static File getImgDirFile(){
        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = new String("src/main/resources/" + IMG_PATH_PREFIX);

        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public static String uploadFile(MultipartFile file) throws IOException {
        //创建文件夹
        String fileDirPath = new String("src/main/resources/"+IMG_PATH_PREFIX);
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) fileDir.mkdirs();
        //获取文件名
        String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        File newFile = new File(fileDir.getAbsolutePath()+File.separator+fileName);
        file.transferTo(newFile);
        //因为要设置数据库中的file字段，所以返回一个文件路径
        return "/upload/imgs/"+fileName;
    }
}