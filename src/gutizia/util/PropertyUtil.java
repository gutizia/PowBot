package gutizia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertyUtil {
    private Map<String, String> defaultProperties;
    private final String absolutePath;
    private Properties properties;
    private FileInputStream fis;
    private FileOutputStream fos;

    public PropertyUtil(String absolutePath, Map<String, String> defaultProperties) {
        this.absolutePath = absolutePath;
        this.defaultProperties = defaultProperties;
        this.properties = new Properties();
        init();
    }

    public PropertyUtil(String path) {
        this.absolutePath = path;
        this.properties = new Properties();
        createFile();
    }

    private void createFile() {
        try {
            File file = new File(absolutePath);
            if (!file.exists()) {
                System.out.println("file " + absolutePath + " did not exist");
                if (!file.createNewFile()) {
                    System.err.println("failed to create new file: " + absolutePath);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            File file = new File(absolutePath);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new Exception("unable to create new file");
                }
            }

            if (isPropertiesFileIncomplete()) {
                setPropertiesToDefault();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isPropertiesFileIncomplete() {
        try {
            fis = new FileInputStream(absolutePath);
            properties.load(fis);

            if (properties.size() < defaultProperties.size()) {
                System.out.println("properties size is less than defaultProperties size");
                return true;
            }
            int i = 0;
            for (Map.Entry<Object, Object> property : properties.entrySet()) {
                for (Map.Entry<String, String> defaultProp : defaultProperties.entrySet()) {
                    if (property.getKey().toString().equals(defaultProp.getKey())) {
                        i++;
                    }
                }
            }
            if (i < properties.size()) {
                System.out.println("properties did not contain all defaultProperties keys (" + i + "/" + defaultProperties.size() + ")");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return true; // I assume it's incomplete if any errors were thrown
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void setPropertiesToDefault() {
        try {
            fos = new FileOutputStream(absolutePath);
            properties.clear();
            for (Map.Entry<String, String> entry : defaultProperties.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
            properties.store(fos, "default properties");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key) {
        try {

            fis = new FileInputStream(absolutePath);
            properties.load(fis);
            return properties.getProperty(key);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void updateProperty(String key, boolean value) {
        if (value) {
            updateProperty(key, "true");
        } else {
            updateProperty(key, "false");
        }
    }

    public void updateProperty(String key, String value) {
        try {
            fis = new FileInputStream(absolutePath);
            properties.load(fis);
            properties.remove(key);
            properties.setProperty(key, value);
            fis.close();

            fos = new FileOutputStream(absolutePath);
            properties.store(fos, "custom properties");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
