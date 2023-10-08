package model.gameobjects;

public enum PowerUpTypes {
	SHIELD("SHIELD"),
	LIFE("+1 LIFE"),
	SCORE_X2("SCORE x2"),
	FASTER_FIRE("FAST FIRE"),
	SCORE_STACK("+1000 SCORE"),
	DOUBLE_GUN("DOUBLE GUN");
	
	public String text;
	
	private PowerUpTypes(String text){
		this.text = text;
	}
}
