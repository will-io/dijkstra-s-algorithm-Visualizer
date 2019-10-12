import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.awt.Color;

import java.util.*;

/**
 * The Class ThreeTenDijkstra represents an Algorithm to simulate Dijkstra shortest path algorithm
 */
class ThreeTenDijkstra implements ThreeTenAlg {

	// ********************************************************************************
	// DO NOT EDIT ANYTHING IN THIS SECTION except to add JavaDocs
	// ********************************************************************************

	public static final Color COLOR_DONE_NODE = Color.GREEN;
	public static final Color COLOR_DONE_EDGE_1 = Color.GREEN.darker();
	public static final Color COLOR_DONE_EDGE_2 = Color.LIGHT_GRAY;
	public static final Color COLOR_ACTIVE_NODE_1 = Color.RED;
	public static final Color COLOR_ACTIVE_NODE_2 = Color.YELLOW;
	public static final Color COLOR_ACTIVE_EDGE = Color.ORANGE;
	public static final Color COLOR_NONE_NODE = Color.WHITE;
	public static final Color COLOR_NONE_EDGE = Color.BLACK;

	public static final String INFINITY_SIGN = "\u221e";

	private boolean started = false;

	// You'll want to use this...
	private Graph<ThreeTenNode, ThreeTenEdge> g;

	
	public EdgeType graphEdgeType() {
		return EdgeType.DIRECTED;
	}

	
	// And you need to understand this...
	public boolean step() {
		if (!started) {
			start();
			return true;
		}

		cleanUpLastStep();
		if (!setupNextMin()) {
			finish();
			return false;
		}
		doUpdates();

		return true;
	}

	
	// And this...
	public void reset(Graph<ThreeTenNode, ThreeTenEdge> g) {
		this.g = g;
		started = false;
	}

	/**
	 * Sets the node text.
	 *
	 * @param n the node
	 * @param distance the distance
	 */
	// And this...
	public void setNodeText(ThreeTenNode n, int distance) {
		String text = (distance == Integer.MAX_VALUE ? INFINITY_SIGN : "" + distance);
		n.setText(text);
	}

	/**
	 * Sets the node tex when a distance is updated
	 *
	 * @param n the node
	 * @param oldCost the old cost
	 * @param newCost the new cost
	 */
	// And this...
	public void setNodeText(ThreeTenNode n, int oldCost, int newCost) {
		String text = (oldCost == Integer.MAX_VALUE ? INFINITY_SIGN : "" + oldCost);
		n.setText("" + text + "->" + newCost);
	}

	// ********************************************************************************
	// END DO-NOT-EDIT SECTION
	// ********************************************************************************

	public static final int INFINITY = Integer.MAX_VALUE;
	private HashSet<ThreeTenNode> vertexSet = new HashSet<>();
	private HashMap<ThreeTenNode, ThreeTenNode> prev = new HashMap<>();
	private HashMap<ThreeTenNode, Integer> distances = new HashMap<>();
	private HashSet<ThreeTenEdge> edgeSet = new HashSet<>();
	private ThreeTenNode nextMin;

	/**
	 * Instantiates a new three ten dijkstra.
	 */
	public ThreeTenDijkstra() {
		// do any setup you want here
	}

	/**
	 * Gets the minimum id node.
	 *
	 * @return the minimum id node
	 */
	public ThreeTenNode getMinimumIdNode() {
		Collection<ThreeTenNode> nodes = g.getVertices();
		ThreeTenNode min = null;
		for (ThreeTenNode threeTenNode : nodes) {
			if (min == null || threeTenNode.compareTo(min) < 0) {
				min = threeTenNode;
			}
		}

		return min;
	}

	
	public void start() {
		// sets all nodes to infinity sign except one with
		// the minimum id, which is set to 0
		started = true;

		distances.clear();
		prev.clear();
		vertexSet.clear();
		
		

		for (ThreeTenNode v : g.getVertices()) {
			v.setText(INFINITY_SIGN);
			distances.put(v, INFINITY);
			prev.put(v, null);
			vertexSet.add(v);
		}

		ThreeTenNode minIdNode = getMinimumIdNode();
		minIdNode.setText("0");

		// Add/change anything you want.
		nextMin = null;
		distances.put(minIdNode, 0);
		edgeSet = new HashSet<>(g.getEdges());
		

	}

