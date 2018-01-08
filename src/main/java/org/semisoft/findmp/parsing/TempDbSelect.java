package org.semisoft.findmp.parsing;

import org.semisoft.findmp.domain.*;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.service.MedicalPointService;
import org.semisoft.findmp.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class TempDbSelect {
    @Autowired
    private MedicalPointService medicalPointService;
    @Autowired
    private static MedicalPointRepository medicalPointRepository;
    @Autowired
    private SectorService sectorService;
    public List<MedicalPoint> select (){
        Connection c = null;
        Statement stmt = null;
        List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/db_example","springuser","ThePassword");
            stmt = c.createStatement();
            String sql = "SELECT * from datatemp WHERE latitude != 0";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String city = rs.getString("city");
                String number = rs.getString("number");
                String street = rs.getString("street");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
//                int sector_x = rs.getInt("sector_x");
//                int sector_y = rs.getInt("sector_y");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                Address address = new Address(city,street,number);
                Specialization specialization1 = new Specialization(specialization);
                Location location = new Location(latitude,longitude);
                //Sector sector = new Sector(sector_x,sector_y);
                MedicalPoint medicalPoint = new MedicalPoint(name, specialization1,address);
                medicalPoint.setSector(sectorService.fromLocation(location));
                medicalPoint.setLocation(location);
                medicalPoints.add(medicalPoint);
            }
            //}
            rs.close();
            stmt.close();
            c.close();
        }
        //catch (IOException io){}
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            //System.exit(0);
        }
        return medicalPoints;
    }
    public MedicalPoint selectElement (Address address, String specialization){
        Connection c = null;
        Statement stmt = null;
        MedicalPoint medicalPoint = null;
        System.out.println("in select");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/db_example","springuser","ThePassword");
            stmt = c.createStatement();
            String sql = "SELECT * FROM DATATEMP WHERE SPECIALIZATION = '"+specialization+"' AND CITY = '"+address.getCity()+"' AND STREET = '"+address.getStreet()+"' AND NUMBER = '"+address.getNumber()+"'";
            ResultSet rs = stmt.executeQuery(sql);
            //if (rs != null) {
            String city = rs.getString("city");
            String number = rs.getString("number");
            String street = rs.getString("street");
            String name = rs.getString("name");
            String specialization1 = rs.getString("specialization");
            //int sector_x = rs.getInt("sector_x");
            //int sector_y = rs.getInt("sector_y");
            System.out.println(city+number+street+name);
            Address address1 = new Address(city, street, number);
            Specialization specialization2 = new Specialization(specialization);
            medicalPoint = medicalPointService.createAndLocalizeMedicalPoint(name, specialization2, address);
            //}
            rs.close();
            stmt.close();
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            //System.exit(0);
        }
        return medicalPoint;
    }
}
