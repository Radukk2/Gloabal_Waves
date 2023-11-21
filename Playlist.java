package Rezolvare;

import fileio.input.SongInput;

import java.util.ArrayList;

public class Playlist {
	ArrayList<SongInput> songs;
	String playlistName;
	String playlistOwner;
	int followers;
	String visibility;
	public ArrayList<SongInput> getSongs() {return songs;}
	public void setPlaylist(ArrayList<SongInput> songs) {this.songs = songs;}
	public String getPlaylistName() {return playlistName;}
	public void setPlaylistName(String playlistName) {this.playlistName = playlistName;}
	public String getPlaylistOwner() {return playlistOwner;}
	public void setPlaylistOwner(String playlistOwner) {this.playlistOwner = playlistOwner;}
	public int getFollowers() {return followers;}
	public void setFollowers(int followers) {this.followers = followers;}

	public String getVisibility() {return visibility;}
	public void setVisibility(String visibility) {this.visibility = visibility;}
}
