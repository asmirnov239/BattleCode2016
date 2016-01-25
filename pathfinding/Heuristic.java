package lectureplayer1;
import battlecode.common.*;

public class Heuristic {
	public Heuristic() {}
	public float getCost(RobotController rc, MapLocation current, MapLocation goal) {
		return current.distanceSquaredTo(goal);
	}
}
