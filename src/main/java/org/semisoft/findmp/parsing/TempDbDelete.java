package org.semisoft.findmp.parsing;

import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class TempDbDelete {
    public void delete (Address address, String specialization){
        Connection c = null;
        Statement stmt = null;
        MedicalPoint medicalPoint = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager
                    .getConnection("jdbc:mysql://127.0.0.1:3306/db_example","springuser","ThePassword");
            stmt = c.createStatement();
            String sql = "DELETE FROM DATATEMP WHERE SPECIALIZATION = '"+specialization+"' AND CITY = '"+address.getCity()+"' AND STREET = '"+address.getStreet()+"' AND NUMBER = '"+address.getNumber()+"'";
            stmt.executeQuery(sql);
            stmt.close();
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            //System.exit(0);
        }
    }
}
