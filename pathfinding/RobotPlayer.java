package lectureplayer1;

import java.util.ArrayList;

import battlecode.common.*;

public class RobotPlayer {
	
	static Direction movingDirection = Direction.EAST;
	static RobotController rc;
	static Direction nextDir = Direction.NONE;
	static ArrayList<MapLocation> path = new ArrayList<MapLocation>();
	
	public static void run(RobotController rcIn) {
		rc=rcIn;
		if (rc.getTeam() == Team.B) {
			movingDirection = Direction.WEST;
		}
		
		while(true) {
			try {
				repeat();
				Clock.yield(); // ends current turn (doesn't use compute power)
			} catch (GameActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void repeat() throws GameActionException {
		RobotInfo[] zombieEnemies = rc.senseNearbyRobots(rc.getType().attackRadiusSquared, Team.ZOMBIE);

		if(zombieEnemies.length > 0){
			if(rc.isWeaponReady()) {
				rc.attackLocation(zombieEnemies[0].location);
			}
		} else {
			// TEAM A
			if (rc.getTeam() == Team.A) {
				if (rc.isCoreReady()){
					if (rc.canMove(movingDirection)){
						rc.move(movingDirection);
					} else {
						MapLocation ahead = rc.getLocation().add(movingDirection);
						if(rc.senseRubble(ahead)>=GameConstants.RUBBLE_OBSTRUCTION_THRESH){
							rc.clearRubble(movingDirection);
						}	
					}
				}
			// TEAM B
			} else {
				if (nextDir == Direction.NONE) {
					Heuristic heur = new Heuristic();
					PathFinder pf = new PathFinder(rc, GameConstants.MAP_MAX_WIDTH, heur);
					MapLocation goal = rc.getLocation().add(movingDirection, 20);

					path = pf.findPath(rc, goal);
				}
				if (path != null) {
				MapLocation nextLoc = path.remove(0);
				nextDir = nextLoc.directionTo(nextLoc);	
				if (rc.canMove(nextDir)) {
					rc.move(nextDir);
				}
				} else {
					if (rc.isCoreReady()){
						if (rc.canMove(movingDirection)){
							rc.move(movingDirection);
						} else {
							MapLocation ahead = rc.getLocation().add(movingDirection);
							if(rc.senseRubble(ahead)>=GameConstants.RUBBLE_OBSTRUCTION_THRESH){
								rc.clearRubble(movingDirection);
							}	
						}
					}
				}
			}
		}
	}
	
	
}


