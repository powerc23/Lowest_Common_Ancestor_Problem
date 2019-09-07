import static org.junit.Assert.*;

import org.junit.Test;


/*
 * Name: Conor Power
*/
public class DAGTest {

	//Tests empty DAG
	@Test
	public void testEmpty() 
	{
		DAG graph = new DAG(0);
		assertTrue(graph.isEmpty());
	}

	//Test DAG with negative amount of vertices
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeVertices()
	{
		DAG graph = new DAG(-1);
	}


	//Tests edge getter fucntion
	@Test
	public void testEdgeGetter()
	{
		DAG graph = new DAG(10);
		graph.addEdge(1,2);
		graph.addEdge(2,3);
		graph.addEdge(3,4);
		graph.addEdge(4,5);

		assertEquals("This graph has 4 edges", 4, graph.E());
	}

	//Test the DAG constructor
	@Test
	public void testGraphConstructor()
	{
		DAG graph = new DAG(0);
		assertEquals("This graph has no vertices", 0, graph.V());
		assertEquals("This graph has no edges", 0, graph.E());
		assertNull("The adjacency list is empty",graph.adj(0));


		graph = new DAG(1);
		assertEquals("This graph has 1 vertex", 1, graph.V());
		assertEquals("This graph has no edges", 0, graph.E());
		assertNotNull("The adjacency list is not empty", graph.adj(0));

		graph = new DAG(1000);
		assertEquals("This graph has 1000 vertices", 1000, graph.V());
		assertEquals("This graph has no edges", 0, graph.E());
		assertNotNull("The adjacency list is not empty", graph.adj(999));

	}

	//Test isEmpty function
	@Test
	public void testIsEmpty()
	{
		DAG graph = new DAG(0);
		assertTrue("This graph is empty", graph.isEmpty());

		graph = new DAG(1);
		assertFalse("This graph is not empty",graph.isEmpty());
	}

	//Tests validateVertex function
	@Test
	public void testValidateVertex()
	{
		DAG graph = new DAG(10);
		assertEquals(-1, graph.validateVertex(-1));
		assertEquals(-1, graph.validateVertex(11));
		assertEquals(-1, graph.validateVertex(10));
		assertEquals(1, graph.validateVertex(9));
		assertEquals(1, graph.validateVertex(0));
	}

	//Test indegree function
	@Test
	public void testIndegree()
	{
		DAG graph = new DAG(10);
		graph.addEdge(1,2);
		graph.addEdge(2,3);
		graph.addEdge(3,4);
		graph.addEdge(4,5);

		assertEquals("This vertex has an indegree of 0", 0, graph.indegree(1));
		assertEquals("This vertex has an indegree of 1", 1, graph.indegree(2));
		assertEquals("This vertex has an indegree of 0", 0, graph.indegree(6));
		assertEquals("This vertex does not exist", -1, graph.indegree(-2));
		assertEquals("This vertex has an indegree of 0", 0, graph.indegree(0));

	}

	//Tests outdegree function
	@Test
	public void testOutdegree()
	{
		DAG graph = new DAG(10);
		graph.addEdge(1,2);
		graph.addEdge(2,3);
		graph.addEdge(3,4);
		graph.addEdge(4,5);

		assertEquals("This vertex has an outdegree of 1", 1, graph.outdegree(1));
		assertEquals("This vertex has an outdegree of 1", 1, graph.outdegree(2));
		assertEquals("This vertex has an outdegree of 0", 0, graph.outdegree(5));
		assertEquals("This vertex has an outdegree of 0", 0, graph.outdegree(6));
		assertEquals("This vertex does not exist", -1, graph.outdegree(-2));
		assertEquals("This vertex has an outdegree of 0", 0, graph.outdegree(0));
	}

	//Tests addEdge function
	@Test
	public void testAddEdge()
	{
		DAG graph = new DAG(10);
		graph.addEdge(1,2);
		graph.addEdge(2,3);
		graph.addEdge(3,4);
		graph.addEdge(4,5);
		graph.addEdge(-1,-2);

		assertEquals("This graph has 4 edges", 4, graph.E());
		
	}

	//Tests hasCycle and findCycle functions
	@Test
	public void testHasCycle()
	{ 
		DAG graph = new DAG(10);
		graph.addEdge(0,1);
		graph.addEdge(1,2);
		graph.addEdge(2,3);
		graph.addEdge(3,4);
		graph.addEdge(4,5);

		graph.findCycle(0);
		assertFalse("Graph does not have cycle",graph.hasCycle());

		DAG graph2 = new DAG(10);
		graph2.addEdge(0,1);
		graph2.addEdge(1,2);
		graph2.addEdge(2,0);
		graph2.addEdge(2,3);
		graph2.addEdge(3,4);
		graph2.addEdge(4,5);

		graph2.findCycle(0);
		assertTrue("Graph has cycle",graph2.hasCycle());

	}

	//Tests for findLCA function
	@Test
	public void testFindLCA()
	{
		DAG graph = new DAG(8);
		
		//Test for empty DAG
		assertEquals("LCA doesn't exist", -1, graph.findLCA(1,2));

		graph.addEdge(0,1);
		graph.addEdge(0,2);
		graph.addEdge(1,3); 
		graph.addEdge(2,3);
		graph.addEdge(3,4);
		graph.addEdge(4,5);
		graph.addEdge(5,6);
		graph.addEdge(5,7);

		//Various tests for full DAG
		assertEquals("The LCA for 0 and 1 is 0", 0, graph.findLCA(0,1));
		assertEquals("The LCA for 1 and 2 is 0", 0, graph.findLCA(1,2));
		assertEquals("The LCA for 3 and 4 is 3", 3, graph.findLCA(3,4));
		assertEquals("The LCA for 6 and 7 is 5", 5, graph.findLCA(6,7));
		assertEquals("The LCA for a single vertex is the vertex", 0, graph.findLCA(0,0));
		assertEquals("LCA doesn't exist", -1, graph.findLCA(9,10));
		
		//Test for negative vertices
		assertEquals("LCA doesn't exist", -1, graph.findLCA(-1,-2));
		assertEquals("LCA doesn't exist", -1, graph.findLCA(-1,2));
		assertEquals("LCA doesn't exist", -1, graph.findLCA(1,-2));

		//Test for two vertices with no LCA
		DAG graph2 = new DAG(4);
		graph2.addEdge(0,1);
		graph2.addEdge(2,3);
		assertEquals("LCA doesn't exist", -1, graph2.findLCA(1,2));
		
		DAG graph3= new DAG(10);
		graph3.addEdge(0,1);
		graph3.addEdge(1,2);
		graph3.addEdge(2,0);
		graph3.addEdge(2,0);
		graph3.addEdge(2,3);
		graph3.addEdge(3,4);
		graph3.addEdge(4,5);

		//Test for cyclical graph
		graph3.findCycle(0);
		assertEquals("Graph has cycle", -1, graph3.findLCA(1,2));

	}

}
