package qiang.util;


public class SleepUtil {

	public static void sleep(long second){
		try {
			Thread.sleep(second*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
