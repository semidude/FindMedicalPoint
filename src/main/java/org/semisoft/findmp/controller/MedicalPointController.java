package org.semisoft.findmp.controller;

import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.domain.repository.SectorRepository;
import org.semisoft.findmp.parsing.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/medicalPoints")
public class MedicalPointController
{
    @Autowired
    private MedicalPointRepository medicalPointRepository;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private Parser parser;
    private List<MedicalPoint> medicalPointsList;

    public MedicalPointController()
    {
        medicalPointsList = new ArrayList<>();

        medicalPointsList.add(new MedicalPoint("Marriot"));
        medicalPointsList.add(new MedicalPoint("Holender"));
        medicalPointsList.add(new MedicalPoint("Kimono"));
    }

    @RequestMapping("/parser")
    public @ResponseBody Iterable<MedicalPoint> parse(){
        //MedicalPoint medicalPoint = parser.add2();
        //medicalPointRepository.save(medicalPoint);
        try{
            List<MedicalPoint> medicalPoints = parser.add();
            for (MedicalPoint medicalPoint1 : medicalPoints){
                medicalPointRepository.save(medicalPoint1);
            }
        }
        catch (IOException e){}
        return medicalPointRepository.findAll();
    }

    @RequestMapping(value = "/findmp2", method = RequestMethod.GET)
    public List<MedicalPoint> findMedicalPoints2()
    {
        //for now, just show them all
        //in the future FindMedicalPointService.findMedicalPoints() would go here
        return medicalPointsList;
    }
    
    /** Find most appropriate medical points for given illness and localization **/
    @RequestMapping("/findmp")
    public @ResponseBody
    Iterable<MedicalPoint> findMedicalPoints()
    {
//        return findMedicalPointService.findMedicalPoints(new Specialization("Ortopeda"), latitude, longitude);
//        return latitude + " " + longitude;
        return medicalPointRepository.findAll();
    }

    /** A method for manually adding medical points to the database,
     *  could be used from the medical point's register form **/
    @RequestMapping("/addmp")
    public @ResponseBody String addMedicalPoint(@RequestParam String name, @RequestParam String specialization, @RequestParam String adress)
    {
        //TODO update findmp
        //example path: http://localhost:8080/addmp?name=Fajna+Przychodnia&specialization=Ortopeda&adress=Warszawa;Ksiecia+Janusza;39
        //note the ';' between adress parts in the line above
        String[] adressParts = adress.split(";");
        MedicalPoint medicalPoint = new MedicalPoint(
                name,
                new Specialization(specialization),
                new Address(adressParts[0], adressParts[1], adressParts[2]));
        medicalPointRepository.save(medicalPoint);
        return "Saved successfully!";
    }

    @RequestMapping(value = "/removemp", method = RequestMethod.POST)
    public @ResponseBody String removeMedicalPoint(@RequestParam long ID)
    {
        //example path: http://localhost:8080/removemp?ID=1
        MedicalPoint medPoint = medicalPointRepository.findOne(ID);
        if(medPoint!=null) {
            medicalPointRepository.delete(ID);
            return "Deleted succesfully!";
        }
        else return "No such ID.";
    }

    /** Just for testing, will be removed later */
    @RequestMapping("/findbysec")
    public @ResponseBody
    Iterable<MedicalPoint> findBySector(@RequestParam int x, @RequestParam int y)
    {
        return medicalPointRepository.findBySector(sectorRepository.findByXAndY(x, y));
    }

}
