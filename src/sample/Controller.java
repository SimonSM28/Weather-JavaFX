package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONObject;

import static javafx.scene.input.KeyCode.ENTER;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane main;

    @FXML
    private AnchorPane upBack;

    @FXML
    private Text name;

    @FXML
    private TextField cityName;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text preasure;

    @FXML
    private Text humididy;

    @FXML
    private Text visible;

    @FXML
    private Text wind;

    @FXML
    private Text info;

    @FXML
    private ImageView image;

    @FXML
    private Text sunrise;

    @FXML
    private Text sunset;


    @FXML
    void initialize() { // Two kinds of listener, first in case of pushing on button with id:getData. Second, if ENTER was realised.
        getData.setOnAction(actionEvent -> {
            getWeather();
            });

        cityName.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == ENTER) {
                getWeather();
            }
        });
    }

    private static String getUrlContent (String urlAdress) { // Method for get JSON line with URL.
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return content.toString();
    }


    public void getWeather() { // Method for get JSON line, get all information from it and fill up the form with this information.
        String getUserCity = cityName.getText().trim();
        String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity +"&units=metric&units=unix" + "&appid=5d25c46486208ab2af77a3d104f627d7");
        if (!output.isEmpty()) {
            JSONObject obj = new JSONObject(output);
            temp.setText(getTemp(obj));
            preasure.setText(getPressure(obj));
            humididy.setText(getHumidity(obj));
            visible.setText(getVisible(obj));
            wind.setText(getWind(obj));
            sunrise.setText(getSunRise(obj));
            sunset.setText(getSunSet(obj));
        }
        System.out.println(output);
    }

    private String getTemp(JSONObject obj) {
        try {
            return "Температура: " + obj.getJSONObject("main").getInt("temp") + " ℃";
        } catch (Exception e) {
            return "Температура: Нет информации";
        }
    }

    private String getPressure(JSONObject obj){
        try {
            return "Давление: " + obj.getJSONObject("main").getInt("pressure") + " барр";
    } catch (Exception e) {
            return "Давление: Нет информации";
        }
    }

    private String getHumidity (JSONObject obj) {
        try {
        return "Влажность: " + obj.getJSONObject("main").getInt("humidity") + " %";
    } catch (Exception e) {
        return "Влажность: Нет информации";
        }
    }

    private String getVisible (JSONObject obj) {
        try {
            return "Видимость: " + obj.getInt("visibility") + " м";
        } catch (Exception e) {
            return "Видимость: Нет информации";
        }
    }
    private String getWind (JSONObject obj) {
        return "Ветер: " + obj.getJSONObject("wind").getInt("speed") + " м/с";
    }
    private String getSunRise (JSONObject obj) {// Get time of sunrise and sunset in sec and convert them to date for demonstrate in format "00:00".
        try {
            long sunR = (obj.getJSONObject("sys").getLong("sunrise") + obj.getInt("timezone")) * 1000;
            SimpleDateFormat sd = new java.text.SimpleDateFormat("HH:mm");
            sd.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            return "Рассвет: " + sd.format(sunR);
        } catch (Exception e) {
            return "Рассвет: Нет информации";
        }
    }
    private String getSunSet (JSONObject obj) {// Get time of sunrise and sunset in sec and convert them to date for demonstrate in format "00:00".
        try {
            long sunS = (obj.getJSONObject("sys").getLong("sunset") + obj.getInt("timezone")) * 1000;
            SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
            sd.setTimeZone(TimeZone.getTimeZone("GMT-0"));
            return "Закат: " + sd.format(sunS);
        } catch (Exception e) {
            return "Закат: Нет информации";
        }
    }
}
