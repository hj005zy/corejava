package util;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ClassPathUtils {

    public static ClassLoader getDefaultClassLoader() {

        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassPathUtils.class.getClassLoader();
        }
        return cl;
    }

    public static String toFullyQualifiedPath(final Class<?> context, final String resourceName) {

        StringBuilder sb = new StringBuilder();
        sb.append(context.getPackage().getName().replace('.', File.separatorChar));
        sb.append(File.separator);
        sb.append(resourceName);
        return sb.toString();
    }

    public static String toFullyAbsolutePath(final Class<?> context, final String resourceName) {

        StringBuilder sb = new StringBuilder();
        sb.append(context.getProtectionDomain().getCodeSource().getLocation().getPath());
        sb.append(context.getPackage().getName().replace('.', File.separatorChar));
        sb.append(File.separator);
        sb.append(resourceName);
        return sb.toString();
    }

    public static InputStream getResourceAsStream(final Class<?> context, final String resourceName) {

        return getDefaultClassLoader().getResourceAsStream(toFullyQualifiedPath(context, resourceName));
    }

    public static URL getResourceAsURL(final Class<?> context, final String resourceName) {

        return getDefaultClassLoader().getResource(toFullyQualifiedPath(context, resourceName));
    }

    public static Path getResourceAsPath(final Class<?> context, final String resourceName) {

        try {
            return Paths.get(getResourceAsURL(context, resourceName).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getResourceAsFile(final Class<?> context, final String resourceName) {

        return getResourceAsPath(context, resourceName).toFile();
    }
}
