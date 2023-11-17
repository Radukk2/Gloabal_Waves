package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.SongInput;
import fileio.input.UserCommands;

import java.util.ArrayList;

public class ShowPlaylists extends Output{
	ArrayList<PlaylistInput> result = new ArrayList<PlaylistInput>();
	public ArrayList<PlaylistInput> getResult() {return result;}
	public void setResult(ArrayList<PlaylistInput> result) {this.result = result;}

	public void ShowUserPlaylists(ArrayNode outputs, Comanda comm, ArrayList<UserCommands> userCommands, ObjectMapper objectMapper) {
		ShowPlaylists showPlaylists = new ShowPlaylists();
		showPlaylists.setUser(comm.getUsername());
		showPlaylists.setCommand(comm.getCommand());
		showPlaylists.setTimestamp(comm.getTimestamp());
		for (UserCommands userCommands1 : userCommands) {
			if (userCommands1 != null) {
				if (userCommands1.getUsername().equals(comm.getUsername())) {
					if (userCommands1.getPlaylist() != null) {
						for (Playlist playlist : userCommands1.getPlaylist()) {
							PlaylistInput playlistInput = new PlaylistInput();
							ArrayList<String> songNames = new ArrayList<String>();
							for (SongInput songInput : playlist.getSongs()) {
								songNames.add(songInput.getName());
							}
							playlistInput.setSongs(songNames);
							playlistInput.setFollowers(playlistInput.getFollowers());
							playlistInput.setName(playlist.getPlaylistName());
							if (playlist.visibility == null)
								playlistInput.setVisibility("public");
							else
								playlistInput.setVisibility(playlist.getVisibility());
							showPlaylists.getResult().add(playlistInput);
						}
						break;
					}
				}
			}
		}
		JsonNode node = objectMapper.valueToTree(showPlaylists);
		outputs.add(node);
	}


}
