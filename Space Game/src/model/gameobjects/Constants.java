package model.gameobjects;

import javax.swing.filechooser.FileSystemView;

public class Constants {
	
	// frame dimensions
	
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	
	// player properties
	
	public static final int FIRERATE = 800;
	public static final double DELTAANGLE = 0.04;
	public static final double ACC = 0.3;
	public static final double PLAYER_MAX_VEL = 3.0;
	public static final long FLICKER_TIME = 200;
	public static final long SPAWNING_TIME = 3000;
	public static final long GAME_OVER_TIME = 3000;
	
	// Laser properties
	
	public static final double LASER_VEL = 10.0;
	public static final double LASER_VEL_ENEMY = 8.0;
	
	// Meteor properties
	
	public static final double METEOR_INIT_VEL = 1.0;
	public static final int METEOR_SCORE = 20;
	public static final double METEOR_MAX_VEL = 3.0;
	public static final int SHIELD_DISTANCE = 150;
	
	// Enemy properties
	
	public static final int NODE_RADIUS = 160;
	public static final double ENEMY_MASS = 60;
	public static final int ENEMY_MAXVEL = 1;
	public static long ENEMY_FIRERATE = 1500;
	public static double ENEMY_ANGLE_RATE = Math.PI / 2;
	public static final int ENEMY_SCORE = 40;
	public static final long ENEMY_SPAWN_RATE = 15000;
	
	// Options
	public static final String PLAY = "PLAY";
	public static final String EXIT = "EXIT";
	public static final String RETURN = "RETURN";
	public static final String HIGH_SCORES = "HIGHEST SCORES";
	public static final String SCORE = "SCORE";
	public static final String NAME = "NAME";
	public static final String COUNT = "HITS";
	public static final String RESUME = "RESUME";
	public static final String RESTART = "RESTART";
	public static final String SCORE_PATH = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() +
			"\\SpaceGame\\data.json";
	
	// PowerUp properties
	public static final long POWER_UP_DURATION = 10000;
	public static final long POWER_UP_SPAWN_TIME = 8000;
	public static final long SHIELD_TIME = 12000;
	public static final long DOUBLE_SCORE_TIME = 10000;
	public static final long FAST_FIRE_TIME = 14000;
	public static final long DOUBLE_GUN_TIME = 12000;
	public static final int SCORE_STACK = 1000;
	
	//Button properties
	public static final int BUTTON_WIDTH = 190;
	public static final int BUTTON_HEIGHT = 49;	
}
