package com.yujun.util.file.FileVisitor;


import com.yujun.util.file.FileInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <b>修改记录：</b>
 * <p>
 * <li>
 * <p>
 * ---- yujun 2019/10/15
 * </li>
 * </p>
 *
 * <b>类说明：</b>
 * <p>
 *
 * </p>
 */
@Slf4j
public class VideoFindVisitor extends FindFileVisitor {

    private List<FileInfo> videoList;

    public VideoFindVisitor videoFindVisitor() {
        return this;
    }

    public VideoFindVisitor() {
        this.videoList = new ArrayList<FileInfo>();
    }

    @Override
    protected Pattern getFilePattern() {
        String pa = "\\.(mp4|mkv|rmvb|avi|wmv|rm|mov|m4v|flv|ogg|mod|mpeg1|mpeg2)$";
        return Pattern.compile(pa, Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected Pattern getDirectoryPattern() {
        return null;
    }

    @Override
    protected void handleFindedFile(Path path, BasicFileAttributes attrs) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename(path.getFileName().toString());
        fileInfo.setFilePath(path.toAbsolutePath().toString());
        fileInfo.setFileMd5(com.yujun.util.security.FileUtil.fileMd5Sum(path));
        try {
            fileInfo.setFileSize(Files.size(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.videoList.add(fileInfo);
        if(this.videoList.size() >= 100) {
//            this.mongoTemplate.insert(this.videoList);
            log.info(this.videoList.toString());
            this.videoList.clear();
        }
    }

    @Override
    protected void handleFindedDirectory(Path dir, BasicFileAttributes attrs) {

    }
}
