package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserCommands;

import java.util.ArrayList;


public class AddRemoveInPlaylist extends Output{
	public SongInput findSongGlobaly(LibraryInput libraryInput, String songName) {
		ArrayList<SongInput> songs = new ArrayList<SongInput>();
		songs = libraryInput.getSongs();
		for (SongInput song : songs) {
			if (song.getName().equals(songName) == true)
				return song;
		}
		return null;
	}
	public SongInput findSongLocally(ArrayList<SongInput> list, String songName) {
		for (SongInput song : list)
			if (song.getName().equals(songName) == true)
					return song;
		return null;
	}
	public void addInPlaylist(Comanda comanda, ArrayList<UserCommands> userCommands, ObjectMapper objectMapper, ArrayNode outputs, LibraryInput libraryInput) {
		AddRemoveInPlaylist addRemoveInPlaylist = new AddRemoveInPlaylist();
		addRemoveInPlaylist.setCommand("addRemoveInPlaylist");
		addRemoveInPlaylist.setTimestamp(comanda.getTimestamp());
		addRemoveInPlaylist.setUser(comanda.getUsername());
		for  (UserCommands user : userCommands) {
			if (user.getUsername().equals(comanda.getUsername())) {
				if (!user.getTrack().equals("song")) {
					addRemoveInPlaylist.setMessage("The loaded source is not a song.");
					break;
				}
				if (user.getLastCommand().equals("load"))  {
					if (user.getPlaylist() != null) {
						Playlist playlist = user.getPlaylist().get(comanda.getPlaylistId() - 1);
						if (playlist.getSongs() == null) {
							addRemoveInPlaylist.setMessage("Successfully added to playlist.");
							SongInput songInput = new SongInput();
							songInput = findSongGlobaly(libraryInput, user.getSelectedSong());
							ArrayList<SongInput> songInputs = new ArrayList<SongInput>();
							playlist.setPlaylist(songInputs);
							playlist.getSongs().add(songInput);
						} else {
							SongInput newSong = new SongInput();
							newSong = findSongLocally(playlist.getSongs(), user.getSelectedSong());
							if (newSong == null) {
								SongInput songInput = new SongInput();
								songInput = findSongGlobaly(libraryInput, user.getSelectedSong());
								playlist.getSongs().add(songInput);
								addRemoveInPlaylist.setMessage("Successfully added to playlist.");
							} else {
								playlist.getSongs().remove(newSong);
								addRemoveInPlaylist.setMessage("Successfully removed from playlist.");
							}
						}
					} else
					addRemoveInPlaylist.setMessage("The specified playlist does not exist.");
				} else
					addRemoveInPlaylist.setMessage("Please load a source before adding to or removing from the playlist.");

			}
		}
		JsonNode node = objectMapper.valueToTree(addRemoveInPlaylist);
		outputs.add(node);
	}
}
