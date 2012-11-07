import java.awt.*;
import java.util.*;
import java.io.*;

import javax.swing.*;


public class Maze {
	static int width,height;
	static int[][] MyMaze;
	static int[][] index;
	static DisjSets mazeSet;
	static Vertex[][] mazeVertex;
	static boolean[][] solution;
	static String directions = "";
	public static void main(String[] args)throws
	FileNotFoundException{
		Scanner s = new Scanner(System.in);
		/*
		System.out.print("Read from existing file? Y/N: ");
		if(s.next().compareToIgnoreCase("y")==0){
			System.out.print("Enter File Name: ");
			try{
				MyMaze = reader(s.next());
			}
			catch (FileNotFoundException e){
				System.out.println("Error: File Not Found.");
				return;
			}
		}
		else{	
		*/
			System.out.print("Enter a width: ");
			width = s.nextInt();
			System.out.print("Enter a height: ");
			height = s.nextInt();
			/*
			System.out.print("Enter file name to save to: ");
			String mazefile = s.next();
			*/
			MyMaze = new int[height][width];
			index = new int[height][width];
			solution = new boolean[height][width];
			int temp = 0;
			for(int i = 0; i<height;i++){
				for(int j = 0; j < width; j++){
					index[i][j] = temp;
					MyMaze[i][j] = 3;
					temp++;
				}
			}
			mazeSet = new DisjSets(height*width);
			createMaze();
			//writer(mazefile);
		//}
		mazeVertex = new Vertex[height][width];
		createVertices();
		dijkstra(mazeVertex[0][0]);
		solve(mazeVertex[height-1][width-1]);
		
		JFrame frame = new JFrame();
		frame.setTitle("Erica's Super Amazing Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new HelloMaze(MyMaze,10));
		frame.pack();
		frame.setVisible(true);
		
		JFrame frame3 = new JFrame();
		frame3.setTitle("Erica's Super Amazing Maze");
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.getContentPane().add(new HelloMaze(MyMaze,10));
		frame3.pack();
		frame3.setVisible(true);
		
	}
	public static void createMaze(){ 
		boolean done = false;
		while(!done){			
			
			int root1 = (int) (Math.random()*10000)%(height*width);
			int rt1H = getHeight(root1);
			int rt1W = getWidth(root1);
			while(adjVal(root1)<0){
				root1 = (int) (Math.random()*10000)%(height*width);
			}
			int root2 = adjVal(root1);
			
				
			int rt2H = getHeight(root2);
			int rt2W = getWidth(root2);
			int minW = getWidth(Math.min(root1, root2));
			int minH = getHeight(Math.min(root1, root2));
			if(!(mazeSet.find(root1)==mazeSet.find(root2))){
				mazeSet.union(mazeSet.find(root1), mazeSet.find(root2));
				if(rt1H==rt2H){
					if(MyMaze[rt1H][minW]==3)
						MyMaze[rt1H][minW]=2;
					else
						MyMaze[rt1H][minW]=0;
				}
				else if(rt1W==rt2W){
					if(MyMaze[minH][rt1W]==3)
						MyMaze[minH][rt1W]=1;
					else
						MyMaze[minH][rt1W]=0;
				}
			}
			for(int i = 0;i<height*width;i++){
				if(mazeSet.s[i]==-1*(height*width)){
					done = true;
				}
			}
		}
	}
	
	public static void createVertices(){
		for(int i = 0;i<height;i++){
			for(int j=0;j<width;j++){
				mazeVertex[i][j] = new Vertex();
				mazeVertex[i][j].index = index[i][j];
			}
		}
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				if(i!=0){
					if(MyMaze[i-1][j]==0 || MyMaze[i-1][j]==1){
						mazeVertex[i][j].makeAdj(mazeVertex[i-1][j]);
					}	
				}
				if(j!=0){
					if(MyMaze[i][j-1]==0 || MyMaze[i][j-1]==2){
						mazeVertex[i][j].makeAdj(mazeVertex[i][j-1]);
					}	
				}
				if(i!=(height-1)){
					if(MyMaze[i][j]==0 || MyMaze[i][j]==1){
						mazeVertex[i][j].makeAdj(mazeVertex[i+1][j]);
					}	
				}
				if(j!=(width-1)){
					if(MyMaze[i][j]==0 || MyMaze[i][j]==2){
						mazeVertex[i][j].makeAdj(mazeVertex[i][j+1]);
					}	
				}
			}
		}
	}
	
	public static void dijkstra(Vertex start){
		start.dist = 0.0;
		for(int i =0;i<mazeVertex.length;i++){
			for(int j=0;j<mazeVertex[i].length;j++){
				Vertex v = smallUnknown();
				if(v == null){
					break;
				}
				v.known = true;
				while(!v.adj.isEmpty()){
					Vertex w = v.adj.remove();
					if(!w.known){
						if((v.dist+1)<w.dist){
							w.dist = v.dist+1;
							w.path = v;
						}
					}
				}
			}
		}
	}
	
	public static Vertex smallUnknown(){
		Double temp = Double.POSITIVE_INFINITY;
		Vertex t = new Vertex();
		for(int i=0;i<mazeVertex.length;i++){
			for(int j=0;j<mazeVertex[i].length;j++){
				if(!mazeVertex[i][j].known){
					if(temp>mazeVertex[i][j].dist){
						t = mazeVertex[i][j];
						temp = t.dist;
					}
				}
			}
		}
		if(temp == Double.POSITIVE_INFINITY){
			return null;
		}
		return t;
	}
	public static void solve(Vertex v){
		if(v.path!=null){
			solve(v.path);
		}
		if(v.path!=null){
			if(getHeight(v.index)==getHeight(v.path.index)){
				if(getWidth(v.index)>getWidth(v.path.index)){
					directions+="E";
				}
				else if(getWidth(v.index)<getWidth(v.path.index)){
					directions+="W";
				}
			}
			else if(getWidth(v.index)==getWidth(v.path.index)){
				if(getHeight(v.index)>getHeight(v.path.index)){
					directions+="S";
				}
				else if(getHeight(v.index)<getHeight(v.path.index)){
					directions+="N";
				}
			}
		}
		solution[getHeight(v.index)][getWidth(v.index)] = true;
	}
		
	public static boolean adjCheck(int a, int b){
		if(getHeight(a)==getHeight(b)){
			if(Math.abs(getWidth(a)-getWidth(b)) == 1)
				return true;
		}
		if(getWidth(a)==getWidth(b)){
			if(Math.abs(getHeight(a)-getHeight(b))==1)
				return true;
		}
		return false;
	}
	
	public static int adjVal(int cell){
		int cellW = getWidth(cell);
		int cellH = getHeight(cell);
		int posCount = 0;
		int[] temp = new int[4];
		for(int i = 0; i<temp.length;i++)
			temp[i] = -1;
		if(cellH!=0){
			if(MyMaze[cellH-1][cellW]!=0 || MyMaze[cellH-1][cellW]!=1){
				temp[posCount] = index[cellH-1][cellW];
				posCount++;
				
			}
		}
		if(cellH!=index.length-1){
			if(MyMaze[cellH][cellW]!=0 || MyMaze[cellH][cellW]!=1){
				temp[posCount] = index[cellH+1][cellW];
				posCount++;
			}
		}
		if(cellW!=0){
			if(MyMaze[cellH][cellW-1]!=0 || MyMaze[cellH][cellW]!=2){
				temp[posCount] = index[cellH][cellW-1];
				posCount++;
			}
		}
		if(cellW!=index[0].length-1){
			if(MyMaze[cellH][cellW]!=0 || MyMaze[cellH][cellW]!=2){
				temp[posCount] = index[cellH][cellW+1];
				posCount++;
			}
		}
		if(temp[0]<0 && temp[1]<0 && temp[2]<0 && temp[3]<0)
			return -1;
		int tempDex = (int) (Math.random()*10)%posCount;
		while(temp[tempDex]<0){
			tempDex = (int) (Math.random()*10)%posCount;
		}
		return temp[tempDex];
	}
	
	public static boolean diffSets(int a, int b){
		if(mazeSet.find(a)== mazeSet.find(b)){
			return false;
		}
		return true;
	}
	
	public static int getHeight(int cellIndex){
		for(int i = 0; i<index.length;i++){
			for(int j = 0; j<index[i].length;j++){
				if(index[i][j] == cellIndex)
					return i;
			}
		}
		return -1;
	}
	
	public static int getWidth(int cellIndex){
		for(int i = 0; i<index.length;i++){
			for(int j = 0; j<index[i].length;j++){
				if(index[i][j] == cellIndex)
					return j;
			}
		}
		return -1;
	}
	
	public static int[][] reader(String file)throws
