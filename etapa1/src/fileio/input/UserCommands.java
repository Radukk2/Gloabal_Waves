package fileio.input;

import Rezolvare.Stats;

import java.util.ArrayList;

public class UserCommands {
	String username;
	String track;
	String lastCommand;
	int lastTimestamp;
	Stats stats;
	String SelectedSong;
	ArrayList<String> results;

	public int getLastTimestamp() {return lastTimestamp;}
	public void setLastTimestamp(int lastTimestamp) {this.lastTimestamp = lastTimestamp;}
	public ArrayList<String> getResults() {return results;}
	public void setResults(ArrayList<String> results) {this.results = results;}
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getTrack() {return track;}
	public void setTrack(String track) {this.track = track;}
	public String getLastCommand() {return lastCommand;}
	public void setLastCommand(String lastCommand) {this.lastCommand = lastCommand;}
	public String getSelectedSong() {return SelectedSong;}
	public void setSelectedSong(String selectedSong) {SelectedSong = selectedSong;}
	public Stats getStats() {return this.stats;}
	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public void LoadData(UserCommands userCommands, LibraryInput libraryInput) {
		if (userCommands.getTrack().equals("song")) {
			ArrayList<SongInput> Songs = libraryInput.getSongs();
			SongInput song = new SongInput();
			for (SongInput songInput : Songs) {
				if (songInput.getName().equals(userCommands.getSelectedSong())) {
					song = songInput;
					break;
				}
			} if (song.getName() != null) {
				Stats stats = new Stats();
				stats.setName(song.getName());
				stats.setRemainedTime(song.getDuration());
				stats.setRepeat("No Repeat");
				stats.setPaused(false);
				stats.setShuffle(false);
				userCommands.setStats(stats);
				userCommands.setLastCommand("load");
			}
		}
	}
}
