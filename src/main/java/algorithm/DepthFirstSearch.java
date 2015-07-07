package algorithm;

import graph.Graph;
import graph.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DepthFirstSearch {
	
	private List<String> currentChain = null;
	private Map<String, Set<Path>> cachedPaths = new HashMap<String, Set<Path>>();
	private Map<String, Set<String>> childrenExecuted = new HashMap<String, Set<String>>();
	
	private List<String> visited = new ArrayList<String>();
	private Graph graph;
	private String root;
	
	public DepthFirstSearch(Graph graph) {
		this.graph = graph;
	}
	
	public boolean hasVertex(String startingVertex, String wantedVertex) {
		
		boolean result = findVertex(startingVertex, wantedVertex);
		visited.clear();
		return result;
	}
	
	private boolean findVertex(String vertex, String wantedVertex) {
		
		boolean result = false;
		
		if (vertex.equals(wantedVertex)) {
			return true;
		}
		
		visited.add(vertex);
		Iterable<String> neighbors = graph.getNeighbors(vertex);
		
		for (String neighbor : neighbors) {
			
			if(visited.contains(neighbor)) {
				continue;
			}
			else {
				result = findVertex(neighbor, wantedVertex);
			}
		}
		
		return result;
	}
	
	public Path findShortestPath(String vertex, String wantedVertex) {
		return null;
	}
	
	public Set<Path> findPaths(String vertex, String wantedVertex) {
		
		root = vertex;
		Set<Path> paths = findPaths(vertex, wantedVertex, null);
		Set<Path> reversedPaths = new HashSet<Path>();
		
		for (Path path : paths) {
			List<String> reversedPath = path.getPath();
			Collections.reverse(reversedPath);
			reversedPaths.add(new Path(reversedPath));
		}
		
		printPaths(reversedPaths);
		return reversedPaths;
	}
	
	private Set<Path> findPaths(String currentVertex, String wantedVertex, String previousVertex) {
		
		Set<Path> childPaths = null;
		Set<Path> paths = new HashSet<Path>();
		
		if (currentVertex.equals(wantedVertex)) {
			Path path = new Path(currentVertex);
			paths.add(path);
			return paths;
		}
		
		if (currentChain != null && !currentChain.isEmpty()) {
			currentChain.add(currentVertex);
		}
		
		Iterable<String> neighbors = graph.getNeighbors(currentVertex);
		
		if (neighbors.equals(childrenExecuted.get(currentVertex))) {
			
			Set<Path> currentPaths = cachedPaths.get(currentVertex);
			
			if (currentPaths != null) {
				return new HashSet<Path>(currentPaths);
			}
		}
		
		for (String neighbor : neighbors) {
			
			if (currentVertex.equals(root)) {
				//start chain
				currentChain = new ArrayList<String>();
				currentChain.add(root);
			}
			
			if(neighbor.equals(previousVertex) || neighbor.equals(root) || currentChain.contains(neighbor)) {
				
				if (!neighbor.equals(previousVertex) || neighbor.equals(root)) {
					
					Set<String> executedNeighbor = childrenExecuted.get(currentVertex);
					
					if (executedNeighbor == null) {
						executedNeighbor = new HashSet<String>();
						executedNeighbor.add(neighbor);
						childrenExecuted.put(currentVertex, executedNeighbor);
					}
					{
						executedNeighbor.add(neighbor);
					}
				}
				
				if (currentChain.contains(neighbor) && !neighbor.equals(previousVertex) && !neighbor.equals(root)) {
					
					Set<Path> neighborPaths = cachedPaths.get(neighbor);
					
					//All possible paths haven't been finished for this neighbor
					if (neighborPaths == null) {
						continue;
					}
					//Take neighbor path add currentVertex and add it to the current list of paths
					Set<Path> newNeighborPaths = new HashSet<Path>();
					
					for (Path linkedPath : neighborPaths) {
						
						linkedPath = linkedPath.addToPath(currentVertex);
						newNeighborPaths.add(linkedPath);
						
						Set<Path> currentVertexPaths = cachedPaths.get(currentVertex);
						
						if (currentVertexPaths == null) {
							currentVertexPaths = new HashSet<Path>();
							currentVertexPaths.add(new Path(linkedPath));
							cachedPaths.put(currentVertex, currentVertexPaths);
						}
						else {
							currentVertexPaths.add(new Path(linkedPath));
						}
					}
					
					paths.addAll(cachedPaths.get(currentVertex));
					return paths;
				}
				else {
					continue;
				}
			}
			
			Set<String> executedNeighbor = childrenExecuted.get(currentVertex);
			
			if (executedNeighbor == null) {
				executedNeighbor = new HashSet<String>();
				executedNeighbor.add(neighbor);
				childrenExecuted.put(currentVertex, executedNeighbor);
			}
			{
				executedNeighbor.add(neighbor);
			}
			
			childPaths = findPaths(neighbor, wantedVertex, currentVertex);
			
			if (!childPaths.isEmpty()) {
				
				Set<Path> newPaths = new HashSet<Path>();
				
				for (Path childPath : childPaths) {
					
					Path newPath = childPath.addToPath(currentVertex);
					newPaths.add(newPath);
					
					Set<Path> currentVertexPaths = cachedPaths.get(currentVertex);
					
					if (currentVertexPaths == null) {
						currentVertexPaths = new HashSet<Path>();
						currentVertexPaths.add(new Path(newPath));
						cachedPaths.put(currentVertex, currentVertexPaths);
					}
					else {
						currentVertexPaths.add(new Path(newPath));
					}
				}
				
				paths.addAll(cachedPaths.get(currentVertex));
			}
		}
		
		return paths;
	}
	
	private void printPaths(Set<Path> paths) {
		for (Path path : paths) {
			System.out.println(path.toString());
		}
	}
}
