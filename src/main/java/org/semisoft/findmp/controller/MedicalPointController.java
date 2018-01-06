package org.semisoft.findmp.controller;

import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.Location;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.domain.repository.SectorRepository;
import org.semisoft.findmp.parsing.Parser;
import org.semisoft.findmp.parsing.*;
import org.semisoft.findmp.service.FindMedicalPointService;
import org.semisoft.findmp.service.LocationService;
import org.semisoft.findmp.service.MedicalPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/medicalPoints")
public class MedicalPointController
{
    @Autowired
    private FindMedicalPointService findMedicalPointService;
    @Autowired
    private MedicalPointService medicalPointService;
    @Autowired
    private LocationService locationService;

    @Autowired
    private MedicalPointRepository medicalPointRepository;
    @Autowired
    private SectorRepository sectorRepository;
    @Autowired
    private Parser parser;

    @Autowired
    private TempDbAdd tempDbAdd;
    @Autowired
    private TempDbSelect tempDbSelect;
    @Autowired
    private MainDbSelect mainDbSelect;
    @Autowired
    private TempDbDelete tempDbDelete;
    @Autowired
    private Shepard shepard;

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

    @RequestMapping("/parse")
    public @ResponseBody Iterable<MedicalPoint> parse2(){
        //MedicalPoint medicalPoint = parser.add2();
        //medicalPointRepository.save(medicalPoint);
        try{
            shepard.add();
        }
        catch (IOException e){}
        return medicalPointRepository.findAll();
    }
    @RequestMapping("/pars")
    public @ResponseBody Iterable<MedicalPoint> parse3(){
        //MedicalPoint medicalPoint = parser.add2();
        //medicalPointRepository.save(medicalPoint);
        /*List<MedicalPoint> medicalPoints = tempDbSelect.select();
            for (MedicalPoint medicalPoint1 : medicalPoints) {
                Address address = new Address(medicalPoint1.getAddress().getCity(),medicalPoint1.getAddress().getStreet(),medicalPoint1.getAddress().getNumber());
                Specialization specialization = new Specialization(medicalPoint1.getSpecialization().getName());

                //medicalPoint = medicalPoint1;
                medicalPointRepository.save(medicalPoint1);
            }
        */
        //List<MedicalPoint> medicalPointList = mainDbSelect.select();
        /*Iterable<MedicalPoint> medicalPointIterable = medicalPointRepository.findAll();
        for (MedicalPoint medicalPoint: medicalPointIterable){
            MedicalPoint subMedicalPoint = tempDbSelect.selectElement(medicalPoint.getAddress(),medicalPoint.getSpecialization().getName());
            System.out.println("Przed ifem");
            System.out.println(medicalPoint.getAddress());
            if (subMedicalPoint != null) {
                System.out.println("Za ifem");
                if (subMedicalPoint.equals(medicalPoint)) {
                    tempDbDelete.delete(subMedicalPoint.getAddress(), subMedicalPoint.getSpecialization().getName());
                } else {
                    System.out.println("W elsie");
                    medicalPointRepository.delete(medicalPoint.getId());
                    medicalPointRepository.save(subMedicalPoint);
                    tempDbDelete.delete(subMedicalPoint.getAddress(), subMedicalPoint.getSpecialization().getName());
                }
            }
        }*/
        medicalPointRepository.deleteAll();
        List<MedicalPoint> medicalPointList = tempDbSelect.select();
        for (MedicalPoint medicalPoint: medicalPointList){
            medicalPointRepository.save(medicalPoint);
            //System.out.println("To drugie");
        }
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
    public @ResponseBody Iterable<MedicalPoint> addMedicalPoint(@RequestParam String name, @RequestParam String specialization, @RequestParam String adress)
    {
        //TODO update findmp
        //example path: http://localhost:8080/addmp?name=Fajna+Przychodnia&specialization=Ortopeda&adress=Warszawa;Ksiecia+Janusza;39
        //note the ';' between adress parts in the line above
        String[] adressParts = adress.split(";");

        MedicalPoint medicalPoint = medicalPointService.createAndLocalizeMedicalPoint(
                name,
                new Specialization(specialization),
                new Address(adressParts[0], adressParts[1], adressParts[2]));

        medicalPointRepository.save(medicalPoint);
        return medicalPointRepository.findAll();
    }

    @RequestMapping(value = "/removemp", method = RequestMethod.POST)
    public @ResponseBody Iterable<MedicalPoint> removeMedicalPoint(@RequestParam long ID)
    {
        //example path: http://localhost:8080/removemp?ID=1
        MedicalPoint medPoint = medicalPointRepository.findOne(ID);
        if(medPoint!=null) {
            medicalPointRepository.delete(ID);

        }
         return medicalPointRepository.findAll();
    }

    /** Just for testing, will be removed later */
    @RequestMapping("/findbysec")
    public @ResponseBody
    Iterable<MedicalPoint> findBySector(@RequestParam int x, @RequestParam int y)
    {
        return medicalPointRepository.findBySector(sectorRepository.findByXAndY(x, y));
    }

    @RequestMapping("/findClosest")
    public @ResponseBody Iterable<MedicalPoint> findClosest(@RequestParam String specialization, @RequestParam double lat, @RequestParam double lon)
    {
        //example path: http://localhost:8080/addmp?specialization=Okulista&lat=1&lon=1
        return findMedicalPointService.findMedicalPoints(new Specialization(specialization), lat, lon, 5);
    }
    @RequestMapping("/findClosestByAddress")
    public @ResponseBody Iterable<MedicalPoint> findClosestByAddress(@RequestParam String specialization, @RequestParam String address)
    {
        //example path: http://localhost:8080/addmp?specialization=Ortopeda&address=Warszawa;Ksiecia+Janusza;39
        String[] addressParts = address.split(";");
        //Address myAddress = new Address(addressParts[0], addressParts[1], addressParts[2]);
        Address myAddress = new Address("Warszawa", "Skoroszewska", "4");
        Location myLocation = locationService.fromAddress(myAddress);
        return findMedicalPointService.findMedicalPoints(new Specialization(specialization), myLocation.getLatitude(), myLocation.getLongitude(), 5);
       // return medicalPointRepository.findAll();
    }


}
