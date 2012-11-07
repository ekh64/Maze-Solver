import java.util.*;
public class Vertex {
	public LinkedList<Vertex> adj;
	public boolean known;
	public Double dist;
	public Vertex path;
	public int index;
	
	public Vertex(){
		adj = new LinkedList();
		dist = Double.POSITIVE_INFINITY;
		known = false;
	}
	
	public void makeAdj(Vertex a){
		adj.add(a);
	}
}
