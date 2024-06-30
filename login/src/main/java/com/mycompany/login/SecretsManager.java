package com.mycompany.login;
import java.io.InputStream;
import java.util.Properties;

public class SecretsManager {
    private Properties properties = new Properties();

    public SecretsManager() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("secrets.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find secrets.properties");
                return;
            }
            properties.load(input);
        } catch (Exception ex) {
        }
    }

    public String getSecretUser() {
        return properties.getProperty("mySecretUser");
    }

    public String getSecretPassword() {
        return properties.getProperty("mySecretPassword");
    }

    public static void main(String[] args) {
        SecretsManager sm = new SecretsManager();
        System.out.println("User: " + sm.getSecretUser());
        System.out.println("Password: " + sm.getSecretPassword());
    }
}
