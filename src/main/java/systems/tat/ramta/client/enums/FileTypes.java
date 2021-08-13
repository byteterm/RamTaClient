package systems.tat.ramta.client.enums;

public enum FileTypes {

    PROPERTIES("properties"),
    INITIALIZE("ini"),
    TEXT("txt"),
    JSON("json");

    private final String shortCut;

    FileTypes(String shortCut) {
        this.shortCut = shortCut;
    }

    public static FileTypes getByShortCut(String value) {
        FileTypes type = FileTypes.TEXT;
        for(FileTypes types : values()) {
            if(types.shortCut.equalsIgnoreCase(value)) {
                type = types;
            }
        }
        return type;
    }

    public String getShortCut() {
        return shortCut;
    }

}
