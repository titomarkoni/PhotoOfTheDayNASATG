import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Nasa {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public URL apodParam() throws URISyntaxException, MalformedURLException {
        URIBuilder b = new URIBuilder("https://api.nasa.gov/planetary/apod");
        b.addParameter("api_key", "yG5cyjoXagPdasv9xndCLPcTaH5yMTY9TaQ431cA");
        b.addParameter("date", dateFormat.format(new Date()));
        URL url = b.build().toURL();
        return url;
    }

    public String getImgNasa() throws URISyntaxException, MalformedURLException {
        String resultJson = parseUrl(apodParam());
        System.out.println("Записали весь JSON в String");
        String res = parseCurrentUrlJsonImg(resultJson);
        return res;
    }

    public String getTitleNasa() throws URISyntaxException, MalformedURLException {
        String resultJson = parseUrl(apodParam());
        System.out.println("Записали весь JSON в String");
        String res = parseCurrentUrlJsonTitle(resultJson);
        return res;
    }

    public static String parseUrl(URL url) {
        if (url == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        // открываем соединение к указанному URL
        // с помощью конструкции try-with-resources
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            // построчно считываем результат в объект StringBuilder
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String parseCurrentUrlJsonImg(String resultJson) {
        JSONObject json = new JSONObject(resultJson);
        System.out.println("Записали в объект");
        String value = json.get("url").toString();
        System.out.println("Записали url в value");
        System.out.println(value);
        return value;
    }

    public static String parseCurrentUrlJsonTitle(String resultJson) {
        JSONObject json = new JSONObject(resultJson);
        System.out.println("Записали в объект");
        String value = json.get("title").toString();
        System.out.println("Записали title в value");
        System.out.println(value);
        return value;
    }
}
