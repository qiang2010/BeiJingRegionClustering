package qiang.bean;

public class OneCell {

	double cellLeftTopLat;
	double cellLeftTopLng;
	
	double centerLat;
	double centerLng;
	public static double latDiff = 0.009;
	public static double lngDiff = 0.012;
	
	int id;
	int row;
	int col;
	public OneCell(int row,int col,double cellLeftTopLat,double cellLeftTopLng){
		this.row = row;
		this.col = col;
		this.cellLeftTopLat = cellLeftTopLat;
		this.cellLeftTopLng = cellLeftTopLng;
		this.centerLat =  cellLeftTopLat - latDiff/2;
		this.centerLng =  cellLeftTopLng + lngDiff/2;
	}
	public String getCenterLngLatTab(){
		return this.centerLng+"\t"+this.centerLat;
	}
	public double getCellLeftTopLat() {
		return cellLeftTopLat;
	}
	public void setCellLeftTopLat(double cellLeftTopLat) {
		this.cellLeftTopLat = cellLeftTopLat;
	}
	public double getCellLeftTopLng() {
		return cellLeftTopLng;
	}
	public void setCellLeftTopLng(double cellLeftTopLng) {
		this.cellLeftTopLng = cellLeftTopLng;
	}
	public double getCenterLat() {
		return centerLat;
	}
	public void setCenterLat(double centerLat) {
		this.centerLat = centerLat;
	}
	public double getCenterLng() {
		return centerLng;
	}
	public void setCenterLng(double centerLng) {
		this.centerLng = centerLng;
	}
	public static double getLatDiff() {
		return latDiff;
	}
	public static void setLatDiff(double latDiff) {
		OneCell.latDiff = latDiff;
	}
	public static double getLngDiff() {
		return lngDiff;
	}
	public static void setLngDiff(double lngDiff) {
		OneCell.lngDiff = lngDiff;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
}
