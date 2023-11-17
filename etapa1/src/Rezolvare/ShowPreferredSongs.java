package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.UserCommands;

import java.util.ArrayList;

public class ShowPreferredSongs extends Output{
	ArrayList<String> result = new ArrayList<String>();
	public ArrayList<String> getResult() {return result;}
	public void setResult(ArrayList<String> result) {this.result = result;}

	public void preferedSongs(ArrayNode outputs, ArrayList<UserCommands> userCommands, Comanda comm, ObjectMapper objectMapper) {
		ShowPreferredSongs showPreferredSongs = new ShowPreferredSongs();
		showPreferredSongs.setCommand(comm.getCommand());
		showPreferredSongs.setUser(comm.getUsername());
		showPreferredSongs.setTimestamp(comm.getTimestamp());
		for (UserCommands userCommands1 : userCommands) {
			if (userCommands1 != null) {
				if (userCommands1.getUsername().equals(comm.getUsername()))
					showPreferredSongs.setResult(userCommands1.getLikedSongs());
			}
		}
		JsonNode node = objectMapper.valueToTree(showPreferredSongs);
		outputs.add(node);
	}
}
