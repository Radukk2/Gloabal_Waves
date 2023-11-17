package Rezolvare;

public class Stats {
	String name;
	int remainedTime;
	String repeat;
	boolean shuffle;
	boolean paused;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getRemainedTime() {return remainedTime;}
	public void setRemainedTime(int remainedTime) {this.remainedTime = remainedTime;}
	public String getRepeat() {return repeat;}
	public void setRepeat(String repeat) {this.repeat = repeat;}
	public boolean isShuffle() {return shuffle;}
	public void setShuffle(boolean shuffle) {this.shuffle = shuffle;}
	public boolean isPaused() {return paused;}
	public void setPaused(boolean paused) {this.paused = paused;}
}