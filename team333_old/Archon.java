package team333;
import battlecode.common.*;
import java.util.*;

public class Archon extends DefaultRobot{
	Boolean buildScoutOnce;
	
	
	//buildProbabilities stores mapping of unit types to the probabilities that archon builds them(make sure probabilities add up to one)
	@SuppressWarnings("serial")
	Map<RobotType, Double> buildProbabilities = new HashMap<RobotType, Double>() {{put(RobotType.GUARD, 0.1); put(RobotType.SOLDIER, 0.9); put(RobotType.SCOUT, 0.0); put(RobotType.VIPER, 0.0); put(RobotType.TURRET, 0.0);}};
	
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
				//otherwise choose a unit to build given the buildProbabilities dictionary defined as a field variable
				Double probability = rand.nextDouble();
				Double currProbValue = 0.0;
				RobotType type = RobotType.SOLDIER;
				Double buildProb;
				for (Map.Entry<RobotType, Double> entry : buildProbabilities.entrySet()) {
					type = entry.getKey();
					buildProb = entry.getValue();
					currProbValue = currProbValue + buildProb;
					if(probability < currProbValue){	
						//here type will be equal to the unit type we want to build unless something has gone horribly wrong
						break;
					}
				}
				
				if(rc.canBuild(Direction.values()[i], type)){
					rc.build(Direction.values()[i], type);
					return;
				}
			}
		}	
	}
}
