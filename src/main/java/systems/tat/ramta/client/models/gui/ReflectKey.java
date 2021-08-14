package systems.tat.ramta.client.models.gui;

public class ReflectKey {

    private final String reflect;
    private final String key;

    public ReflectKey(String reflect, String key) {
        this.reflect = "%" + reflect + "%";
        this.key = key;
    }

    public String getReflect() {
        return reflect;
    }

    public String getKey() {
        return key;
    }
}
