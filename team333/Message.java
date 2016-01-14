package team333;
import battlecode.common.*;


public class Message{


	RobotController rc;
	RobotType rt;		
	
	public Message(RobotController rc) {
		this.rc = rc;
		this.rt = rc.getType();
	}
	
	public void sendDistressSignal() throws GameActionException{
	 	rc.broadcastSignal(400);
	}
	
	
}
