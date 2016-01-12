package ZombieBytes;
import battlecode.common.*;


public class Archon extends DefaultRobot{
	
	public Archon(RobotController rc) throws GameActionException {
		super(rc);
	}
	
	@Override
	public void executeTurn() throws GameActionException{
		if(rc.isCoreReady()){
			for(int i: Utils.allDirections){
			 	if(rc.canBuild(Direction.values()[i], RobotType.SOLDIER)){
			 		rc.build(Direction.values()[i], RobotType.SOLDIER);
			 		return;
			 	}
			}
		}
		moveRandomly();
	}
	
	public void collectParts() throws GameActionException{
		MapLocation[] partsLocations = rc.sensePartLocations(rc.getType().sensorRadiusSquared);
		for(MapLocation loc:partsLocations){
			//TODO
		}
	}
}