	/**
	 * Gets the unused edges.
	 *
	 * @return the unused edges
	 */
	public Collection<ThreeTenEdge> getUnusedEdges() {
		// returns all the nodes in the graph that aren't being "used"
		// as part of Dijkstra's (these are the ones that are "greyed out"
		// at the end of the algorithm).
		return edgeSet;
	}

	
	public void finish() {
		// sets all unused edges to grey color
		for (ThreeTenEdge e : getUnusedEdges()) {
			e.setColor(COLOR_DONE_EDGE_2);
		}

		// Add/change anything you want.
	}

	/**
	 * Clean up last step.
	 */
	public void cleanUpLastStep() {
		// mark the node picked on the _previous_ step to "done"
		// somehow... you choose how this works...

		// set that node's color to COLOR_DONE_NODE
		if(nextMin != null) {
			nextMin.setColor(COLOR_DONE_NODE);
		}
		

		
		// change all edges not being used from the orange highlight
		// (COLOR_ACTIVE_EDGE) back to COLOR_NONE_EDGE
		for (ThreeTenEdge threeTenEdge : edgeSet) {
			threeTenEdge.setColor(COLOR_NONE_EDGE);
		}

		// reset all non-finished nodes back to COLOR_NONE_NODE and
		// text representing their distance (not their update)
		for (ThreeTenNode threeTenNode : vertexSet) {
			threeTenNode.setColor(COLOR_DONE_NODE);
			setNodeText(threeTenNode, distances.get(threeTenNode));
		}
	}

	/**
	 * Setup next min.
	 *
	 * @return true, if successful
	 */
	public boolean setupNextMin() {
		// if there are no more nodes remaining in the algorithm
		// return false
		if(vertexSet.isEmpty())
			return false;

		// pick the next minimum node to look at (min-distance,
		// and min-ID if tied on distance)
		
		nextMin = null;
		for (ThreeTenNode node : vertexSet) {
			if(nextMin == null || distances.get(nextMin) > distances.get(node)) 
				nextMin = node;
			
			else if(nextMin != null && distances.get(nextMin) == distances.get(node) && node.compareTo(nextMin) < 0)
				nextMin = node;
 		}

		// set color of the new node to COLOR_ACTIVE_NODE_1
		nextMin.setColor(COLOR_ACTIVE_NODE_1);
		
		vertexSet.remove(nextMin);
		
		
		// set the edge between the node and it's parent to COLOR_DONE_EDGE_1
		// careful... if the node can't be reached from the starting node
		// there might not be a parent...
		ThreeTenNode parent = prev.get(nextMin);
		
		if(parent != null) {
			ThreeTenEdge edge = g.findEdge(parent, nextMin);
			edge.setColor(COLOR_DONE_EDGE_1);
			edgeSet.remove(edge);
		}

		// when you're done, return true

		return true;
	}

	/**
	 * Do updates.
	 */
	public void doUpdates() {
		// from the newly picked node

		// (1) calculate a new cost for neighbours that can be updated
		//
		// (2) if the new cost is better, highlight the edges to that
		// node with COLOR_ACTIVE_EDGE and the neighbour with COLOR_ACTIVE_NODE_2
		//
		// (3) if the new cost is better, change the text of the neighbour
		// using setNodeText(node, currentDistance, newDistance)
		//
		// (4) perform any other tracking of these updates you need

		// Note: there is no infinity + 1... so neighbours of nodes set to
		// infinity won't update
		Collection<ThreeTenNode> nodes = g.getSuccessors(nextMin);
		for (ThreeTenNode neighbor : nodes) {
			if(vertexSet.contains(neighbor)) {
				ThreeTenEdge edge =  g.findEdge(nextMin, neighbor);
				int dist = distances.get(nextMin);
				
				if(dist == INFINITY)
					continue;
				
				int alt = dist + edge.getWeight();
				if(alt < distances.get(neighbor)) {
					setNodeText(neighbor, distances.get(neighbor), alt);
					distances.put(neighbor, alt);
					prev.put(neighbor, nextMin);
					neighbor.setColor(COLOR_ACTIVE_NODE_2);
					edge.setColor(COLOR_ACTIVE_EDGE);
				}
				
 			}
		}
		
		
	}
}
