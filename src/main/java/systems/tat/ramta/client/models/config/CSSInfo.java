package systems.tat.ramta.client.models.config;

import java.util.List;

public class CSSInfo {

    private List<String> account;
    private List<String> client;
    private List<String> variables;

    public CSSInfo() { }

    public List<String> getAccount() {
        return account;
    }

    public List<String> getClient() {
        return client;
    }

    public List<String> getVariables() {
        return variables;
    }
}
