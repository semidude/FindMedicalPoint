package org.semisoft.findmp.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Sheep implements Callable<List<MedicalPoint>> {
    private List<String> urls;
    private String threadName;
    private String sessionId;

    public Sheep(List<String> urls, String threadName, String sessionId) {
        this.urls = urls;
        this.threadName = threadName;
        this.sessionId = sessionId;
    }

    public List<MedicalPoint> call () {
        int licz2 = 0;
        List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        for (String url : urls) {
            while (licz2 < 10) {
                try {
                    Document doc2;
                    System.gc();
                    doc2 = Jsoup.connect("https://zip.nfz.gov.pl" + url + "&X-Requested-With=XMLHttpRequest")
                            .cookie("ASP.NET_SessionId", sessionId)
                            .timeout(10000)
                            .get();
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
                            String s = subLink.ownText().replace("'", "`").replace('"', '`');
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
                                if (!numer.matches(".*\\d+.*")) {
                                    i = ulica.lastIndexOf(" ");
                                    numer = ulica.substring(i+1)+numer;
                                    ulica = ulica.substring(0,i);
                                }
                                i = adres.indexOf(", ");
                                s = adres.substring(2);
                                i = s.lastIndexOf("-");
                                int z = 0;
                                while (true) {
                                    i = s.indexOf("-",z);
                                    kodPocztowy = s.substring(i-2, i+4);
                                    miasto = s.substring(i+5);
                                    if (kodPocztowy.matches(".*[a-zA-Z].*")) {
                                        z = i+1;
                                    }
                                    else {
                                        break;
                                    }
                                }
                            } catch (IndexOutOfBoundsException iobe) {
                                System.err.println("Error");
                                System.err.println(s);
                                adres = s;
                                ulica = "dupa";
                                numer = "dupa";
                                kodPocztowy = "dupa";
                                miasto = "dupa";
                            }
                        /*String adres = s.substring(i);
                        i = adres.indexOf(",");
                        String ulica = adres.substring(0, i);
                        i = ulica.lastIndexOf(" ");
                        String numer = ulica.substring(i + 1);
                        ulica = ulica.substring(0, i);
                        i = adres.indexOf(", ");
                        s = adres.substring(2);
                        i = s.indexOf("-");
                        String kodPocztowy = s.substring(i-2, i+4);
                        String miasto = s.substring(i+5);*/
                            System.out.println(ulica + "/" + numer + "/" + kodPocztowy + "/" + miasto);
                            //System.out.println(nazwaPlacowki);
                            //System.out.println(adres);
                            String telefonRejestracja = subLink.select("span:containsOwn(rejestracji:)").text();
                            //System.out.println(telefonRejestracja);
                            String telefon = subLink.select("span:containsOwn(Telefon:)").text();
                            //System.out.println(telefon);
                            String typ = subLink.select("span.Mark").text().replace("'", "`");
                            System.out.println(typ);
                            String witryna = subLink.select("a[href*=http://]").text();
                            name = nazwaPlacowki;
                            city = miasto;
                            street = ulica;
                            number = numer;
                            type = typ;
                            //System.out.println("ByĹ‚em tu2");
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
                        System.out.println("ByĹ‚em tu");
                        medicalPoints.add(medicalPoint);
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
                    System.out.println("Socket Sheep" + threadName);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                    }
                    //licz2 += 1;
                    if (licz2 >= 10) {
                        try {
                            URL urla = new URL("https://zip.nfz.gov.pl" + url + "&X-Requested-With=XMLHttpRequest");
                            URLConnection connection = urla.openConnection();
                            connection.connect();
                            break;
                        } catch (Exception exc) {
                            System.out.println("No internet connection");
                            licz2 = 0;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("IO Sheep" + threadName);
                    //licz2 += 1;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ie) {
                    }
                    while (true) {
                        try {
                            System.out.println("Checking connection");
                            URL urla = new URL("https://zip.nfz.gov.pl" + url + "&X-Requested-With=XMLHttpRequest");
                            URLConnection connection = urla.openConnection();
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
            System.out.println(threadName + " Exiting" + " - " + licz2);
        /*try {
            TempDbAdd.add(medicalPoints,1);
        }catch (IOException ioe){}*/

        }
        return medicalPoints;
    }
}
