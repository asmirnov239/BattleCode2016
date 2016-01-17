package team333;
import battlecode.common.*;

public class Scout extends DefaultRobot{
	public Scout(RobotController rc) throws GameActionException {
		super(rc);
	}

	Direction currentDir = Direction.NORTH;
	MapLocation[] markedDens = new MapLocation[GameConstants.MAP_MAX_HEIGHT * GameConstants.MAP_MAX_WIDTH];
	static int maxBroadcast = 2500;	
	
	@Override
	public void executeTurn() throws GameActionException{
		move.runAwayFromEnemies();
		moveScout();
		detectZombieDen();
	}
	
	public void detectZombieDen() throws GameActionException {
		RobotInfo[] zombieEnemies = rc.senseNearbyRobots(rc.getType().sensorRadiusSquared, Team.ZOMBIE);
	
		int x, y;
		boolean marked = false;
		for(RobotInfo zombie:zombieEnemies) {
			if(zombie.type == RobotType.ZOMBIEDEN) {
				
				//check if already marked
				marked = false;
				for(MapLocation loc:markedDens) {
					if(loc == zombie.location) {
						marked = true;
					}
				}
				
				if(!marked) {
					x = zombie.location.x;
					y = zombie.location.y;
					rc.broadcastMessageSignal(x, y, maxBroadcast);
				}
			} 
		}
	}
	
	public void moveScout() throws GameActionException {

		if(rc.isCoreReady()){
			if(!rc.canMove(currentDir)){	
				for(int i:Utils.possibleDirections) {
					Direction candidateDir = Direction.values()[(currentDir.ordinal()+i+8)%8];
					if(rc.canMove(candidateDir)){	
						currentDir = candidateDir;
					}				
				}
			}
			rc.move(currentDir);
		}
	}
	
}
