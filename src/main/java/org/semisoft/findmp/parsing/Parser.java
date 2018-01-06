package org.semisoft.findmp.parsing;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

import org.jsoup.Connection.Response;
import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.service.MedicalPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Parser {
    @Autowired
    private MedicalPointService medicalPointService;
    @Autowired
    private static MedicalPointRepository medicalPointRepository;
    public MedicalPoint add2(){
        Address address = new Address("Warszawa","Sarmacka","12D");
        Specialization specialization = new Specialization ("Piotr");
        MedicalPoint medicalPoint = medicalPointService.createAndLocalizeMedicalPoint("Moj",specialization,address);
        //medicalPointRepository.save(medicalPoint);
        return medicalPoint;
    }
    public List<MedicalPoint> add() throws IOException {
        //public static void main (String[] args) throws IOException{
        List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        TempDbAdd.create();
        int id = 0;
        for (int woj = 7; woj <= 7; woj++) {
            int licz1 = 0;
            while (licz1 < 10) {
                try {
                    String elUrl = "https://zip.nfz.gov.pl/GSL/GSL/UniwersalneSearch?specjalizacja=&wojewodztwo=";
                    if (woj < 5) {
                        elUrl = elUrl + "0" + 2 * woj;
                    } else {
                        elUrl = elUrl + 2 * woj;
                    }
                    elUrl = elUrl + "&powiat=&dni=&miejscowosc=&ulica=&nazwa=&GodzinyOd=&GodzinyDo=&X-Requested-With=XMLHttpRequest";
                    Response res = Jsoup.connect(elUrl)
                            //.data("username", "myUsername", "password", "myPassword")
                            //.method(Method.POST)
                            .timeout(10000)
                            .execute();

                    Document doc = res.parse();
                    String sessionId = res.cookie("ASP.NET_SessionId");
                    String links = doc.select("div.pages a[href*=/GSL/GSL/UniwersalnePage?]:containsOwn(»)").first().attr("href");
                    int p = links.indexOf("Count=");
                    int num = Integer.parseInt(links.substring(p+6));
                    Double t = num*0.1;
                    t = t - t%1;
                    if (num%10 != 0 ) {
                        t = t+1;
                    }
                    num = t.intValue();
                    int iteracja = 1;
                    while (iteracja <= 5) {
                        links = null;
                        //System.out.println(sessionId);
                        if (iteracja != 0) {
                            links = doc.select("div.pages a[href*=/GSL/GSL/UniwersalnePage?]:containsOwn(»)").first().attr("href");
                            if (links == null) {
                                break;
                            }
                        }
                        p = links.indexOf("Page=");
                        links = links.substring(0,p+5) + iteracja + links.substring(links.indexOf("&PageSize"));
                        //System.out.println(links);
                        int licz2 = 0;
                        while (licz2 < 10) {
                            try {
                                Document doc2;
                                if (iteracja != 0) {
                                    doc2 = Jsoup.connect("https://zip.nfz.gov.pl" + links + "&X-Requested-With=XMLHttpRequest")
                                            .cookie("ASP.NET_SessionId", sessionId)
                                            .timeout(10000)
                                            .get();
                                } else {
                                    doc2 = Jsoup.connect(elUrl)
                                            .cookie("ASP.NET_SessionId", sessionId)
                                            .timeout(10000)
                                            .get();
                                }
                        /*Document doc2 = Jsoup.connect("https://zip.nfz.gov.pl/GSL/GSL/UniwersalneSearch?specjalizacja=&wojewodztwo=02&powiat=&dni=&miejscowosc=&ulica=&nazwa=&GodzinyOd=&GodzinyDo=&X-Requested-With=XMLHttpRequest")
                        .cookie("ASP.NET_SessionId", sessionId)
                        .timeout(10000)
                        .get();*/
                                //System.out.println("oifsj");
                                String name = null;
                                String city = null;
                                String street = null;
                                String number = null;
                                String type = null;
                                Elements resultLinks = doc2.select("div.ResultContnet[tab-content=BasicInfoTab]");
                                for (Element link : resultLinks) {
                                    //System.out.println(link.text());
                                    //System.out.println(" \\ ");
                                    Elements subResultLinks = link.select("div.BasicInfo");
                                    for (Element subLink : subResultLinks) {
                                        //System.out.println(l.ownText());
                                        String s = subLink.ownText().replace("'","`");
                                        int i = s.indexOf("ul.");
                                        if (i == -1) {
                                            i = s.indexOf("UL ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("Aleje ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("UL.");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("Plac");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("al.");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("AL.");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("Pl");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("pl.");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("PL ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("Aleja");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("ALEJA");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("aleja ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("PL.");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("PLAC ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("PASAŻ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("Pasaż");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("pasaż");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("PASAZ ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("Pasaz ");
                                        }
                                        if (i == -1) {
                                            i = s.indexOf("pasaz ");
                                        }
                                        //System.out.println(i);
                                        System.out.println(s);
                                        String nazwaPlacowki = null;
                                        String adres = null;
                                        String ulica = null;
                                        String numer = null;
                                        String kodPocztowy = null;
                                        String miasto = null;
                                        try {
                                            nazwaPlacowki = s.substring(0, i);
                                            adres = s.substring(i);
                                            i = adres.indexOf(",");
                                            ulica = adres.substring(0, i);
                                            i = ulica.lastIndexOf(" ");
                                            numer = ulica.substring(i + 1);
                                            ulica = ulica.substring(0, i);
                                            i = adres.indexOf(", ");
                                            s = adres.substring(2);
                                            i = s.indexOf("-");
                                            kodPocztowy = s.substring(i - 2, i + 4);
                                            miasto = s.substring(i + 5);
                                        } catch (IndexOutOfBoundsException iobe) {
                                            System.err.println("Error");
                                            System.err.println(s);
                                            adres = s;
                                            ulica = "dupa";
                                            numer = "dupa";
                                            kodPocztowy = "dupa";
                                            miasto = "dupa";
                                        }
                                        System.out.println(ulica + "/" + numer + "/" + kodPocztowy + "/" + miasto);
                                        //System.out.println(nazwaPlacowki);
                                        //System.out.println(adres);
                                        String telefonRejestracja = subLink.select("span:containsOwn(rejestracji:)").text();
                                        //System.out.println(telefonRejestracja);
                                        String telefon = subLink.select("span:containsOwn(Telefon:)").text();
                                        //System.out.println(telefon);
                                        String typ = subLink.select("span.Mark").text().replace("'","`");
                                        System.out.println(typ);
                                        String witryna = subLink.select("a[href*=http://]").text();
                                        name = nazwaPlacowki;
                                        city = miasto;
                                        street = ulica;
                                        number = numer;
                                        type = typ;
                                        System.out.println("Byłem tu2");
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
                                    MedicalPoint medicalPoint = medicalPointService.createAndLocalizeMedicalPoint(name, specialization, address);
                                    System.out.println("Byłem tu");
                                    medicalPoints.add(medicalPoint);
                                    id += 1;
                                    TempDbAdd.add(medicalPoint);
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
                                    } catch (Exception e2) {
                                        System.out.println("No internet connection");
                                        licz2 = 0;
                                        //System.err.println(e.getClass().getName() + ": " + e.getMessage());
                                        //System.exit(0);
                                    }
                                }

                            }
                        }
                        iteracja += 1;
                    }
                    break;
                } catch (SocketTimeoutException ex) {
                    //l.add("text...");
                    System.out.println("Socket");
                    licz1 += 1;
                    if (licz1 >= 10) {
                        try {
                            URL url = new URL("https://www.google.pl/");
                            URLConnection connection = url.openConnection();
                            connection.connect();
                            break;
                        } catch (Exception exc) {
                            System.out.println("No internet connection");
                            licz1 = 0;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("IO");
                    licz1 += 1;
                    while (true) {
                        try {
                            System.out.println("Checking connection");
                            URL url = new URL("https://www.google.pl/");
                            URLConnection connection = url.openConnection();
                            connection.connect();

                            break;
                        } catch (Exception e2) {
                            System.out.println("No internet connection");
                            licz1 = 0;
                            //System.err.println(e.getClass().getName() + ": " + e.getMessage());
                            //System.exit(0);
                        }
                    }

                }
            }
        }
        return medicalPoints;
    }
}

