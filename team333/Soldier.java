package team333;
import battlecode.common.*;

public class Soldier extends DefaultRobot{
	public Soldier(RobotController rc) throws GameActionException {
		super(rc);
	}
		
	@Override
	public void executeTurn() throws GameActionException{
		if(!attackRandomZombie()){
			attackDen();
			attackWeakest();
			followDistressSignal();
			moveRandomly();
		}
		return;	
	}
	
}
