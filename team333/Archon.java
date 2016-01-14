package team333;
import battlecode.common.*;
import java.util.Random;

public class Archon extends DefaultRobot{
	Boolean buildScoutOnce;
			
	public Archon(RobotController rc) throws GameActionException {
		super(rc);
		buildScoutOnce = false;
	}
	
	@Override
	public void executeTurn() throws GameActionException{
		move.runAwayFromEnemies();
		collectParts();
		buildUnits();
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
	

	
	public void buildUnits() throws GameActionException{
		if(rc.isCoreReady()){
			for(int i: Utils.allDirections){
				//build a scout if an archon haven't build one yet
				if(!buildScoutOnce){
					if(rc.canBuild(Direction.values()[i], RobotType.SCOUT)){
						rc.build(Direction.values()[i], RobotType.SCOUT);
						buildScoutOnce = true;
						return;
					}
				}
				int prob = rand.nextInt(20);
				//otherwise build units given the buildProbabilities
				if(prob < 5){
					if(rc.canBuild(Direction.values()[i], RobotType.GUARD)){
						rc.build(Direction.values()[i], RobotType.GUARD);
						return;
					}
				} else if(prob < 19) {
					if(rc.canBuild(Direction.values()[i], RobotType.SOLDIER)){
						rc.build(Direction.values()[i], RobotType.SOLDIER);
						return;
					}					
				} else {
					if(rc.canBuild(Direction.values()[i], RobotType.SCOUT)){
						rc.build(Direction.values()[i], RobotType.SCOUT);
						return;
					}	
				}
			}
		}	
	}
}
