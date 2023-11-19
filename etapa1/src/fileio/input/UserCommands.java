package fileio.input;
import Rezolvare.Playlist;
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
	ArrayList<Playlist> playlists;
	ArrayList<String> likedSongs = new ArrayList<String>();
	String playingPlaylist;

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
	public void setStats(Stats stats) {this.stats = stats;}
	public ArrayList<Playlist> getPlaylist(){return  this.playlists;}
	public void setPlaylist(ArrayList<Playlist> playlists) {this.playlists = playlists;}
	public ArrayList<String> getLikedSongs() {return likedSongs;}
	public void setLikedSongs(ArrayList<String> likedSongs) {this.likedSongs = likedSongs;}
	public String getPlayingPlaylist() {return playingPlaylist;}
	public void setPlayingPlaylist(String playingPlaylist) {this.playingPlaylist = playingPlaylist;}

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

	public void LoadDataPlaylist(UserCommands userCommands1, LibraryInput libraryInput) {

		for (Playlist playlist : userCommands1.getPlaylist()) {
			if (playlist.getPlaylistName().equals(userCommands1.getPlayingPlaylist()) == true) {
				userCommands1.setSelectedSong(playlist.getSongs().get(0).getName());
				userCommands1.LoadData(userCommands1, libraryInput);
				userCommands1.setLastCommand("load");
				int playlistDuration = 0;
				for (SongInput songInput : playlist.getSongs())
					playlistDuration += songInput.getDuration();
				userCommands1.getStats().setRemainedTime(playlistDuration);
			}
		}
	}

	public void LoadDataPodcast(UserCommands userCommands1, LibraryInput libraryInput) {
		if (userCommands1.getTrack().equals("podcast")) {
			ArrayList<PodcastInput> Podcasts = libraryInput.getPodcasts();
			PodcastInput podcast = new PodcastInput();
			for (PodcastInput podcastInput : Podcasts) {
				if (podcastInput.getName().equals(userCommands1.getSelectedSong())) {
					podcast = podcastInput;
					break;
				}
			}
			if (podcast.getName() != null) {
				Stats stats = new Stats();
				stats.setName(podcast.getName());
				int duration = 0;
				for (EpisodeInput episodeInput : podcast.getEpisodes())
					duration += episodeInput.getDuration();
				stats.setRemainedTime(duration);
				stats.setRepeat("No Repeat");
				stats.setPaused(false);
				stats.setShuffle(false);
				userCommands1.setStats(stats);
				userCommands1.setLastCommand("load");
			}
		}
	}
}
