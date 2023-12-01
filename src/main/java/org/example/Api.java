package org.example;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;

public class Api {
    private static final String API_KEY = "example";
    private static double temperature = 0.0;
    private static int humidity = 0;
    private static int pressure = 0;
    private Map<String, Object> data = new HashMap<>();
    private Map<String, Object> tem = new HashMap<>();
    private Map<String, Object> hum = new HashMap<>();
    private Map<String, Object> pres = new HashMap<>();

    public String connection(double latitude, double longitude, String cityName) {
        String result = "none";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY); //Tworzy obiekt HttpGet, reprezentujący żądanie typu GET. Adres URL zawiera podane wartości szerokości (latitude), długości (longitude) oraz klucz API (API_KEY) do OpenWeatherMap.

        // add request headers
        request.addHeader("custom-key", "programming");
        //wykonanie żądania:
        try (CloseableHttpResponse response = httpclient.execute(request)) {
            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);
            if (entity != null) {
                result = EntityUtils.toString(entity);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(result);
                temperature = jsonNode.path("main").path("temp").asDouble();
                humidity = jsonNode.path("main").path("humidity").asInt();
                pressure = jsonNode.path("main").path("pressure").asInt();
                //wyswietlanie tych informacji:
                System.out.println("Temp: " + temperature + "K");
                System.out.println("Wilgotność: " + humidity + "%");
                System.out.println("Ciśnienie: " + pressure + " hPa");

                tem.put("temperature",temperature);
                hum.put("humidity",String.valueOf(humidity));
                pres.put("pressure",String.valueOf(pressure));
                weatherData(cityName,String.valueOf(tem),String.valueOf(hum),String.valueOf(pres));
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public void weatherData(String cit,String tem, String hum, String pres){
        List<String> dane = new ArrayList<>();
        dane.add(String.valueOf(tem));
        dane.add(String.valueOf(hum));
        dane.add(String.valueOf(pres));
        data.put(cit, dane);
    }

    public void saveToXml(String fileName) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(fileName), data);
    }
    public void saveToJson(String fileName) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(fileName), data);
    }
    public void saveToPDF(){
        Document document = new Document();
        Page page = document.getPages().add();
        page.getParagraphs().add(new TextFragment(String.valueOf(data)));
        document.save("weather.pdf");
    }
}