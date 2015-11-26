package qiang.getGridRoadDis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import qiang.bean.OneCell;
import qiang.gridGene.GridGenerator;
import qiang.util.FileUtil;
import qiang.util.SendPost;
import qiang.util.SleepUtil;

public class RoadDisBetweenGrids {

	public static void main(String[] args) {
//		List<Integer> ans = jsonDecode("");
//		System.out.println("s:"+ans.size());
//		for(int a:ans){
//			System.out.println(a);
//		}
		calRoadDis();
	}
	
	
	static  List<String> keyList = keylist();
	static String url = "http://restapi.amap.com/v3/distance?";
	//origins=116.481028,39.989643|116.39,39.1|116.59,39.2|114.465302,40.004717
		//&destination=114.465302,40.004717&output=json&key=<用户的key>
	static int validKeyIndex = 0;
	static int postCount = 0;
	static List<String> keylist(){
		keyList = new ArrayList<>();
		keyList.add("46518cf0edfed35a34f09815c0d94cdc");
		keyList.add("83c01ccfdea8d9c05789d2722ce36e9e");
		keyList.add("777598131cf7fc0d9215ea31628ef73d");
		keyList.add("467015b1e013218daf7c46a724afebde");

		keyList.add("820a5179cbfadf9c41235afa328e99fb");
		keyList.add("e87df10d114744266552ac4a3cbec576");
		keyList.add("ab99c17bde35f13c0c0b4e1299c67f82");
		keyList.add("318823b845dff4a6c93337fe385b9877");

		keyList.add("80d0beee8b3d1dca42e97d6d40d91fd9");
		keyList.add("3babb91e7d374f5f16196fc1690c3f52");
		keyList.add("e9878064ee9b470bd92b5b53f1584943");
		keyList.add("2ec821ba711f1212608c114c25c147f7");

		keyList.add("0a0a6da1a071bc9cb0477e2bda797fad");
		keyList.add("c42901c2bbf4fae8cf955a1e812fc85d");
		keyList.add("705e80dea475f1de4f139bf1c86f0479");
		keyList.add("0e8d686d4f4d049f2fd1a79d27e18280");
		
		
		
		return keyList;
	}
	static String ansPath = "E:\\北京分区聚类\\区域距离计算\\";
	public static void calRoadDis(){
		
		Map<String,String> params = new HashMap<>();
		params.put("output", "json");
		// 获取所有的格子的中心。
		 OneCell[][] grids =  GridGenerator.getGridCells();
		 
		 int row = grids.length;
		 int col = grids[0].length;
		 int lastI =0;
		 int lastJ = 0;
		 int lastX = 0;
		 int lastY = 0;
		 // 读取历史数据
		 FileUtil fileUtil = new FileUtil(ansPath+"dis.txt");
		 String tempL,lastLine= null;
		 String [] splits;
		 while((tempL = fileUtil.readLine())!= null){
			 //splits = tempL.split("\\s+");
			 lastLine = tempL;
		 }
		 boolean log = false;
		 if(lastLine != null){
			 splits = lastLine.split("\\s+");
			 lastI = Integer.parseInt(splits[0]);
			 lastJ = Integer.parseInt(splits[1]);
			 lastX = Integer.parseInt(splits[4]);
			 lastY = Integer.parseInt(splits[5]);
			 log = true;
		 }
		 boolean first = true;
		 int i;
		 int j = lastJ;
		 for( i =lastI;i < row; i++){
			 for( ;j<col;j++){
				 params.put("destination", String.format("%.6f",grids[i][j].getCenterLng())+","+String.format("%.6f",grids[i][j].getCenterLat()));
				 int count = 0 ;
				 StringBuilder origin = new StringBuilder();
				 List<String> lines = new ArrayList<>();
				 int x = i;
				 int y = j+1;
				 if(log){
					 x = lastX;
					 y = lastY+1;
					 log = false;
				 }
				 for(;x<row ;x++){
					 for(;y<col;y++){
						 origin.append(String.format("%.6f",grids[x][y].getCenterLng())+","+String.format("%.6f",grids[x][y].getCenterLat())+"|");
						 //System.out.println(origin.toString());
						 lines.add(x+"\t"+y+"\t"+grids[x][y].getCenterLng()+"\t"+grids[x][y].getCenterLat());
						 count++;
						 if(count==30){
							 origin.deleteCharAt(origin.length()-1);
							 params.put("origins",origin.toString());
							 List<Integer> dis = null;
							 while(dis== null || dis.size()==0){
								  String postUrl = buildPostUrl(params); 
								 // System.out.println(postUrl);
								  if(!first){
									  SleepUtil.sleep(5);
								  }
								  first = false;
								  String ans = SendPost.sendPost(postUrl);
								  postCount++;
								  dis = jsonDecode(ans,postUrl);
							 }
							 // 写文件
							 writeToFile(i, j, grids, lines, dis);
							 lines = new ArrayList<>();
							 origin = new StringBuilder();
							 count =0;
						 }
					 }
					 y=0;
				 }
				 // 剩余的
				 if(count !=0 && count <100){
					 origin.deleteCharAt(origin.length()-1);
					 List<Integer> dis = null;
					 params.put("origins",origin.toString());
					 while(dis== null || dis.size()==0){
						  String postUrl = buildPostUrl(params); 
						  SleepUtil.sleep(15);
						  String ans = SendPost.sendPost(postUrl);
						  dis = jsonDecode(ans,postUrl);
					 }
					 // 写文件
					 writeToFile(i, j, grids, lines, dis); 
				 }
				 
			 }
			 j=0;
		 }
	}
	
	
	static void writeToFile(int i,int j,OneCell[][] grids,List<String> lines,List<Integer> dis){
		
		FileUtil fileUtil = new FileUtil(ansPath+"dis.txt", true);
		int size = lines.size();
		String ij = grids[i][j].getCenterLng()+"\t"+grids[i][j].getCenterLat();
		for(int m = 0 ; m < size;m++){
			fileUtil.writeLine(i+"\t"+j+"\t"+ij+"\t"+lines.get(m)+"\t"+dis.get(m));
		}
		fileUtil.close();
	}
	
