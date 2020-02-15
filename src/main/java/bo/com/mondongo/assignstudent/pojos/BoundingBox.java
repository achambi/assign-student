package bo.com.mondongo.assignstudent.pojos;

public class BoundingBox {
    public MapPoint minPoint;
    public MapPoint maxPoint;

    public BoundingBox(MapPoint minPoint, MapPoint maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    public MapPoint verifyPoint(MapPoint mapPoint) {
        if (mapPoint.getLatitude() >= minPoint.getLatitude() &&
            mapPoint.getLatitude() <= maxPoint.getLatitude() &&
            mapPoint.getLongitude() >= minPoint.getLongitude() &&
            mapPoint.getLongitude() <= maxPoint.getLongitude()) {
            return mapPoint;
        } else {
            return null;
        }
    }
}
