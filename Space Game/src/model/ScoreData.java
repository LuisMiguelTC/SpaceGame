package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreData {
	
	private String date;
	private int score;
	private String name;
	private int enemydestroyed;
	
	public ScoreData(int score, String name, int enemydestroyed) {
		
		this.score = score;
		this.name = name;
		this.enemydestroyed = enemydestroyed;
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		date = format.format(today);
	}
	
	public ScoreData() {	
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
	
}
