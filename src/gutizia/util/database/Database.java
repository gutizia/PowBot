package gutizia.util.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import static gutizia.util.managers.SettingsManager.settingsManager;

public class Database {
    private final static Logger LOGGER = Logger.getLogger("db");

    public void createNewRun(String login, String run, String farmLevel) {
        try {
            URL url = new URL("http://www.gutizia.com/osrs/make_new_run.php");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("login", login);
            params.put("run", run);
            params.put("farmLevel", farmLevel);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: \n" + response);

            conn.disconnect();

        } catch (Exception ignored) {
        }
    }

    public String getReadyPatches(String login) {
        try {
            LOGGER.info("getting ready patches for login: " + login);
            URL url = new URL("http://www.gutizia.com/osrs/get_ready_patches.php");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("login",login);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: \n" + response);

            conn.disconnect();
            return response;
        } catch (Exception ignored) {
        }
        return "";
    }

    public void setReadyLoginDraft() {
        try {
            LOGGER.info("setting login info for ready farm runner");
            URL url = new URL("http://www.gutizia.com/osrs/get_ready_runner.php");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: " + response);
            settingsManager.setLogin(response);
            conn.disconnect();
        } catch (Exception ignored) {
        }
    }

    public void setReadyLogin() {
        try {
            LOGGER.info("setting login info for ready farm runner");
            URL url = new URL("http://10.0.0.125:8080/readyRunner");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: " + response);
            settingsManager.setLogin(response);
            conn.disconnect();
        } catch (Exception ignored) {
        }
    }

    public void updateFarmLevel(String login, int farmLevel) {
        try {
            LOGGER.info("updating farm level with new level: " + farmLevel);
            URL url = new URL("http://10.0.0.125:8080/updateFarmLevel");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("login",login);
            params.put("farmLevel",farmLevel);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: " + response);

            conn.disconnect();

        } catch (Exception ignored) {
        }
    }

    public void makeNewRun(String login, String produceName) {
        try {
            LOGGER.info("making new run");
            URL url = new URL("http://10.0.0.125:8080/makeNewRow");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("login",login);
            params.put("produceName",produceName);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: " + response);

            conn.disconnect();

        } catch (Exception ignored) {
        }
    }

    public void updateRun(String login, String produceName, long elapsedTime, int produce, int expGained) {
        try {
            LOGGER.info("updating " + produceName + " of " + login);
            URL url = new URL("http://10.0.0.125:8080/updateRun");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("login",login);
            params.put("produceName",produceName);
            params.put("elapsedTime",elapsedTime);
            params.put("produce",produce);
            params.put("expGained",expGained);
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: " + response);

            conn.disconnect();

        } catch (Exception ignored) {
        }
    }

    public void saveFarmTime(String login, String column, int growthTime) {
        try {
            LOGGER.info("saving farm time");
            URL url = new URL("http://10.0.0.125:8080/saveFarmTime");
            StringBuilder postData = new StringBuilder();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("login",login);
            params.put("column",column);
            params.put("time",growthTime + System.currentTimeMillis());
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Panel", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();

            System.out.println("response from server: " + response);

            conn.disconnect();

        } catch (Exception ignored) {
        }
    }
}
