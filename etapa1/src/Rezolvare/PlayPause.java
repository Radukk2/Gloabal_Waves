package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserCommands;

import java.util.ArrayList;

public class PlayPause extends Output{

	public void playPauseCommand(Comanda comm, ArrayList<UserCommands> userCommands, ObjectMapper objectMapper, ArrayNode outputs) {
		for (UserCommands user : userCommands) {
			if (user.getUsername().equals(comm.getUsername()) && user.getTrack().equals("song")) {
				PlayPause playPause = new PlayPause();
				playPause.setCommand("playPause");
				playPause.setTimestamp(comm.getTimestamp());
				playPause.setUser(comm.getUsername());
				if (user.getLastCommand().equals("load") == false) {
					playPause.setMessage("Please load a source before attempting to pause or resume playback.");
				}
				boolean ok = false;
				if (user.getStats().isPaused() == true) {
					playPause.setMessage("Playback resumed successfully.");
					user.getStats().setPaused(false);
					ok = true;

				}
				if (user.getStats().isPaused() == false && ok == false) {
					playPause.setMessage("Playback paused successfully.");
					user.getStats().setPaused(true);
					user.getStats().setRemainedTime(user.getStats().getRemainedTime() - comm.getTimestamp() + user.getLastTimestamp());
				}
				user.setLastTimestamp(comm.getTimestamp());
				JsonNode node = objectMapper.valueToTree(playPause);
				outputs.add(node);
				break;
			}
		}
	}
}
