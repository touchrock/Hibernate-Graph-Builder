package algorithm;

import graph.Graph;
import graph.Path;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch<T> implements GraphSearch<T> {
	
	private Graph<T> graph = null;
	private Set<T> visited = new HashSet<T>();
	private Queue<T> queue = new LinkedList<T>();
	private Map<T, T> links = new HashMap<T, T>();
	private Map<String, Path<T>> cachedPaths = new HashMap<String, Path<T>>(); 
	
	public BreadthFirstSearch(Graph<T> graph) {
		this.graph = graph;
	}
	
	public boolean hasVertex(T startingVertex, T wantedVertex) {
		
		boolean result = false;
		
		queue.add(startingVertex);
		
		while (!queue.isEmpty()) {
			
			T currentVertex = queue.poll();
		
			if (currentVertex.equals(wantedVertex)) {
				result = true;
				break;
			}
			
			Iterable<T> neighbors = graph.getNeighbors(currentVertex);
			
			for (T neighbor : neighbors) {
				if(!this.visited.contains(neighbor)) {
					this.visited.add(neighbor);
					this.queue.add(neighbor);
				}
			}
		}
		
		visited.clear();
		queue.clear();
		return result;
	}
	
	public Path<T> findShortestPath(T startingVertex, T wantedVertex) {
		
		if (cachedPaths.get(startingVertex.toString() + wantedVertex.toString()) != null) {
			return cachedPaths.get(startingVertex.toString() + wantedVertex.toString());
		}
		
		T currentVertex = null;
		Path<T> path = null;
		
		queue.add(startingVertex);
		
		while (!queue.isEmpty()) {
			
			currentVertex = queue.poll();
		
			if (currentVertex.equals(wantedVertex)) {
				break;
			}
			
			for (T neighbor : graph.getNeighbors(currentVertex)) {
				if(!this.visited.contains(neighbor)) {
					this.visited.add(neighbor);
					this.queue.add(neighbor);
					links.put(neighbor, currentVertex);
				}
			}
		}
		
		if (currentVertex == null) {
			return path;
		}
		
		path = new Path<T>(currentVertex);

		currentVertex = links.get(currentVertex);
		
		while (currentVertex != null) {
			path = path.addToPath(currentVertex);
			currentVertex = links.get(currentVertex);
		}
		
		List<T> reversedPath = path.getPath();
		Collections.reverse(reversedPath);
		path = new Path<T>(reversedPath);
		
		cachedPaths.put(startingVertex.toString() + wantedVertex.toString(), path);
		return path;
	}
}
