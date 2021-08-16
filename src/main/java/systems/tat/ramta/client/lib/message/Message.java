package systems.tat.ramta.client.lib.message;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class Message extends LinkedHashMap<String, Object> {

    private static final Type TYPE = new TypeToken<Map<String, Object>>() {
    }.getType();

    public Message() {
    }

    private Message(Map<String, Object> map) {
        super(map);
    }

    public static Message create() {
        return new Message();
    }
    public static Message create(Map<String, Object> map) {
        return new Message(map);
    }

    public static Message createFromJsonString(String json) {
        return new Message(new Gson().fromJson(json, TYPE));
    }

    public Message set(String key, Object value) {
        put(key, value);
        return this;
    }

    public Message setIfNotNull(String key, Object value) {
        if(value == null)
            return this;

        return set(key, value);
    }

    public Message setIfAbsent(String key, Object value) {
        if(containsKey(key))
            return this;

        return set(key, value);
    }

    public Message setIfCondition(String key, Object value, boolean condition) {
        if (!condition)
            return this;

        return set(key, value);
    }

    public Message setType(String type) {
        set("type", type);

        return this;
    }

    public Message setData(Object data) {
        set("data", data);

        return this;
    }

    public Message setTarget(String target) {
        set("target", target);

        return this;
    }

    public <T> T get(String key, Class<T> type) {
        return (T) get(key);
    }

    public String getType() {
        return (String) get("type");
    }

    public String getTarget() {
        return (String) get("target");
    }

    public Object getData() {
        return get("data");
    }

    public JsonObject toJsonObject() {
        return new Gson().toJsonTree(this).getAsJsonObject();
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}