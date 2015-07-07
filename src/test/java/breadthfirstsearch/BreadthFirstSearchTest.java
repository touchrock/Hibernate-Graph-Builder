package breadthfirstsearch;

import static org.junit.Assert.*;
import graph.Graph;
import graph.Path;

import org.junit.Before;
import org.junit.Test;

import algorithm.BreadthFirstSearch;
import algorithm.GraphSearch;

public class BreadthFirstSearchTest {
	
	Graph<String> graph = new Graph<String>();

	@Test
	public void findShortestPathAtoF() {
		
		String startingVertex = "A";
		String wantedVertex = "F";
		
		GraphSearch<String> bfs = new BreadthFirstSearch<String>(graph);
		
		assertEquals(new Path<String>("A","D","E","F"), bfs.findShortestPath(startingVertex, wantedVertex));
	}
	
	@Test
	public void hasVertex() {
		
		String wantedVertex = "F";
		String startingVertex = "A";
		
		GraphSearch<String> bfs = new BreadthFirstSearch<String>(graph);
		
		assertTrue(bfs.hasVertex(startingVertex, wantedVertex));
	}
	
	
	@Before
	public void setup() {
		
		graph.addEdge("A", "B");
		graph.addEdge("A", "C");
		graph.addEdge("A", "D");
		graph.addEdge("D", "E");
		graph.addEdge("E", "F");
	}
}
