package org.semisoft.findmp.parsing;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.service.LocationService;
import org.semisoft.findmp.service.MedicalPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.OSEnvironment;

import java.util.Map;

@Service
public class TempDbAdd {
    @Autowired
    private static MedicalPointRepository medicalPointRepository;
    @Autowired
    private MedicalPointService medicalPointService;
    @Autowired
    private LocationService locationService;
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
            System.out.println("Create");
            sql = "CREATE TABLE datatemp " +
                    "(ID bigint(20)  NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    " city	varchar(255), " +
                    " number	varchar(255), " +
                    " street	varchar(255), " +
                    " name	varchar(255)," +
                    " sector_x	INT ," +
                    " sector_y	INT ," +
                    "latitude DOUBLE ," +
                    "longitude DOUBLE ," +
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
        Connection c = null;
        Statement stmt = null;
        Shepard shepard = new Shepard();
        Parser parser = new Parser();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/db_example","springuser","ThePassword");
            stmt = c.createStatement();
            String sql;
            sql = "Insert into datatemp (city,number,street,name,sector_x, sector_y,latitude,longitude,specialization) VALUES (" +
                    "'"+medicalPoint.getAddress().getCity()+"'," +
                    "'"+medicalPoint.getAddress().getNumber()+"'," +
                    "'"+medicalPoint.getAddress().getStreet()+"'," +
                    "'"+medicalPoint.getName()+"'," +
                    ""+0+"," +
                    ""+0+"," +
                    "0," +
                    "0," +
                    "'"+medicalPoint.getSpecialization().getName()+"');";
            stmt.executeUpdate(sql);
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
    public void sector () {
        java.sql.Connection c = null;
        Statement stmt = null;
        Statement stmtbis = null;
        List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/db_example","springuser","ThePassword");
            stmt = c.createStatement();
            stmtbis = c.createStatement();
            //String sql = "Select id from data order by id desc";
            //ResultSet rs = stmt.executeQuery(sql);
            //int max = rs.getInt("id");
            //for (int id = 1;id<=max;id++) {
            String sql = "SELECT * from datatemp where latitude = 0 and longitude = 0";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String city = rs.getString("city");
                String number = rs.getString("number");
                String street = rs.getString("street");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                int id = rs.getInt("id");
                //MedicalPoint medicalPoint = medicalPointService.createAndLocalizeMedicalPoint(name,new Specialization(specialization),new Address(city,street,number));
                MedicalPoint medicalPoint = new MedicalPoint(name, new Specialization(specialization), new Address(city, street, number));
                medicalPoint.setLocation(locationService.fromAddress(medicalPoint.getAddress()));

//                sql = "UPDATE datatemp set sector_x = '"+medicalPoint.getSector().getX()+"' where ID='"+id+"';";
//                stmtbis.executeUpdate(sql);
//                sql = "UPDATE datatemp set sector_y = '"+medicalPoint.getSector().getY()+"' where ID='"+id+"';";
//                stmtbis.executeUpdate(sql);
                sql = "UPDATE datatemp set latitude = '"+medicalPoint.getLocation().getLatitude()+"' where ID='"+id+"';";
                stmtbis.executeUpdate(sql);
                sql = "UPDATE datatemp set longitude = '"+medicalPoint.getLocation().getLongitude()+"' where ID='"+id+"';";
                stmtbis.executeUpdate(sql);
                /*sql = "UPDATE datatemp set sector_x = "+0+" where ID='"+id+"';";
                stmtbis.executeUpdate(sql);
                sql = "UPDATE datatemp set sector_y = "+0+" where ID='"+id+"';";
                stmtbis.executeUpdate(sql);
                sql = "UPDATE datatemp set latitude = "+0+" where ID='"+id+"';";
                stmtbis.executeUpdate(sql);
                sql = "UPDATE datatemp set longitude = "+0+" where ID='"+id+"';";
                stmtbis.executeUpdate(sql);*/

            }
            rs.close();
            stmt.close();
            stmtbis.close();
            c.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            //System.exit(0);
        }
    }
}
