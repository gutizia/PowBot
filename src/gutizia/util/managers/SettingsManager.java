package gutizia.util.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SettingsManager {
    private final static Logger LOGGER = Logger.getLogger(SettingsManager.class.getName());

    public final static SettingsManager settingsManager = new SettingsManager();

    private String login;

    private SettingsManager() {}

    /**
     * loops through all lines in a text file and adds them to a list
     * skips over the line to remove
     * replaces the whole file with the saved lines
     *
     * @param raf the RandomAccessFile to remove the line from
     * @param lineNumber identifies the line to be removed
     */
    private void removeLine(RandomAccessFile raf, int lineNumber) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            raf.seek(0);
            String line;
            int i = 0;

            do {
                line = raf.readLine();
                i++;
                if (i != lineNumber && line != null) {
                    lines.add(line);
                }
            } while (line != null);

            raf.seek(0);
            raf.setLength(0);

            for (String newLine : lines) {
                raf.writeBytes(newLine + System.getProperty("line.separator"));
            }

        } catch (NullPointerException e) {
            LOGGER.info("!!!!!!!! NULL POINTER EXCEPTION IN REMOVING LINE!!!!!!!!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTradeSpot() {
        try {
            File file = new File(System.getProperty("java.io.tmpdir") + "gutTradeSpot.txt");

            if (!file.exists()) {
                if (file.createNewFile()) {
                    LOGGER.info("created file gutTradeSpot.txt");
                }
            }

            RandomAccessFile raf = new RandomAccessFile(file,"rw");
            raf.seek(0);

            return raf.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (NullPointerException ignored) {
        }

        return "";
    }

    public void setLoginOffline() {
        try {
            File file = new File("C:\\Users\\Jørgen\\AppData\\Local\\Temp\\gutLogin.txt");
            RandomAccessFile raf = new RandomAccessFile(file,"rw");
            raf.seek(0);

            String[] split;
            int lineNumber = 0;
            do {
                lineNumber++;
                split = raf.readLine().split(",");

            } while (!split[0].contains(login));

            removeLine(raf,lineNumber);

            raf.seek(raf.length());

            raf.writeBytes(split[0] + "," + "offline" + System.getProperty("line.separator"));

        }  catch (FileNotFoundException e) {
            LOGGER.severe("!!!!gutLogin.txt file not found!!!!");
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (NullPointerException e) {
            LOGGER.info("gutLogin reached nullPointerException");
        }
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
