package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.SongInput;
import fileio.input.UserCommands;

import java.util.ArrayList;

public class CretatePlaylist extends Output{
	public void playlistInitialize (Comanda comanda, ObjectMapper objectMapper, ArrayNode outputs, ArrayList<UserCommands> userCommands) {
		CretatePlaylist cretatePlaylist = new CretatePlaylist();
		cretatePlaylist.setCommand("createPlaylist");
		cretatePlaylist.setUser(comanda.getUsername());
		cretatePlaylist.setTimestamp(comanda.getTimestamp());
		for (UserCommands user : userCommands) {
			if (comanda.getUsername().equals(user.getUsername())){
				if (user.getPlaylist() != null) {
					for (Playlist playlist1 : user.getPlaylist()) {
						if (comanda.getPlaylistName().equals(playlist1.getPlaylistName())) {
							cretatePlaylist.setMessage("A playlist with the same name already exists.");
							JsonNode node = objectMapper.valueToTree(cretatePlaylist);
							outputs.add(node);
							return;
						}
					}
					cretatePlaylist.setMessage("Playlist created successfully.");
					Playlist playlist = new Playlist();
					playlist.setPlaylistName(comanda.getPlaylistName());
					playlist.setPlaylistOwner(comanda.getUsername());
					user.getPlaylist().add(playlist);
					JsonNode node = objectMapper.valueToTree(cretatePlaylist);
					outputs.add(node);
					return;
				}
				cretatePlaylist.setMessage("Playlist created successfully.");
				Playlist playlist = new Playlist();
				playlist.setPlaylistName(comanda.getPlaylistName());
				playlist.setPlaylistOwner(comanda.getUsername());
				ArrayList<Playlist> newArray= new ArrayList<Playlist>();
				user.setPlaylist(newArray);
				user.getPlaylist().add(playlist);
				JsonNode node = objectMapper.valueToTree(cretatePlaylist);
				outputs.add(node);
				return;
			}
		}
		if (userCommands.size() == 0) {
			UserCommands userCommands1 = new UserCommands();
			userCommands1.setUsername(comanda.getUsername());
			cretatePlaylist.setMessage("Playlist created successfully.");
			Playlist playlist = new Playlist();
			playlist.setPlaylistName(comanda.getPlaylistName());
			playlist.setPlaylistOwner(comanda.getUsername());
			ArrayList<Playlist> arrayList= new ArrayList<Playlist>();
			arrayList.add(playlist);
			userCommands1.setPlaylist(arrayList);
			userCommands.add(userCommands1);
			JsonNode node = objectMapper.valueToTree(cretatePlaylist);
			outputs.add(node);
		}
	}
}
