package systems.tat.ramta.client.service.settings.handler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesHandler implements IFileHandler {

    private final File propertiesFile;

    public PropertiesHandler(String fileName) {
        this.propertiesFile = new File(fileName);
    }

    public void write(String header, String key, String value) {
        try {
            Properties properties = new Properties();
            properties.setProperty(key, value);
            FileWriter writer = new FileWriter(propertiesFile);
            properties.store(writer, header);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Object get(String key) {
        try {
            FileReader reader = new FileReader(propertiesFile);
            Properties properties = new Properties();
            properties.load(reader);
            Object value = properties.getProperty(key);
            reader.close();
            return value;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public int getInteger(String key) {
        return (int) get(key);
    }

    public double getDouble(String key) {
        return (Double) get(key);
    }

    public float getFloat(String key) {
        return (Float) get(key);
    }

    public String[] getStringList(String key) {
        return getString(key).split(";");
    }

    public List<String> getList(String key) {
        return Arrays.asList(getStringList(key));
    }

}