FileNotFoundException{
		Scanner scan = new Scanner(new File(file));
		int ht = scan.nextInt();
		int wdth = scan.nextInt();
		int[][] maze = new int[ht][wdth];
		for(int i = 0; i<ht;i++){
			for(int j=0;j<wdth;j++){
				maze[i][j] = scan.nextInt();
			}
		}
		return maze;
	}
	
	public static void writer(String filename)throws FileNotFoundException{
		PrintWriter writefile = new PrintWriter(filename);
		writefile.print(height+ " "+width);
		writefile.println();
		writer(writefile);
		writefile.flush();
		writefile.close();
	}
	public static void writer(PrintWriter wrtr){
		for(int i = 0; i<height; i++){
			for(int j = 0; j<width; j++){
				wrtr.print(MyMaze[i][j]+" ");
			}
			wrtr.println();
		}
	}

	
	static class HelloMaze extends JPanel {
		private int [][] a;
		private int boxsize;
		public HelloMaze(int [][] array, int boxsize) {
			a = array;
			this.boxsize = boxsize;
			setPreferredSize(new Dimension((a[0].length + 1) * boxsize,
					(a.length + 1) * boxsize));
			}
		public void paintComponent(Graphics g) {
			int startx = 5;
			int currenty = startx;
			g.setColor(Color.YELLOW);
			for(int i=0;i<a.length;i++){
				int currentx = startx;
				for(int j=0; j<a[i].length;j++){
					if(solution[i][j])
						g.fillRect(currentx, currenty, boxsize, boxsize);
					currentx += boxsize;
				}
				currenty+= boxsize;
			}
			
			g.setColor(Color.BLACK);
			
			currenty = startx;
			for(int i = 0; i<a.length;i++){
				int currentx = startx;
				for (int j = 0 ; j < a[i].length ; j++){
					currentx += boxsize;
					if((a[i][j]==1||a[i][j]==3)&& j!=a[i].length-1)
						g.drawLine(currentx,currenty,currentx,currenty+boxsize);
				}
				currenty+=boxsize;
			}
			g.drawLine(startx+boxsize,startx,boxsize*a[0].length+5,startx);
			g.drawLine(startx,boxsize*a.length+5,boxsize*a[0].length+5,boxsize*a.length+5);
			g.drawLine(startx,startx,startx,boxsize*a.length+5);
			g.drawLine(boxsize*a[0].length+5,startx,boxsize*a[0].length+5,boxsize*a.length+5-boxsize);
			currenty = startx;
			for(int i = 0;i<a.length;i++){
				int currentx = startx;
				currenty+=boxsize;
				for (int j = 0 ; j < a[0].length ; j++){
					if((a[i][j] == 2) ||(a[i][j] == 3))
						g.drawLine(currentx,currenty,currentx+boxsize,currenty);
					currentx += boxsize;
				}
				
			}
		}
			
	}
	
}
