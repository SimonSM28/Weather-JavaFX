package sample;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import com.sun.javafx.css.StyleCacheEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONObject;

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
    void initialize() {
            getData.setOnAction(actionEvent -> {
                String getUserCity = cityName.getText().trim();
                    String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=5d25c46486208ab2af77a3d104f627d7");
                    if (!output.isEmpty()) {
                        JSONObject obj = new JSONObject(output);
                        temp.setText("Температура: " + (obj.getJSONObject("main").getInt("temp") - 273) + " ℃");
                        preasure.setText("Давление: " + obj.getJSONObject("main").getInt("pressure") + " барр");
                        humididy.setText("Влажность: " + obj.getJSONObject("main").getInt("humidity"));
                        visible.setText("Видимость: " + obj.getInt("visibility") + " м");
                        wind.setText("Ветер: " + obj.getJSONObject("wind").getInt("speed") + " м/с");
                    }
                    System.out.println(output);

            });
    }

    private static String getUrlContent (String urlAdress) {
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
}
