package corejava.voumeII.chapter01.zip;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @version 1.4 2012-06-01
 * @author Cay Horstmann
 */
public class ZipTest {

    public static void main(String[] args) throws IOException {

        String zipname = args[0];
        showContents(zipname);
        System.out.println("---");
        showContents2(zipname);
    }

    public static void showContents(String zipname) throws IOException {

        // Here, we use the classic zip API
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipname))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                System.out.println(entry.getName());

                Scanner in = new Scanner(zin);
                while (in.hasNextLine()) {
                    System.out.println("   " + in.nextLine());
                }
                // DO NOT CLOSE in            
                zin.closeEntry();
            }
        }
    }

    public static void showContents2(String zipname) throws IOException {

        // Here, we make a Java SE 7 file system
        FileSystem fs = FileSystems.newFileSystem(Paths.get(zipname), null);
        Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

                System.out.println(path);
                for (String line : Files.readAllLines(path, Charset.forName("UTF-8"))) {
                    System.out.println("   " + line);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
