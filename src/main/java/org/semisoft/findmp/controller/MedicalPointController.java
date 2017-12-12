package org.semisoft.findmp.controller;

import org.semisoft.findmp.domain.Adress;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/medicalPoints")
public class MedicalPointController
{
    @Autowired
    private MedicalPointRepository medicalPointRepository;
    private List<MedicalPoint> medicalPointsList;

    public MedicalPointController()
    {
        medicalPointsList = new ArrayList<>();

        medicalPointsList.add(new MedicalPoint("Marriot"));
        medicalPointsList.add(new MedicalPoint("Holender"));
        medicalPointsList.add(new MedicalPoint("Kimono"));
    }


    /** Find most appropriate medical points for given illness and localization **/
    @RequestMapping(value = "/findmp", method = RequestMethod.GET)
    public Iterable<MedicalPoint> findMedicalPoints()
    {
        //for now, just show them all
        //in the future FindMedicalPointService.findMedicalPoints() would go here
         return medicalPointRepository.findAll();
    }

    @RequestMapping(value = "/findmp2", method = RequestMethod.GET)
    public List<MedicalPoint> findMedicalPoints2()
    {
        //for now, just show them all
        //in the future FindMedicalPointService.findMedicalPoints() would go here
        return medicalPointsList;
    }

    /** A method for manually adding medical points to the database,
     *  could be used from the medical point's register form **/
    @RequestMapping(value ="/addmp", method = RequestMethod.POST)
    public @ResponseBody String addMedicalPoint(@RequestParam String name, @RequestParam String adress, @RequestParam String type)
    {
        //example path: http://localhost:8080/addmp?name=Fajna+Przychodnia&type=Ortopeda&adress=Warszawa;Ksiecia+Janusza;39
        //note the ';' between adress parts in the line above
        String[] adressParts = adress.split(";");
        MedicalPoint medicalPoint = new MedicalPoint(
                name, new Adress(adressParts[0], adressParts[1], adressParts[2]), type);
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

}
