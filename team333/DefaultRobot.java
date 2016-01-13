package team333;
import battlecode.common.*;

import java.util.Random;

public class DefaultRobot {
	Movement move = new Movement();
	class Movement {
		public void moveTowardsLocationAndDig(MapLocation loc) throws GameActionException{
			Direction moveDirection = rc.getLocation().directionTo(loc);
			if(rc.isCoreReady()) {
				if(rc.canMove(moveDirection)){
					rc.move(moveDirection);
				} else {
					MapLocation ahead = rc.getLocation().add(moveDirection);
					if(rc.senseRubble(ahead)>=GameConstants.RUBBLE_OBSTRUCTION_THRESH){
						rc.clearRubble(moveDirection);
					}	
				}
			}
		}
		
		public void runAwayFromLocation(MapLocation loc) throws GameActionException{
			Direction moveDirection = rc.getLocation().directionTo(loc).opposite();
			if(rc.isCoreReady()) {
				if(rc.canMove(moveDirection)){
					rc.move(moveDirection);		
				}
			}
		}
	}
	
	RobotController rc;
	RobotType rt;
	static Random rand;
	
	public DefaultRobot(RobotController rc){
		this.rc = rc;
		rand = new Random(rc.getID());
		rt = rc.getType();
	}
	
	public void run() throws GameActionException{
		executeTurn();
	}
	
	public void executeTurn() throws GameActionException{
		if(!attackRandomZombie()){
		  attackRandomOpponent();
		  followDistressSignal();
		  moveRandomly();
		}
		return;
	}
	
	
	public boolean attackRandomZombie() throws GameActionException{
		RobotInfo[] zombieEnemies = rc.senseNearbyRobots(this.rt.attackRadiusSquared, Team.ZOMBIE);
		
		if(zombieEnemies.length > 0){
			sendDistressSignal();
			if(rc.isWeaponReady()){
				rc.attackLocation(zombieEnemies[0].location);
			}
			return true;
		}
		return false;
	}
	
	public void attackRandomOpponent() throws GameActionException{
		RobotInfo[] opponentEnemies = rc.senseNearbyRobots(this.rt.attackRadiusSquared, rc.getTeam().opponent());
		
		if(opponentEnemies.length > 0){
			sendDistressSignal();
			if(rc.isWeaponReady()){
				rc.attackLocation(opponentEnemies[0].location);
			}
		}
	}
	public void moveRandomly() throws GameActionException{
		int n = rand.nextInt(8);
		Direction randomDir = Direction.values()[n];
		
		MapLocation randomLocation = rc.getLocation().add(randomDir);
		if(rc.isCoreReady()){
			if(rc.canMove(randomDir)){
				rc.move(randomDir);
				return;
			}
		}
	}
	
	public void sendDistressSignal() throws GameActionException{
	 	rc.broadcastSignal(400);
	}
	
	public void followDistressSignal() throws GameActionException{
		Signal[] distressSignals = rc.emptySignalQueue();
		if(distressSignals.length > 0){
		  move.moveTowardsLocationAndDig(distressSignals[0].getLocation());
		}
	}
}