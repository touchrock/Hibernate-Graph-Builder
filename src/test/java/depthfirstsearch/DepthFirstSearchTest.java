package depthfirstsearch;

import static org.junit.Assert.*;
import graph.Graph;
import graph.Path;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import algorithm.DepthFirstSearch;


public class DepthFirstSearchTest {
	
	Graph<String> graph = new Graph<String>();

	@Test
	public void testSearchFromAtoF() {
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		assertTrue(dfs.hasVertex("A", "F"));
	}
	
	@Test
	public void testSearchFromAtoD() {
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		assertTrue(dfs.hasVertex("A", "D"));
	}
	
	@Test
	public void testSearchToAbsentNode() {
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		assertFalse(dfs.hasVertex("A", "Z"));
	}
	
	@Test
	public void testFindPathsFromAtoF() {
		
		int expected = 4;
		
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		Set<Path> paths = dfs.findPaths("A", "F");
		
		int actual = paths.size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFindPathsFromAtoB() {
		
		int expected = 2;
		
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		Set<Path> paths = dfs.findPaths("A", "B");
		
		int actual = paths.size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFindPathsToAbsentNode() {
		int expected = 0;
		
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		Set<Path> paths = dfs.findPaths("A", "Z");
		
		int actual = paths.size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFindPathsFromAtoG() {
		int expected = 2;
		
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		Set<Path> paths = dfs.findPaths("A", "G");
		
		int actual = paths.size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFindPathsFromBtoG() {
		int expected = 1;
		
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		Set<Path> paths = dfs.findPaths("B", "G");
		
		int actual = paths.size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFindPathsFromBtoF() {
		int expected = 1;
		
		DepthFirstSearch dfs = new DepthFirstSearch(graph);
		Set<Path> paths = dfs.findPaths("B", "F");
		
		int actual = paths.size();
		
		assertEquals(expected, actual);
	}
	
	@Before
	public void setup() {
		
		graph.addEdge("A", "D");
		graph.addEdge("E", "F");
		graph.addEdge("D", "F");
		graph.addEdge("A", "B");
		graph.addEdge("A", "C");
		graph.addEdge("C", "B");
		graph.addEdge("B", "D");
		graph.addEdge("C", "E");
		graph.addEdge("C", "D");
		graph.addEdge("B", "G");
	}
}
