package org.semisoft.findmp.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
public class Shepard {
    @Autowired
    private static MedicalPointRepository medicalPointRepository;
    public MedicalPoint add2(){
        Address address = new Address("Warszawa","Sarmacka","12D");
        Specialization specialization = new Specialization ("Piotr");
        MedicalPoint medicalPoint = new MedicalPoint("Moj",specialization,address);
        //medicalPointRepository.save(medicalPoint);
        return medicalPoint;
    }
    public List<MedicalPoint> add() throws IOException {
        //public static void main (String[] args) throws IOException{
        List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        for (int woj = 1; woj <= 1; woj++) { //Ustawianie ile wojewĂłdztw ma parsawac
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
                    ExecutorService executor = Executors.newFixedThreadPool(10);
                    List<Future<List<MedicalPoint>>> list = new ArrayList<Future<List<MedicalPoint>>>();
                    int iteracja = 1;
                    while (iteracja <= 5) {// iloĹ›c stron do sparsowania, jeĹ›li wszystkie to wartoĹ›Ä‡ 'num'
                        //links = null;
                        //System.out.println(sessionId);
                        /*if (iteracja != 0) {
                            links = doc.select("div.pages a[href*=/GSL/GSL/UniwersalnePage?]:containsOwn(Â»)").first().attr("href");
                            if (links == null) {
                                break;
                            }
                        }*/
                        p = links.indexOf("Page=");
                        links = links.substring(0,p+5) + iteracja + links.substring(links.indexOf("&PageSize"));
                        Future<List<MedicalPoint>> future = executor.submit(new Sheep(links,"Thred - " + iteracja, sessionId));
                        list.add(future);
                        //System.out.println(links);
                        iteracja+= 1;
                    }
                    for (Future<List<MedicalPoint>> fut:  list){
                        try {
                            List<MedicalPoint> medicalPoints1 = fut.get();
                            for (MedicalPoint medicalPoint: medicalPoints1){
                                medicalPoints.add(medicalPoint);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
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

