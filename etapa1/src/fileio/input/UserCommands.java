package fileio.input;

public class UserCommands {
	String username;
	String track;
	String lastCommand;
	Mp3 mp3;
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getTrack() {return track;}
	public void setTrack(String track) {this.track = track;}
	public String getLastCommand() {return lastCommand;}
	public void setLastCommand(String lastCommand) {this.lastCommand = lastCommand;}
	public Mp3 getMp3() {return mp3;}

	public void setMp3(Mp3 mp3) {
		this.mp3 = mp3;
	}
}

class Mp3 {
	String name;
	String remainedTime;
	String repeat;
	boolean shuffle;
	boolean paused;

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getRemainedTime() {return remainedTime;}
	public void setRemainedTime(String remainedTime) {this.remainedTime = remainedTime;}
	public String getRepeat() {return repeat;}
	public void setRepeat(String repeat) {this.repeat = repeat;}
	public boolean isShuffle() {return shuffle;}
	public void setShuffle(boolean shuffle) {this.shuffle = shuffle;}
	public boolean isPaused() {return paused;}
	public void setPaused(boolean paused) {this.paused = paused;}
	public Mp3() {
		this.shuffle = false;
		this.paused = false;
	}
	public Mp3(String name, String remainedTime, String repeat, boolean shuffle, boolean paused) {
		this.name = name;
		this.remainedTime =remainedTime;
		this.repeat = repeat;
		this.shuffle = shuffle;
		this.paused = paused;
	}

}