package hbmgraphbuilder;

import static org.junit.Assert.*;
import graph.Graph;
import graph.Path;
import hbm.DaoNode;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;

import algorithm.BreadthFirstSearch;
import algorithm.GraphSearch;
import builder.HbmGraphBuilder;
import util.HibernateFileUtility;
import util.HibernateMappingParser;

public class HbmGraphBuilderTest {

	 HibernateMappingParser parser = null;
	 HibernateFileUtility hbmUtility;
	
	HbmGraphBuilder graphBuilder = null;
	Graph<DaoNode> graph = null;
	GraphSearch<DaoNode> bfs = null;
	String rootTable = "Stock";
	
	@Test
	public void testBuildGraph() {
		
		List<File> references = null;
		Graph<DaoNode> graph = null;
		HbmGraphBuilder graphBuilder = null;
		HibernateMappingParser parser = new HibernateMappingParser();
		
		try {
			graphBuilder = new HbmGraphBuilder(rootTable);
			references = parser.getAllTableReferences(hbmUtility.findHbmFile("Stock"));
		} 
		catch (Exception e1) {
			fail(ExceptionUtils.getStackTrace(e1));
		}
		
		int expected = references.size();
		
		try {
			graph = graphBuilder.buildGraph();
		}
		catch (Exception e) {
			fail(ExceptionUtils.getStackTrace(e));
		}
		
		int actual = graph.size();
		
		graph.printGraph();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDaoNodes() {
		HibernateMappingParser parser = new HibernateMappingParser();
		DaoNode actual = null;
		DaoNode expected = new DaoNode();
		expected.setClassName("Stock");
		expected.setDaoForeignKey("StockDetail", "stockDetail");
		expected.setDaoForeignKey("Category", "categories");
		expected.setDaoForeignKey("StockDailyRecord", "stockDailyRecords");
		
		try {
			actual = parser.getDaoNode(hbmUtility.findHbmFile("Stock"));
		} 
		catch (Exception e) {
			fail(ExceptionUtils.getStackTrace(e));
		}
		
		assertEquals(expected, actual);
	}
	
	@Before
	public void setup() {
		try {
			parser = new HibernateMappingParser();
			hbmUtility = new HibernateFileUtility(new File("src/test/resources/"));
		}
		catch (Exception e) {
			fail(ExceptionUtils.getStackTrace(e));
		}
	}
}
