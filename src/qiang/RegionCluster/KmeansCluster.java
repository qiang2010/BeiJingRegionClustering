package qiang.RegionCluster;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import qiang.bean.KMeansClusterBean;
import qiang.bean.OneCell;
import qiang.getGridRoadDis.GetRegionDis;
import qiang.gridGene.GridGenerator;
import qiang.util.FileUtil;

public class KmeansCluster {

	public static void main(String[] args) {
		
		
		Set<KMeansClusterBean> pivo = new KmeansCluster().kmeans();
		File f = new File(ansPath);
		f.deleteOnExit();
		f.mkdirs();
//		int count = 1;
//		for(KMeansClusterBean cur:pivo){
//			FileUtil curF = new FileUtil(ansPath+count+".txt");
//			for(OneCell cell:cur.getCluserCells()){
//				curF.writeLine(cell.getCenterLngLatTab());
//			}
//			count++;
//		}
		int count = 0;
		FileUtil curF = new FileUtil(ansPath+"centersGPS.txt");
		for(KMeansClusterBean cur:pivo){
			cur.calCenter();
//			curF.writeLine(count+"\t"+cur.getRow()+"\t"+cur.getCol()+"\t"+cur.getCenterLat()+"\t"+cur.getCenterLng());
			curF.writeLine(cur.getCenterLat()+"\t"+cur.getCenterLng());
//			for(OneCell cell:cur.getCluserCells()){
//				curF.writeLine(count+"\t"+cell.getRow()+"\t"+cell.getCol()+"\t"+cell.getCenterLngLatTab());
//			}
			count++;
		}
		
	}
	
	static String ansPath = "E:\\北京分区聚类\\聚类结果\\";
	static long disThreshold = 1000; // 质心变化范围。
	
	public Set<KMeansClusterBean> kmeans(){
		
		// 获取初始质心
		Set<KMeansClusterBean> pivo = initCluster();
		Set<KMeansClusterBean> newPivo;
		int deep =0;
		System.out.println(pivo.size());
		KMeansClusterBean minCluser = null;
		long minDis = Long.MAX_VALUE;
		long curDis;
		for(;;){
			System.out.println(deep++);
			newPivo = new HashSet<KMeansClusterBean>();
			for(int i = 0;i< this.row;i++){
				for(int j =0; j < this.col;j++){
					minDis = Long.MAX_VALUE;
					for(KMeansClusterBean curC:pivo){
						int x = curC.getRow();
						int y = curC.getCol();
						curDis = disBetweentTwoCell(i, j, x, y);
						if(curDis < minDis){
							minDis = curDis;
							minCluser = curC;
						}
					}
					minCluser.addOneCell(grids[i][j]);
				}
			}
			// 
			int cou = 0 ;
			for(KMeansClusterBean curC:pivo){
				KMeansClusterBean tempNewKM = new KMeansClusterBean();
				//System.out.println("curC.getCluserCells()"+curC.getCluserCells().size());
				tempNewKM.setCluserCells(curC.getCluserCells());
				tempNewKM.calCenter();
				newPivo.add(tempNewKM);
				if(disBetweenTwoCluser(curC,tempNewKM)< disThreshold){
					cou++;
				}
			}
			if(cou ==  newPivo.size()){
				return newPivo;
			}
			// 否则继续迭代，需要清空簇中的list
			pivo = newPivo;
			for(KMeansClusterBean curC:pivo){
				curC.getCluserCells().clear();
			}
		}
	}
	
	public long disBetweenTwoCluser(KMeansClusterBean km1, KMeansClusterBean km2){
		return disBetweentTwoCell(km1.getRow(), km1.getCol(), km2.getRow(), km2.getCol());
	}
	
	static int[][][][] allRegionDis = GetRegionDis.getAllRegionDis();
	
	
	public long disBetweentTwoCell(int i ,int j ,int x,int y){
		//if(i == x && j == y) return 0;
		if(isRowColValid(i, j) && isRowColValid(x, y))
			return allRegionDis[i][j][x][y];
		System.err.println("wrong i j x y"+ i +"\t"+j+"\t"+x+"\t"+y);
		return -1;
		
	}
	boolean isRowColValid(int i ,int j ){
		if(i < 0 || j < 0) return false;
		if(i > GridGenerator.latCount-1 || j > GridGenerator.lngCount-1)return false;
		return true;
	}
	
	static OneCell[][] grids = GridGenerator.getGridCells();
	int row;
	int col;
	Set<KMeansClusterBean> pivo;
	// 初始化质心
	public  Set<KMeansClusterBean> initCluster(){
		pivo = new HashSet<KMeansClusterBean>();
		// 初始20个格子。 7 *8 构成一个格子
		this.row = grids.length;
		this.col = grids[0].length;
		KMeansClusterBean tempKM;
		for(int i = 0 ; i < row ; i+=7){
			for(int j = 0 ; j < col; j+=8){
				tempKM = new KMeansClusterBean();
				tempKM.setCenterLat(grids[i][j].getCellLeftTopLat()-OneCell.latDiff*7/2);
				tempKM.setCenterLng(grids[i][j].getCellLeftTopLng()+OneCell.lngDiff*4);
				tempKM.setCluserCenter(tempKM.getCenterLat(), tempKM.getCenterLng());
				pivo.add(tempKM);
			}
		}
		return pivo;
	}
}
