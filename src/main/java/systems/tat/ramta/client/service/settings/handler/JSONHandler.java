package systems.tat.ramta.client.service.settings.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import systems.tat.ramta.client.utils.ResourcesUtils;

import java.io.File;

public class JSONHandler implements IFileHandler {

    private final File jsonFile;

    private final JsonObject json;

    public JSONHandler(String path) {
        this.jsonFile = new File(path);
        Gson gson = new Gson();
        this.json = gson.fromJson(ResourcesUtils.read(jsonFile), JsonObject.class);
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

    public File getJsonFile() {
        return jsonFile;
    }
}
