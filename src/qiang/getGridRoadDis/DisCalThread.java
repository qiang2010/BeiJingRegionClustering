package qiang.getGridRoadDis;

public class DisCalThread implements Runnable{

 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		RoadDisBetweenGrids.calRoadDis();
	}

}
