package systems.tat.ramta.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public static BufferedImage getImageFromBytes(byte[] bytes) {
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error("Can't load image by bytes!");
        }
        return null;
    }

    public static InputStream getImageAsStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static byte[] getBytesFromImage(BufferedImage image, String type) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, type, stream);
            return stream.toByteArray();
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.error("Can't create byte array from image -> " + image.toString());
        }
        return null;
    }

}
