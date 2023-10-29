package model.utils;

public class ScoreData {
	
	private int score;
	private String name;
	private int enemydestroyed;
	
	public ScoreData(int score, String name, int enemydestroyed) {
		
		this.score = score;
		this.name = name;
		this.enemydestroyed = enemydestroyed;
	}
	
	public ScoreData() {	
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnemydestroyed() {
		return enemydestroyed;
	}

	public void setEnemydestroyed(int enemydestroyed) {
		this.enemydestroyed = enemydestroyed;
	}

	@Override
	public String toString() {
		return "ScoreData [score=" + score + ", name=" + name + ", enemydestroyed=" + enemydestroyed + "]";
	}	
}
