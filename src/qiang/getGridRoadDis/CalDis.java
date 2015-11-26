package qiang.getGridRoadDis;

import java.lang.Thread.State;

import qiang.util.SleepUtil;

public class CalDis {

	public static void main(String[] args) {
		Thread calThread = new Thread(new DisCalThread());
		calThread.start();
		do{
			State stat = calThread.getState();
			if(stat == Thread.State.TERMINATED){
				calThread = new Thread(new DisCalThread());
				calThread.start();
			}else{
				SleepUtil.sleep(30);
			}
			
		}while(true);
	}
}
