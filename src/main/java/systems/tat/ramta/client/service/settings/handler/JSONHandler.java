package systems.tat.ramta.client.service.settings.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONHandler implements IFileHandler {

    private final File jsonFile;

    private final JsonObject json;

    public JSONHandler(String path) {
        this.jsonFile = new File(path);
        Gson gson = new Gson();
        this.json = gson.fromJson(jsonFrom(), JsonObject.class);
    }

    public String getString(String objectName, String key) {
        JsonArray array = json.getAsJsonArray(objectName);
        String result = "Error";
        for(int i = 0; i < array.size(); i++) {
            if(array.get(i).getAsJsonObject().get(key) != null) {
                result = array.get(i).getAsJsonObject().get(key).getAsString();
            }
        }
        return result;
    }

    private String jsonFrom() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
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

}
