package algorithm;

import graph.Path;

public interface GraphSearch<T> {
	
	public Path<T> findShortestPath(T sourceVertex, T targetVertex);
	
	public boolean hasVertex(T sourceVertex, T targetVertex);
	
}
