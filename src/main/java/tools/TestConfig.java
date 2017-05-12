package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * the class contains getters of the parameters out of the .properties file
 * which are used to specify the potentially various tools and the settings
 * to run the framework and system under test.
 */
public final class TestConfig {

    public String getSystemUnderTestBaseUrl() {
        String relativeFileLocation = "config.properties";
        Properties property = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(relativeFileLocation)) {
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String systemUnderTestBaseUrl =
                property.getProperty("systemUnderTestBaseUrl");

        return systemUnderTestBaseUrl;

    }

    public String getLocalPort() {
        String relativeFileLocation = "config.properties";
        Properties property = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(relativeFileLocation)) {
            property.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String localPort =
                property.getProperty("local.port");

        return localPort;
    }

}
