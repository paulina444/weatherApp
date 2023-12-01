package org.example;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        double latitudeCity = 0.0;
        double longitudeCity = 0.0;

        // Wczytanie listy obiektów miast z pliku JSON
        List<City> citie = Loading.loadCitiesFromJson("/miasta.json");
        //Wczytywanie listy obiektów miast z pliku XML
        List<City> c = Loading.loadCitiesFromXml("/miasta.xml");
        //dodanie ich do listy
        List<String> cityname = citie.stream()
                .map(City::getName)
                .collect(Collectors.toList());

        Api con = new Api();
        while (true){
            System.out.println("P-Podaj miasto, Z-Zakończ");
            Scanner input = new Scanner(System.in);
            String text = input.nextLine();
            if(text.equals("P")){
                String cit = input.nextLine();
                if(cityname.contains(cit)){
                    for(City city : citie){
                        if(cit.equals(city.getName())){
                            latitudeCity = city.getLatitude();
                            longitudeCity = city.getLongitude();
                        }
                    }
                    double latitude = latitudeCity;
                    double longitude = longitudeCity;
                    con.connection(latitude,longitude,cit);
                } else {
                    System.out.println("miasto nie znajduje sie na liscie");
                }
            } else if (text.equals("Z")) {
                System.out.println("P-PDF J-JSON X-XML");
                Scanner save = new Scanner(System.in);
                String saving = save.nextLine();
                switch (saving) {
                    case "P" -> con.saveToPDF();
                    case "J" -> con.saveToJson("target/weather.json");
                    case "X" -> con.saveToXml("target/weather.xml");
                }
                break;
            }
        }
        System.exit(0);
    }
}
