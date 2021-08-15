package systems.tat.ramta.client.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ResourcesUtils {

    public static void unzip(String file, String dest) throws IOException {
        unzip(new File(file), new File(dest));
    }

    public static void unzip(File unzip, File dest) throws IOException {
        if (!dest.exists()) {
            dest.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(unzip.getPath()));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            String filePath = dest + File.separator + entry.getName();
            if (!entry.isDirectory()) {

                extractFile(zipIn, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            assert children != null;
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public static File getExecutePath() {
        return new File(System.getProperty("user.dir"));
    }

    public static boolean trueFormat(File file, String value) {
        if(!(file.getName().contains("."))) {
            return false;
        }
        String format = value;
        String[] split = file.getName().split("\\.");
        String type = "." + split[1];
        if(!(format.startsWith("."))) {
            format = "." + value;
        }
        return type.equals(format);
    }

    public static String read(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String result = null;
            StringBuilder builder = new StringBuilder();
            while ((result = reader.readLine()) != null) {
                builder.append(result);
            }
            reader.close();
            return builder.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String[] makeArray(String string, String regex) {
        return string.split(regex);
    }

}
