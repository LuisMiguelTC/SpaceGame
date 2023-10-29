package model.gameobjects;

import java.util.ArrayList;
import mathgame.Vector2D;

public interface EnemyFeatures {

	public void setpathEnemy(EnemyPathStrategy enemyPath);
	
	public Vector2D getpathFollowing();
	
	public ArrayList<Vector2D> getPath();

	public void setFollowing(boolean following);
	
	public boolean isShooting();
	
}
