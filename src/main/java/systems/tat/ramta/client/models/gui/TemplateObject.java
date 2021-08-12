package systems.tat.ramta.client.models.gui;

import lombok.Getter;

import java.util.List;

@Getter
public class TemplateObject {

    private String name;
    private String version;
    private List<String> authors;

    private String bannerPath;
    private List<String> description;
    private List<String> images;

    public TemplateObject() { }



}
