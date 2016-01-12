package ZombieBytes;

import battlecode.common.*;

import java.util.Random;

public class RobotPlayer {

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
     * @throws GameActionException 
     **/

    public static void run(RobotController rc) throws GameActionException {

    	DefaultRobot thisRobot;
    	RobotType rt = rc.getType();
		switch(rt){
			case ARCHON:
				thisRobot = new Archon(rc);
				break;
			case SOLDIER: 
				thisRobot = new Soldier(rc);
				break;
			case GUARD:
				thisRobot = new Guard(rc);
				break;
			case SCOUT:
				thisRobot = new Scout(rc);
				break;
			case VIPER:
				thisRobot = new Viper(rc);
				break;
			case TURRET:
				thisRobot = new Turret(rc);
				break;
			case TTM:
				thisRobot = new TTM(rc);
				break;
			default:
				thisRobot = new DefaultRobot(rc);
				break;
		}	
    	while(true){
    		try{
    			//check type of robot and execute appropriate 
    			thisRobot.run();
                Clock.yield();
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    			e.printStackTrace();
    		}
        }
    }
}
