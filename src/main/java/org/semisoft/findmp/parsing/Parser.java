package org.semisoft.findmp.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Iterator;

@Service
public class Parser {
    @Autowired
    private static MedicalPointRepository medicalPointRepository;
    public MedicalPoint add2(){
        Address address = new Address("Warszawa","Sarmacka","12D");
        Specialization specialization = new Specialization ("Piotr");
        MedicalPoint medicalPoint = new MedicalPoint("Moj",specialization,address);
        //medicalPointRepository.save(medicalPoint);
        return medicalPoint;
    }
    public List<MedicalPoint> add() throws IOException{
    //public static void main (String[] args) throws IOException{
        Response res = Jsoup.connect("https://zip.nfz.gov.pl/GSL/GSL/UniwersalneSearch?specjalizacja=&wojewodztwo=02&powiat=&dni=&miejscowosc=&ulica=&nazwa=&GodzinyOd=&GodzinyDo=&X-Requested-With=XMLHttpRequest")
                //.data("username", "myUsername", "password", "myPassword")
                //.method(Method.POST)
                .timeout(10000)
                .execute();

        Document doc = res.parse();
        String sessionId = res.cookie("ASP.NET_SessionId");
        //System.out.println(sessionId);
        String links = doc.select("div.pages a[href*=/GSL/GSL/UniwersalnePage?]:containsOwn(»)").first().attr("href");
        //System.out.println(links);
        List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        int licz2=0;
        while(licz2<10) {
            try {
                Document doc2 = Jsoup.connect("https://zip.nfz.gov.pl" + links + "&X-Requested-With=XMLHttpRequest")
                        //Document doc2 = Jsoup.connect("https://zip.nfz.gov.pl/GSL/GSL/UniwersalneSearch?specjalizacja=&wojewodztwo=02&powiat=&dni=&miejscowosc=&ulica=&nazwa=&GodzinyOd=&GodzinyDo=&X-Requested-With=XMLHttpRequest")
                        .cookie("ASP.NET_SessionId", sessionId)
                        .timeout(10000)
                        .get();
                //System.out.println("oifsj");
                String name = null;
                String city = null;
                String street = null;
                String number = null;
                String type = null;
                Elements resultLinks = doc2.select("div.ResultContnet");
                for (Element link : resultLinks) {
                    //System.out.println(link.text());
                    //System.out.println(" \\ ");
                    Elements subResultLinks = link.select("div.BasicInfo");
                    for (Element subLink : subResultLinks) {
                        //System.out.println(l.ownText());
                        String s = subLink.ownText();
                        int i = s.indexOf("ul.");
                        if (i == -1) {
                            i = s.indexOf("UL ");
                        }
                        System.out.println(i);
                        //System.out.println(s);
                        String nazwaPlacowki = s.substring(0, i);
                        String adres = s.substring(i);
                        i = adres.indexOf(",");
                        String ulica = adres.substring(3, i);
                        i = ulica.lastIndexOf(" ");
                        String numer = ulica.substring(i + 1);
                        ulica = ulica.substring(0, i);
                        s = adres.substring(i);
                        String kodPocztowy = s.substring(7, 13);
                        String miasto = s.substring(14);
                        System.out.println(ulica + "/" +numer+"/"+ kodPocztowy + "/" + miasto);
                        //System.out.println(nazwaPlacowki);
                        //System.out.println(adres);
                        String telefonRejestracja = subLink.select("span:containsOwn(rejestracji:)").text();
                        //System.out.println(telefonRejestracja);
                        String telefon = subLink.select("span:containsOwn(Telefon:)").text();
                        //System.out.println(telefon);
                        String typ = subLink.select("span.Mark").text();
                        System.out.println(typ);
                        String witryna = subLink.select("a[href*=http://]").text();
                        name = nazwaPlacowki;
                        city = miasto;
                        street = ulica;
                        number = numer;
                        type = typ;
                        //System.out.println(witryna);
                    }
                    subResultLinks = link.select("div.ResultTimetable.HarmonogramPracy div[class*=TimeTableOuter]");
                    for (Element subLink : subResultLinks) {
                        //System.out.println(subLink.text());
                    }
                    subResultLinks = link.select("div.ResultTimetable.HarmonogramRejestracji div[class*=TimeTableOuter]");
                    for (Element subLink : subResultLinks) {
                        //System.out.println(subLink.text());
                    }
                    Specialization specialization = new Specialization(type);
                    Address address = new Address(city, street, number);
                    MedicalPoint medicalPoint = new MedicalPoint(name, specialization, address);
                    medicalPoints.add(medicalPoint);
                    //medicalPointRepository.save(medicalPoint);
                }
                resultLinks = doc2.select("div[class*=yes]");
                for (Element link : resultLinks) {
                    //System.out.println(link.className());
                }
                break;
                //medicalPointRepository.save(medicalPoint);
            } catch (SocketTimeoutException ex) {
                //l.add("text...");
                System.out.println("Socket");
                licz2 += 1;
                if (licz2 >= 10) {
                    try {
                        URL url = new URL("https://www.google.pl/");
                        URLConnection connection = url.openConnection();
                        connection.connect();
                        break;
                    } catch (Exception exc) {
                        System.out.println("No internet connection");
                        licz2 = 0;
                    }
                }
            } catch (IOException e) {
                System.out.println("IO");
                licz2 += 1;
                while (true) {
                    try {
                        System.out.println("Checking connection");
                        URL url = new URL("https://www.google.pl/");
                        URLConnection connection = url.openConnection();
                        connection.connect();

                    break;
                } catch(Exception e2){
                    System.out.println("No internet connection");
                    licz2 = 0;
                    //System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    //System.exit(0);
                }
            }

        }
        }
        return medicalPoints;

    }
}

