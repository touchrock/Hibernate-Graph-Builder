package builder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import util.HibernateFileUtility;
import util.HibernateMappingParser;
import hbm.DaoNode;
import graph.Graph;

/**
 * The purpose of this class is to build a graph starting at a root table. <br>
 * Each table will be represented by a DaoNode object. <br>
 * Each table will only appear once in the graph, and there will be no cycles.<br>
 * The algorithm for creating the graph is BreadthFirst.<br>
 * For each level of the graph, the tables will be created from largest to smallest.
 * @author e1054909
 */
public class HbmGraphBuilder {

	private Graph<DaoNode> graph = new Graph<DaoNode>();
	private List<String> visited = new ArrayList<String>();
	private Queue<DaoNode> queue = new LinkedList<DaoNode>();
	private HibernateMappingParser hbmParser = new HibernateMappingParser();
	private HibernateFileUtility hbmUtility = new HibernateFileUtility();
	private DaoNode root = null;
	
	private Logger log = LogManager.getLogger();
	
	public HbmGraphBuilder(String rootTable) throws Exception {
		
		File rootFile = hbmUtility.findHbmFile(rootTable);
		root = hbmParser.getDaoNode(rootFile);
		this.queue.add(root);
		this.visited.add(root.getClassName());
	}
	
	public Graph<DaoNode> buildGraph() throws Exception {
		
		log.debug("Building graph starting at " + queue.peek());
		
		while (!queue.isEmpty()) {
			buildGraph(queue.poll());
		}
		
		log.debug("Done building graph.");
		
		return graph;
	}
	
	private void buildGraph(DaoNode daoNode) throws Exception {
		
		List<String> foreignKeys = new ArrayList<String>(daoNode.getDaoForeignKeys().keySet());
		
		for (String foreignKey : foreignKeys) {
			
			if (!this.visited.contains(foreignKey)) {
				
				File hbmFile = hbmUtility.findHbmFile(foreignKey);
				DaoNode foreignKeyNode = hbmParser.getDaoNode(hbmFile);
				graph.addEdge(daoNode, foreignKeyNode);
				this.queue.add(foreignKeyNode);
				this.visited.add(foreignKey);
			}
		}
	}
	
	public DaoNode getRoot() {
		return this.root;
	}
}
