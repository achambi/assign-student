package bo.com.mondongo.assignstudent.pojos;

public class DegreeAndRadConvertor {
    // degrees to radians
    public static double Deg2rad(double degrees) {
        return Math.PI*degrees/180.0;
    }

    // radians to degrees
    public static double Rad2deg(double radians) {
        return 180.0*radians/Math.PI;
    }
}
