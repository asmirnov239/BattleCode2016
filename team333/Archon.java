package team333;
import battlecode.common.*;
import java.util.Random;

public class Archon extends DefaultRobot{
	Movement move = new Movement();
			
	public Archon(RobotController rc) throws GameActionException {
		super(rc);
	}
	
	@Override
	public void executeTurn() throws GameActionException{
		runAwayFromEnemies();
		collectParts();
		if(rc.isCoreReady()){
			for(int i: Utils.allDirections){
				if(rand.nextInt(3) == 0){
					if(rc.canBuild(Direction.values()[i], RobotType.GUARD)){
						rc.build(Direction.values()[i], RobotType.GUARD);
						return;
					}
				} else {
					if(rc.canBuild(Direction.values()[i], RobotType.SOLDIER)){
						rc.build(Direction.values()[i], RobotType.SOLDIER);
						return;
					}					
				}
			}
		}
		moveRandomly();
	}
	
	public void collectParts() throws GameActionException{
		MapLocation[] partsLocations = rc.sensePartLocations(rc.getType().sensorRadiusSquared);
		if(partsLocations.length > 0){
			move.moveTowardsLocationAndDig(partsLocations[0]);
		}
		for(MapLocation loc:partsLocations){
			//TODO
			return;
		}
	}
	
	public void runAwayFromEnemies() throws GameActionException{
		RobotInfo[] zombieEnemies = rc.senseNearbyRobots(rc.getType().sensorRadiusSquared, Team.ZOMBIE);
		RobotInfo[] opponentEnemies = rc.senseNearbyRobots(rc.getType().sensorRadiusSquared, rc.getTeam().opponent());
		if(zombieEnemies.length > 0){
			move.runAwayFromLocation(zombieEnemies[0].location);
		}
		if(opponentEnemies.length > 0){
			move.runAwayFromLocation(opponentEnemies[0].location);
		}		
	}
}
