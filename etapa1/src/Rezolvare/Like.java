package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.UserCommands;

import java.util.ArrayList;

public class Like extends Output{

	public void LikeUnlikeSongs (ObjectMapper objectMapper, Comanda comanda, ArrayNode outputs, ArrayList<UserCommands> userCommands) {
		Like like = new Like();
		like.setCommand("like");
		like.setUser(comanda.getUsername());
		like.setTimestamp(comanda.getTimestamp());
		for (UserCommands userCommands1 : userCommands) {
			if (userCommands1.getUsername().equals(comanda.getUsername())) {
				if (userCommands1.getLastCommand().equals("load")) {
					if (userCommands1.getTrack().equals("song") == false) {
						like.setMessage("Loaded source is not a song");
						break;
					}
					else {
						if (userCommands1.getLikedSongs().size() == 0) {
							userCommands1.getLikedSongs().add(userCommands1.getSelectedSong());
							like.setMessage("Like registered successfully.");
						} else {
							boolean found = false;
							for (String song : userCommands1.getLikedSongs()) {
								if (song.equals(userCommands1.getSelectedSong())) {
									found = true;
									like.setMessage("Unlike registered successfully.");
									userCommands1.getLikedSongs().remove(song);
									break;
								}
							}
							if (found == false) {
								like.setMessage("Like registered successfully.");
								userCommands1.getLikedSongs().add(userCommands1.getSelectedSong());
							}
						}
					}
				}
				else
					like.setMessage("Please load a source before liking or unliking.");
			}
		}
		JsonNode node = objectMapper.valueToTree(like);
		outputs.add(node);
	}
}
