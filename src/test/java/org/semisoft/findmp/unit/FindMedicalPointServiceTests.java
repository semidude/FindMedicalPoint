package org.semisoft.findmp.unit;

import org.junit.Test;
import org.mockito.Mockito;
import org.semisoft.findmp.domain.Address;
import org.semisoft.findmp.domain.MedicalPoint;
import org.semisoft.findmp.domain.Sector;
import org.semisoft.findmp.domain.Specialization;
import org.semisoft.findmp.domain.repository.MedicalPointRepository;
import org.semisoft.findmp.service.FindMedicalPointService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindMedicalPointServiceTests {

    private MedicalPointRepository medicalPointRepository;
    private FindMedicalPointService findMedicalPointService;
    private Map<String, MedicalPoint> specMedicalPointMap = new HashMap<>();
    private List<MedicalPoint> medicalPoints = new ArrayList<>();
    private Sector sector = new Sector(0, 0);

    public FindMedicalPointServiceTests() {

        medicalPointRepository = Mockito.mock(MedicalPointRepository.class);
        findMedicalPointService = new FindMedicalPointService(medicalPointRepository);

        Mockito.when(medicalPointRepository.findBySector(sector))
                .thenReturn(medicalPoints);
    }

	@Test
	public void givenAB_WhenFindA_ThenAIsFound() {
	    createMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("A");

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("A"), foundPoints.get(0));
	}

    @Test
    public void givenAB_WhenFindB_ThenBIsFound() {
        createMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("B");

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("B"), foundPoints.get(0));
    }

	@Test
    public void givenAB_WhenFindC_ThenNothingIsFound() {
        createMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("C");

        assertEquals(0, foundPoints.size());
    }

    @Test
    public void givenABBC_WhenFindB_ThenBFoundTwice() {
        createMedicalPoints("A", "B", "B", "C");

        List<MedicalPoint> foundPoints = findMedicalPoints("B");

        assertEquals(2, foundPoints.size());
        assertEquals(specMedicalPointMap.get("B"), foundPoints.get(0));
        assertEquals(specMedicalPointMap.get("B"), foundPoints.get(1));
    }

    @Test
    public void givenABBC_WhenFindC_ThenCFoundSingle() {
        createMedicalPoints("A", "B", "B", "C");

        List<MedicalPoint> foundPoints = findMedicalPoints("C");

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("C"), foundPoints.get(0));
    }

    @Test
    public void givenAB_WhenFindAInDifferentPlace_ThenNothingIsFound() {
        createMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("A", 5, 5);

        assertEquals(0, foundPoints.size());
    }

    @Test
    public void givenAB_WhenFindAInNearby_ThenAIsFound() {
        createMedicalPoints("A", "B");

        List<MedicalPoint> foundPoints = findMedicalPoints("A", 0.01, 0.01);

        assertEquals(1, foundPoints.size());
        assertEquals(specMedicalPointMap.get("A"), foundPoints.get(0));
    }

	private void createMedicalPoints(String... specs) {
        medicalPoints.clear();
        specMedicalPointMap.clear();

        for (String spec : specs) {
            MedicalPoint medicalPoint = new MedicalPoint(
                    spec,
                    new Specialization(spec),
                    //address could be anything in case of mocking MedicalPointRepository
                    new Address("Warszawa", "Skoroszewska", "4"));

            medicalPoints.add(medicalPoint);
            specMedicalPointMap.put(spec, medicalPoint);
        }
    }

    private List<MedicalPoint> findMedicalPoints(String spec) {
        return findMedicalPoints(spec, 0, 0);
    }

    private List<MedicalPoint> findMedicalPoints(String spec, double lat, double lon) {
        return findMedicalPointService.findMedicalPoints(
                new Specialization(spec), lat, lon );
    }
}
