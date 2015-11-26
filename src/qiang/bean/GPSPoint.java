package qiang.bean;

public class GPSPoint {

	double lat;
	double lng;
	public GPSPoint(double lat,double lng){
		
		this.lat = lat;
		this.lng = lng;
		
	}
	
	@Override
	public String toString() {
		return "GPSPoint [lat=" + lat + ", lng=" + lng + "]";
	}
	public String toStringTab(){
		return lat+"\t"+lng;
	}

	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	
}
