package graph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Graph<T> {
	
	private Map<T, Set<T>> edges = new HashMap<T, Set<T>>();
	
	public void addEdge(T src, T dest) {
		
		Set<T> srcNeighbors = this.edges.get(src);
		
		if (srcNeighbors == null) {
			
			srcNeighbors = new HashSet<T>();
			this.edges.put(src, srcNeighbors);
		}
		
		srcNeighbors.add(dest);
	}
	
	public Iterable<T> getNeighbors(T vertex) {
		Set<T> neighbors = this.edges.get(vertex);
		
		if (neighbors == null) {
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableCollection(neighbors);
		}
		
	}
	
	public int size() {
		
		Set<T> uniqueNodes = new HashSet<T>();
		
		for (T key : this.edges.keySet()) {
			uniqueNodes.add(key);
			uniqueNodes.addAll(this.edges.get(key));
		}
		
		return uniqueNodes.size();
	}
	
	public void printGraph() {
		
		Queue<T> queue = new LinkedList<T>();
		queue.addAll(edges.keySet());
		
		while (!queue.isEmpty()) {
			T next = queue.poll();
			System.out.println(next);
			printLine(next, queue, 1);
		}
	}
	
	private void printLine(T key, Queue queue, int level) {

		if (edges.get(key) == null) {
			return;
		}
		
		for (T neighbor : edges.get(key)) {
			queue.remove(neighbor);
			System.out.println(StringUtils.repeat('-', level) + neighbor.toString());
			printLine(neighbor, queue, level + 1);
		}
	}
}
