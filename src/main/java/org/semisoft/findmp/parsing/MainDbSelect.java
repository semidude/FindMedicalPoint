package org.semisoft.findmp.parsing;

import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainDbSelect {
    public List<MedicalPoint> select (){
        Connection c = null;
        Statement stmt = null;
        List<MedicalPoint> medicalPoints = new ArrayList<MedicalPoint>();
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/temp_db",
                            "postgres", "piotrek");
            stmt = c.createStatement();
            //String sql = "Select id from data order by id desc";
            //ResultSet rs = stmt.executeQuery(sql);
            //int max = rs.getInt("id");
            //for (int id = 1;id<=max;id++) {
            String sql = "SELECT * from data";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String city = rs.getString("city");
                String number = rs.getString("number");
                String street = rs.getString("street");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                int sector_x = rs.getInt("sector_x");
                int sector_y = rs.getInt("sector_y");
                int id = rs.getInt("id");
                Address address = new Address(city,street,number);
                Specialization specialization1 = new Specialization(specialization);
                MedicalPoint medicalPoint = new MedicalPoint(name,specialization1,address);
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
}
