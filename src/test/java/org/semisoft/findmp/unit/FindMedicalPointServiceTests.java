package org.semisoft.findmp.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.semisoft.findmp.FindMedicalPointApplication;
import org.semisoft.findmp.domain.*;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.service.FindMedicalPointService;
import org.semisoft.findmp.service.MedicalPointService;
import org.semisoft.findmp.service.SectorService;
import org.semisoft.findmp.unit.fake.ExpandableAreaServiceTestConfiguration;
import org.semisoft.findmp.unit.fake.FilterServiceTestConfiguration;
import org.semisoft.findmp.unit.fake.SectorServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
        FindMedicalPointApplication.class,
        SectorServiceTestConfiguration.class, //SectorService fake
        ExpandableAreaServiceTestConfiguration.class, //ExpandableAreaService fake
        FilterServiceTestConfiguration.class}) // FilterService fake
@RunWith(SpringJUnit4ClassRunner.class)

public class FindMedicalPointServiceTests {

    @MockBean
    private MedicalPointRepository medicalPointRepository;
    @Autowired
    private MedicalPointService medicalPointService;
    @Autowired
    private FindMedicalPointService findMedicalPointService;
    @Autowired //faked, no persistence
    private SectorService sectorService;

    private static Map<String, MedicalPoint> specMedicalPointMap = new HashMap<>();
    private List<MedicalPoint> medicalPoints = new ArrayList<>();

    @Before
    public void init() {
        Sector sector = sectorService.fromCoordinates(0, 0);

        Mockito.when(medicalPointRepository.findBySector(sector))
                .thenReturn(medicalPoints);

        if (specMedicalPointMap.isEmpty())
            createMedicalPoints("A", "B", "C");
    }

    private void createMedicalPoints(String... specs) {
        for (String spec : specs) {
            MedicalPoint medicalPoint = medicalPointService.createAndLocalizeMedicalPoint(
                    spec,
                    new Specialization(spec),
                    //address could be anything in case of mocking MedicalPointRepository
                    new Address("Warszawa", "Skoroszewska", "4"));

            specMedicalPointMap.put(spec, medicalPoint);
        }
    }

    @Test
    public void givenAB_WhenFindA_ThenAIsFound() {
        prepareMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("A");

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("A"), foundPoints.get(0));
    }

    @Test
    public void givenAB_WhenFindB_ThenBIsFound() {
        prepareMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("B");

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("B"), foundPoints.get(0));
    }

	@Test
    public void givenAB_WhenFindC_ThenNothingIsFound() {
        prepareMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("C");

        assertEquals(0, foundPoints.size());
    }

    @Test
    public void givenABBC_WhenFindB_ThenBFoundTwice() {
        prepareMedicalPoints("A", "B", "B", "C");

        List<MedicalPoint> foundPoints = findMedicalPoints("B");

        assertEquals(2, foundPoints.size());
        assertEquals(specMedicalPointMap.get("B"), foundPoints.get(0));
        assertEquals(specMedicalPointMap.get("B"), foundPoints.get(1));
    }

    @Test
    public void givenABBC_WhenFindC_ThenCFoundSingle() {
        prepareMedicalPoints("A", "B", "B", "C");

        List<MedicalPoint> foundPoints = findMedicalPoints("C");

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("C"), foundPoints.get(0));
    }

    @Test
    public void givenAB_WhenFindAInDifferentPlace_ThenNothingIsFound() {
        prepareMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("A", 5, 5);

        assertEquals(0, foundPoints.size());
    }

    @Test
    public void givenAB_WhenFindAInNearby_ThenAIsFound() {
        prepareMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("A", 0.01, 0.01);

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("A"), foundPoints.get(0));
    }

    private void prepareMedicalPoints(String... specs) {
        for (String spec : specs)
            medicalPoints.add(new MedicalPoint(specMedicalPointMap.get(spec)));
    }

    private List<MedicalPoint> findMedicalPoints(String spec) {
        return findMedicalPoints(spec, 0, 0);
    }

    private List<MedicalPoint> findMedicalPoints(String spec, double lat, double lon) {
        return findMedicalPointService.findMedicalPoints(
                new Specialization(spec), new Location(lat, lon), 5 );
    }
}
