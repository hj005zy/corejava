package corejava.voumeII.chapter01.findDirectories;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @version 1.1 2012-05-31
 * @author Cay Horstmann
 */
public class FindDirectories {

    public static void main(String[] args) throws IOException {

        Path dir = Paths.get(args.length == 0 ? System.getProperty("user.home") : args[0]);
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                if (attrs.isDirectory()) {
                    System.out.println(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

                return FileVisitResult.CONTINUE;
            }
        });
    }
}
