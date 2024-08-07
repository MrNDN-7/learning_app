package org.example.cuoiki_code_tutorial.Utils;

public class Session {
    public static String path;
    private static Session instance;
    private String loggedInUsername;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedInUsername(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
    }
}
