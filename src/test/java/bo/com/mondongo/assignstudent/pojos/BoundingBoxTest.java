package bo.com.mondongo.assignstudent.pojos;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class BoundingBoxTest {

    private List<BoundingBox> classes;
    private double halfSideInMts;

    @Before
    public void setUp() {
        halfSideInMts = 10;
        //Principles of computational geo-location analysis
        MapPoint point = new MapPoint(34.069140, -118.442689);
        BoundingBox engineeringClassroom = point.createBoundingBox(halfSideInMts);

        point = new MapPoint(34.069585, -118.441878);
        BoundingBox geologyClassroom = point.createBoundingBox(halfSideInMts);

        point = new MapPoint(34.069742, -118.441312);
        BoundingBox psychologyClassroom = point.createBoundingBox(halfSideInMts);

        point = new MapPoint(34.070223, -118.440193);
        BoundingBox musicClassroom = point.createBoundingBox(halfSideInMts);

        point = new MapPoint(34.071528, -118.441211);
        BoundingBox humanitiesClassroom = point.createBoundingBox(halfSideInMts);

        classes = Arrays.asList(engineeringClassroom,
                                geologyClassroom,
                                psychologyClassroom,
                                musicClassroom,
                                humanitiesClassroom
        );
    }

    @Test
    public void testCalculateExample1() {
        {

            List<MapPoint> students = Arrays.asList(
                //john_student - engineering
                new MapPoint(34.069149, -118.442639),
                //jane_student - geology
                new MapPoint(34.069601, -118.441862),
                //pam_student - humanities
                new MapPoint(34.071513, -118.441181)
            );

            List<MapPoint> mapPointsFound = new BoundingBoxManager(
                classes,
                students,
                halfSideInMts
            ).studentsInClasses();

            assertNotNull(mapPointsFound);
            assertEquals(students.get(0), mapPointsFound.get(0));
            assertEquals(students.get(1), mapPointsFound.get(1));
            assertEquals(students.get(2), mapPointsFound.get(2));
        }
    }

    @Test
    public void testCalculateExample2() {
        {

            List<MapPoint> students = Arrays.asList(
                //john_student - engineering
                new MapPoint(34.069849, -118.443539),
                //jane_student - geology
                new MapPoint(34.069901, -118.441562),
                //pam_student - humanities
                new MapPoint(34.071523, -118.441171)
            );

            List<MapPoint> mapPointsFound = new BoundingBoxManager(
                classes,
                students,
                halfSideInMts
            ).studentsInClasses();

            assertNotNull(mapPointsFound);
            assertEquals(students.get(2), mapPointsFound.get(0));
        }
    }
}