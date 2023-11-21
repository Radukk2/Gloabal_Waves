package Rezolvare;

import java.util.ArrayList;

public class PlaylistInput {
	String name;
	ArrayList<String> songs;
	String visibility;
	int followers;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public ArrayList<String> getSongs() {return songs;}
	public void setSongs(ArrayList<String> songs) {this.songs = songs;}
	public String getVisibility() {return visibility;}
	public void setVisibility(String visibility) {this.visibility = visibility;}
	public int getFollowers() {return followers;}
	public void setFollowers(int followers) {this.followers = followers;}
}
