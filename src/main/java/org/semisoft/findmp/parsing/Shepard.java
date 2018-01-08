package org.semisoft.findmp.parsing;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
public class Shepard {
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
        int lt = 10;
        for (int woj = 7; woj <= 7; woj++) { //Ustawianie ile wojewĂłdztw ma parsawac
            int licz1 = 0;
            ExecutorService executor = Executors.newFixedThreadPool(lt);
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
                    int koniec = 0;
                    List<Future<List<MedicalPoint>>> list = new ArrayList<Future<List<MedicalPoint>>>();
                    while (iteracja <= num && koniec == 0) {
                        //links = null;
                        //System.out.println(sessionId);
                        /*if (iteracja != 0) {
                            links = doc.select("div.pages a[href*=/GSL/GSL/UniwersalnePage?]:containsOwn(Â»)").first().attr("href");
                            if (links == null) {
                                break;
                            }
                        }*/
                        res = Jsoup.connect(elUrl)
                                //.data("username", "myUsername", "password", "myPassword")
                                //.method(Method.POST)
                                .timeout(10000)
                                .execute();

                        doc = res.parse();
                        sessionId = res.cookie("ASP.NET_SessionId");
                        p = links.indexOf("Page=");
                        List<String> linkList = new ArrayList<String>();
                        for (int k = iteracja*10-9;k<=iteracja*10 && k<=num;k++) {
                            links = links.substring(0, p + 5) + k + links.substring(links.indexOf("&PageSize"));
                            linkList.add(links);
                            if (k == num){
                                koniec = 1;
                                break;
                            }
                        }
                        Future<List<MedicalPoint>> future = executor.submit(new Sheep(linkList,"Thred - " + iteracja, sessionId));
                        list.add(future);
                        //System.out.println(links);
                        iteracja+= 1;
                    }
                    iteracja = 0;
                    for (Future<List<MedicalPoint>> fut:  list){
                        iteracja += 1;
                        while (true) {
                            try {
                                System.out.println("Fut");
                                List<MedicalPoint> medicalPoints1 = fut.get();
                                //TempDbAdd.add(medicalPoints1);
                                /*for (MedicalPoint medicalPoint : medicalPoints1) {
                                    TempDbAdd.add(medicalPoints);
                                    medicalPoints.add(medicalPoint);
                                }*/
                                //fut.cancel(true);
                                break;
                            } catch (OutOfMemoryError ome) {
                                System.err.println("Memory problems");
                                System.gc();
                                try {
                                    fut.wait(10000);
                                }catch (InterruptedException ie){}
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                break;
                            } catch (ExecutionException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                break;
                            }
                        }

                    }
                    break;
                } catch (SocketTimeoutException ex) {
                    //l.add("text...");
                    System.out.println("Socket Shepard");
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
                    System.out.println("IO Shepard");
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

