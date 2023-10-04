package model.gameobjects;

import java.util.ArrayList;

import mathgame.Vector2D;

public class BasicPathEnemy implements EnemyPathStrategy{
	
	private int index = 0;
	
	@Override
	public void pathEnemy(ArrayList<Vector2D> path) {
		
		double posX, posY;
		
		for(int i = 0; i < 4; i++) {
			posX = Math.random()*Constants.WIDTH/2;
			posY = Math.random()*Constants.HEIGHT/2;	
			path.add(new Vector2D(posX, posY));
		}
	}

	@Override
	public Vector2D pathFollowing(Enemy e) {
		

		Vector2D currentNode = e.getPath().get(index);
		
		double distanceToNode = currentNode.subtract(e.getCenter()).getMagnitude();
		
		if(distanceToNode < Constants.NODE_RADIUS) {
			index ++;
			if(index >= e.getPath().size()) {
				e.setFollowing(false);
			}
		}		
		return seekForce(e,currentNode);
		
	}

	@Override
	public Vector2D seekForce(Enemy e, Vector2D target) {
		Vector2D desiredVelocity = target.subtract(e.getCenter());
		desiredVelocity = desiredVelocity.normalize().scale(e.maxVel);	
		return desiredVelocity.subtract(e.velocity);
	}
	
}
