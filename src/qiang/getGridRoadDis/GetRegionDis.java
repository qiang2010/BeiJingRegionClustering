package qiang.getGridRoadDis;

import qiang.gridGene.GridGenerator;
import qiang.util.FileUtil;



/**
 * 
 * 获取任意两个格子之间的距离。
 * 
 * @author jq
 *
 */
public class GetRegionDis {

	public static void main(String[] args) {
		
		getAllRegionDis();
		check();
		
	}
	
	static void check(){
		
		for(int i =0;i<row; i++){
			for(int j =0;j <col ;j++){
				for(int x = 0 ; x < row ; x++){
					for(int y = 0 ; y<col;y++){
						if( i == x && j == y)continue;
						if(allRegionDis[i][j][x][y] <1){
							System.err.println("wrong i j x y\t"+ i +"\t"+j+"\t"+x+"\t"+y);
						}
					}
				}
				
			}
		}
		
		
	}
	
	static int row = GridGenerator.latCount;
	static int col = GridGenerator.lngCount;
	static int[][][][] allRegionDis ; 
	static String path = "E:\\北京分区聚类\\区域距离计算\\间距计算last.txt";
	public static int[][][][] getAllRegionDis(){
		
		allRegionDis = new int[row][col][row][col];
		FileUtil fileUtil = new FileUtil(path);
		String line ;
		String []splits;
		int i,j,x,y;
		while((line = fileUtil.readLine())!=null){
			splits = line.split("\\s+");
			i = Integer.parseInt(splits[0].trim());
			j = Integer.parseInt(splits[1].trim());
			x = Integer.parseInt(splits[4].trim());
			y = Integer.parseInt(splits[5].trim());
			allRegionDis[i][j][x][y] = allRegionDis[x][y][i][j] = Integer.parseInt(splits[8]);
		}
		return allRegionDis;
	}
	
}
