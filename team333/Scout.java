package team333;
import battlecode.common.*;

public class Scout extends DefaultRobot{
	public Scout(RobotController rc) throws GameActionException {
		super(rc);
	}

	@Override
	public void executeTurn() throws GameActionException{
		move.runAwayFromEnemies();
		moveRandomly();
	}
	
}
