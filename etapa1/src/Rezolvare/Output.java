package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.UserCommands;

import java.util.ArrayList;
import java.util.List;

public class Output {
	String command;
	public String getCommand() {return command;}
	public void setCommand(String command) {this.command = command;}
	String user;
	public String getUser() {return user;}
	public void setUser(String user) {this.user = user;}
	int timestamp;
	public int getTimestamp() {return timestamp;}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	String message;
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	public Output() {}
	public void selectTrack(Comanda comm, ArrayList<UserCommands> userCommands, ObjectMapper objectMapper, ArrayNode outputs) {
		Output output = new Output();
		output.setCommand("select");
		UserCommands userCommands1 = new UserCommands();
		for (UserCommands user : userCommands) {
			if (comm.getUsername().equals(user.getUsername())) {
				userCommands1 = user;
				break;
			}
		}
		output.setTimestamp(comm.getTimestamp());
		output.setTimestamp(comm.getTimestamp());
		output.setUser(comm.getUsername());
		if (userCommands1.getLastCommand().equals("search")) {
			if (userCommands1.getTrack().equals("song")) {
				if (userCommands1.getResults().size() < comm.getItemNumber())
					output.setMessage("The selected ID is too high.");
				else {
					String elem = userCommands1.getResults().get(comm.getItemNumber() - 1);
					output.setMessage("Successfully selected " + elem + ".");
					userCommands1.setSelectedSong(elem);
				}
			}
			if (userCommands1.getTrack().equals("podcast")) {
				if (userCommands1.getResults().size() < comm.getItemNumber())
					output.setMessage("The selected ID is too high.");
				else {
					String elem = userCommands1.getResults().get(comm.getItemNumber() - 1);
					output.setMessage("Successfully selected " + elem + ".");
					userCommands1.setSelectedSong(elem);
				}
			}
			if (userCommands1.getTrack().equals("playlist")) {
				if (userCommands1.getResults().size() < comm.getItemNumber())
					output.setMessage("The selected ID is too high.");
				else {
					String elem = userCommands1.getResults().get(comm.getItemNumber() - 1);
					output.setMessage("Successfully selected " + elem + ".");
//					userCommands1.setSelectedSong(elem);
					userCommands1.setPlayingPlaylist(elem);
				}
			}
		}
		else {
			output.setMessage("Please conduct a search before making a selection.");
		}

		JsonNode node = objectMapper.valueToTree(output);
		outputs.add(node);
		userCommands1.setLastCommand("select");
	}

	public void loadCommand(Comanda comm, ArrayList<UserCommands> userCommands
			, LibraryInput library, ArrayNode outputs, ObjectMapper objectMapper) {
		Output output = new Output();
		output.setCommand("load");
		output.setUser(comm.getUsername());
		output.setTimestamp(comm.getTimestamp());
		UserCommands userCommands1 = new UserCommands();
		for (UserCommands user : userCommands) {
			if (comm.getUsername().equals(user.getUsername())) {
				userCommands1 = user;
				break;
			}
		}
		if (userCommands1.getLastCommand().equals("select")) {
			if (userCommands1.getTrack().equals("song"))
				userCommands1.LoadData(userCommands1, library);
			if (userCommands1.getPlayingPlaylist() != null) {
//				userCommands1.LoadData(userCommands1, library);
//				userCommands1.
			}
			userCommands1.setLastTimestamp(comm.getTimestamp());
			output.setMessage("Playback loaded successfully.");
		}
		else
			output.setMessage("Please select a source before attempting to load.");
		JsonNode node = objectMapper.valueToTree(output);
		outputs.add(node);

	}
}