	static List<Integer> jsonDecode(String json,String url){
		//String test = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"1000\",\"results\":[{\"origin_id\":\"1\",\"dest_id\":\"1\",\"distance\":\"261565\",\"duration\":\"18960\"},{\"origin_id\":\"2\",\"dest_id\":\"1\",\"distance\":\"3391\",\"duration\":\"540\"}]}";
		JSONObject jsonO = JSONObject.fromObject(json);
		String info = jsonO.getString("info");
		JSONArray results ;
		List<Integer> dis = new ArrayList<>();
		if("OK".equals(info)){
			results = jsonO.getJSONArray("results");
			JSONObject tempObject;
			System.out.println("ss"+results.size());
			for(int i = 0 ; i < results.size();i++){
				tempObject = results.getJSONObject(i);
				dis.add(Integer.parseInt(tempObject.getString("distance")));
			}
		}else{
			System.err.println(info);
			
			if("IP_QUERY_OVER_LIMIT,PLEASE CONTACTapi@autonavi.com".equals(info)){
				validKeyIndex++;
			}
			validKeyIndex++;
			validKeyIndex %= keyList.size();
			System.out.println(url);
			return null;
		}
		// 轮换key
		if(postCount > 0){
			//postCount = 0 ;
			validKeyIndex++;
			validKeyIndex %= keyList.size();
		}
		return dis;
	}
	
	
	static String buildPostUrl(Map<String,String> params){
		StringBuilder uurl = new StringBuilder();
		uurl.append(url);
		uurl.append("origins=");
		uurl.append(params.get("origins"));
		uurl.append("&");
		uurl.append("destination=");
		uurl.append(params.get("destination"));
		uurl.append("&");
		uurl.append("output=");
		uurl.append(params.get("output"));
		uurl.append("&");
		uurl.append("key=");
		uurl.append(keyList.get(validKeyIndex));
		return uurl.toString();
	}
	
}
