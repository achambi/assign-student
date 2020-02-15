package bo.com.mondongo.assignstudent.pojos;

import java.util.ArrayList;
import java.util.List;

public class BoundingBoxManager {
    private List<BoundingBox> boundingBoxes;
    private List<MapPoint> mapPoints;

    public BoundingBoxManager(List<BoundingBox> boundingBoxes, List<MapPoint> mapPoints, double halfSideInMts) {
        this.boundingBoxes = boundingBoxes;
        this.mapPoints = mapPoints;
    }

    public List<MapPoint> studentsInClasses() {
        List<MapPoint> mapPointsFound = new ArrayList<>();
        for (BoundingBox boundingBox : boundingBoxes) {
            for (MapPoint mapPoint : mapPoints) {
                MapPoint mapPointFound = boundingBox.verifyPoint(mapPoint);
                if (mapPointFound != null) {
                    mapPointsFound.add(mapPointFound);
                }
            }
        }
        return mapPointsFound;
    }
}
