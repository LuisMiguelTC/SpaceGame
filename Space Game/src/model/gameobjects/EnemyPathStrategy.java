package model.gameobjects;

import java.util.ArrayList;

import mathgame.Vector2D;

public interface EnemyPathStrategy{

	public void pathEnemy(ArrayList<Vector2D> path);
	
	public Vector2D pathFollowing(Enemy e);
	
	public Vector2D seekForce(Enemy e, Vector2D target);
}
