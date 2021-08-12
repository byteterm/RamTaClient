package systems.tat.ramta.client.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;

public class ResourcesUtils {

    public static boolean unzip(String file, String dest) {
        return unzip(new File(file), new File(dest));
    }

    public static boolean unzip(File unzip, File dest) {
        return false;
    }

    public static File getExecutePath() {
        return new File(new File(ResourceUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getParent());
    }

    public static boolean trueFormat(File file, String value) {
        String format = value;
        String[] split = file.getName().split("\\.");
        String type = "." + split[1];
        if(!(format.startsWith("."))) {
            format = "." + value;
        }
        return type.equals(format);
    }

}
