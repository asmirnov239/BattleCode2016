package ZombieBytes;
import battlecode.common.*;

import java.util.Random;

public class DefaultRobot {
	
	class Movement {
		public void moveTowardsLocation(MapLocation loc){
			//TODO
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
		attackRandomZombie();
		attackRandomOpponent();
		moveRandomly();
		return;
	}
	
	
	public void attackRandomZombie() throws GameActionException{
		RobotInfo[] zombieEnemies = rc.senseNearbyRobots(this.rt.attackRadiusSquared, Team.ZOMBIE);
		
		if(zombieEnemies.length > 0){
			if(rc.isWeaponReady()){
				rc.attackLocation(zombieEnemies[0].location);
			}
		}
	}
	
	public void attackRandomOpponent() throws GameActionException{
		RobotInfo[] opponentEnemies = rc.senseNearbyRobots(this.rt.attackRadiusSquared, rc.getTeam().opponent());
		
		if(opponentEnemies.length > 0){
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
}
