package qiang.util;

import qiang.bean.GPSPoint;

public class TwoGPSDis {
	
	public static void main(String[] args) {
		
		GPSPoint p1 = new GPSPoint(40.0311978465,116.1658156035);
		GPSPoint p2 = new GPSPoint(40.0311978465,116.1778156035);
		System.out.println(twoGPSDisInMeter(p1, p2));
		
		
	}
		
	
	public static double twoGPSDisInMeter(GPSPoint p1,GPSPoint p2){
		return haversineInM(p1.getLat(), p1.getLng(), p2.getLat(), p2.getLng());
	}
	
	public static double twoGPSDisInKM(GPSPoint p1,GPSPoint p2){
		return haversineInKM(p1.getLat(), p1.getLng(), p2.getLat(), p2.getLng());
	}
	   
	
	static final double _eQuatorialEarthRadius = 6378.1370D;
	static final double _d2r = (Math.PI / 180D);

	public static int haversineInM(double lat1, double long1, double lat2, double long2) {
        return (int) (1000D * haversineInKM(lat1, long1, lat2, long2));
    }

    public static double haversineInKM(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;

        return d;
    }
}
