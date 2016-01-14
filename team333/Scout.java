package team333;
import battlecode.common.*;

public class Scout extends DefaultRobot{
	public Scout(RobotController rc) throws GameActionException {
		super(rc);
	}

	Direction currentDir = Direction.NORTH;

	@Override
	public void executeTurn() throws GameActionException{
		move.runAwayFromEnemies();
		moveScout();
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
