package com.yujun.util.file.FileVisitor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Pattern;

/**
 * <b>修改记录：</b>
 * <p>
 * <li>
 * <p>
 * ---- yujun 2019/10/22
 * </li>
 * </p>
 *
 * <b>类说明：用于复制文件到指定文件夹下</b>
 * <p>
 *
 * </p>
 */
@Slf4j
public abstract class CopyFileVisitor extends FindFileVisitor {
    /**
     * 获取复制文件被复制到的目标文件夹
     * @author: yujun
     * @date: 2019/10/22
     * @param
     * @return: {@link java.nio.file.Path}
     * @exception:
    */
    protected abstract Path getTargetPath();

    @Override
    protected Pattern getDirectoryPattern() {
        return null;
    }

    @Override
    protected void handleFindedDirectory(Path dir, BasicFileAttributes attrs) {
        return;
    }

    @Override
    protected void handleFindedFile(Path path, BasicFileAttributes attrs) {
        try {
            Files.copy(path, this.getTargetPath(), null);
        } catch (IOException e) {
            log.error("Copy file fail.", e);
        }
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return super.visitFileFailed(file, exc);
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return super.postVisitDirectory(dir, exc);
    }
}
