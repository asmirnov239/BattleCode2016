package lectureplayer1;
import battlecode.common.*;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Dictionary;

public class PathFinder {
	
	private ArrayList<Node> closed = new ArrayList<Node>();
	private PriorityQueue<Node> open = new PriorityQueue<Node>();
	private int maxSearchDistance;
	private Dictionary<MapLocation, Node> nodes;
	private Heuristic heuristic; 
	private int RADIUS = 99;
	
	public PathFinder(RobotController rc, int maxSearchDistance, Heuristic heuristic) {
		this.heuristic = heuristic;
		this.maxSearchDistance = maxSearchDistance;
		
		MapLocation[] locations = rc.getLocation().getAllMapLocationsWithinRadiusSq(rc.getLocation(), RADIUS);
		for (MapLocation loc:locations) {
				nodes.put(loc, new Node(loc));		
		}
	}
	
	public ArrayList<MapLocation> findPath(RobotController rc, MapLocation goal) throws GameActionException {
		
		MapLocation start = rc.getLocation();
		
		
		// Check and Initialize the Open/Closed lists
		if(!rc.canSense(goal)) {
			return null;
		}
		
		if(!rc.onTheMap(goal)) {
			return null;
		}
				
		Node startNode = nodes.get(start);
		if(startNode == null) {
			return null;
		}
		startNode.cost = 0;
		startNode.depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes.get(start));
		nodes.get(goal).setParent(null);

		// Search for path while there are unchecked nodes and not exceeded maxSearchDistance
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
			Node current = open.remove(); // get next in path off priority queue
			if (current == nodes.get(goal)) {
				break;
			}
			
			open.remove(current);
			closed.add(current);
			
			int[] allDirections = new int[]{0,1,2,3,4,5,6,7};
			// evaluate all not obstructed directions
			for(int dir:allDirections ){
				Node neighbor = new Node(current.loc.add(Direction.values()[dir]));
				
				if (rc.senseRubble(neighbor.loc) > GameConstants.RUBBLE_OBSTRUCTION_THRESH) {
					continue; //don't evaluate blocked directions
				}
				
				float nextStepCost = neighbor.cost + getHeuristicCost(rc, current, neighbor);
				
				// If nextStep is cheaper, set parent and reevaluate
				if (nextStepCost < neighbor.cost) {
					if (open.contains(neighbor)) {
						open.remove(neighbor);
					}
					if (closed.contains(neighbor)) {
						closed.remove(neighbor);
					}
				}
				
				if(!open.contains(neighbor) && !closed.contains(neighbor)) {
					neighbor.cost = nextStepCost;
					maxDepth = Math.max(maxDepth, neighbor.setParent(current));
					open.add(neighbor);
				}
			}
			
		}
		
		if (nodes.get(goal) == null) {
			return null;
		}
		
		ArrayList<MapLocation> path = new ArrayList<MapLocation>();
		Node target = nodes.get(goal);
		while (target != nodes.get(start)) {
			path.add(0,target.loc);
			target = target.parent;
		}
		
		return path;
	}
		
	private float getHeuristicCost(RobotController rc, Node current, Node neighbor) {
		return heuristic.getCost(rc, current.loc, neighbor.loc);
	}

	private class Node {

		private MapLocation loc;
		private Node parent;
		private float cost;
		private int depth;
		
		public Node(MapLocation loc) {
			this.loc = loc;
		}
		
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			return depth;
		}
		
	}
	
}