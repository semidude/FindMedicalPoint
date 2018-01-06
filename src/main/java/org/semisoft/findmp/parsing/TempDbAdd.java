package org.semisoft.findmp.parsing;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection.Response;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.OSEnvironment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@Service
public class TempDbAdd {
    @Autowired
    private static MedicalPointRepository medicalPointRepository;
    public static void create (){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/db_example", "springuser", "ThePassword");
            stmt = c.createStatement();
            System.out.println("Drop");
            String sql;
            sql = "DROP TABLE IF EXISTS datatemp";
            stmt.executeUpdate(sql);
            /*sql = "CREATE TABLE data " +
                    "(ID INT    NOT NULL," +
                    " city	TEXT, " +
                    " number	TEXT, " +
                    " street	TEXT, " +
                    " name	TEXT," +
                    " sector_x	INT ," +
                    " sector_y	INT ," +
                    "specialization	TEXT)";*/
            System.out.println("Create");
            sql = "CREATE TABLE datatemp " +
                    "(ID bigint(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " city	varchar(255), " +
                    " number	varchar(255), " +
                    " street	varchar(255), " +
                    " name	varchar(255)," +
                    " sector_x	INT ," +
                    " sector_y	INT ," +
                    "specialization	varchar(255))";
            stmt.executeUpdate(sql);
            System.out.println("After Create");
            stmt.close();
            c.close();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }
    public static void add (MedicalPoint medicalPoint) throws IOException{
        //List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        Connection c = null;
        Statement stmt = null;
        Shepard shepard = new Shepard();
        Parser parser = new Parser();
        //int id = 0;
        try {
            /*Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/temp_db",
                            "postgres", "piotrek");*/
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/db_example","springuser","ThePassword");
            stmt = c.createStatement();
            String sql;
            /*if (thread == 1) {
                sql = "DROP TABLE IF EXISTS data";
                stmt.executeUpdate(sql);
            /*sql = "CREATE TABLE data " +
                    "(ID INT    NOT NULL," +
                    " city	TEXT, " +
                    " number	TEXT, " +
                    " street	TEXT, " +
                    " name	TEXT," +
                    " sector_x	INT ," +
                    " sector_y	INT ," +
                    "specialization	TEXT)";
                sql = "CREATE TABLE data " +
                        "(ID bigint(20)    NOT NULL," +
                        " city	varchar(255), " +
                        " number	varchar(255), " +
                        " street	varchar(255), " +
                        " name	varchar(255)," +
                        " sector_x	INT ," +
                        " sector_y	INT ," +
                        "specialization	varchar(255))";
                stmt.executeUpdate(sql);
            }*/

            //medicalPoints = shepard.add();
            //for (MedicalPoint medicalPoint: medicalPoints){
                //id+=1;
                sql = "Insert into datatemp (city,number,street,name,specialization) VALUES (" +
                        "'"+medicalPoint.getAddress().getCity()+"'," +
                        "'"+medicalPoint.getAddress().getNumber()+"'," +
                        "'"+medicalPoint.getAddress().getStreet()+"'," +
                        "'"+medicalPoint.getName()+"'," +
                        //""+medicalPoint.getSector().getX()+"," +
                        //""+medicalPoint.getSector().getY()+"," +
                        "'"+medicalPoint.getSpecialization().getName()+"');";
                stmt.executeUpdate(sql);

            //}
            stmt.close();
            c.close();
        }
        //catch (IOException io){}
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            //System.exit(0);
        }
    }
}
