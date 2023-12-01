package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Loading {
    static List<City> loadCitiesFromJson(String filePath) {
        try {
            InputStream inputStream = Main.class.getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new FileNotFoundException("Plik " + filePath + " nie został odnaleziony");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            return objectMapper.readValue(jsonNode.toString(), objectMapper.getTypeFactory().constructCollectionType(List.class, City.class));
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas wczytywania danych z pliku JSON", e);
        }
    }
    public static List<City> loadCitiesFromXml(String filePath) {
        try {
            InputStream inputStream = Main.class.getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new FileNotFoundException("Plik " + filePath + " nie został odnaleziony");
            }
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(inputStream, xmlMapper.getTypeFactory().constructCollectionType(List.class, City.class));

        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas wczytywania danych z pliku XML", e);
        }
    }

}
