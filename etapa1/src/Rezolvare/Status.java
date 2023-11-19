package Rezolvare;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.UserCommands;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;

public class Status extends Output{
	Stats stats;
	public Stats getStats() { return stats;}
	public void setStats(Stats stats) { this.stats = stats;}
	public void StatusCommand(Comanda comm, ArrayList<UserCommands> userCommands, ObjectMapper objectMapper, ArrayNode outputs) {
		Status status = new Status();
		for (UserCommands user : userCommands) {
			if (user.getUsername().equals(comm.getUsername()) && user.getTrack().equals("song"))
				status.showSongStatus(comm, user,objectMapper, outputs);
			if (user.getUsername().equals(comm.getUsername()) && user.getTrack().equals("podcast")) {
				status.podcastStatusShow(comm, user,objectMapper, outputs);
			}

		}
	}

	public void showSongStatus(Comanda comm, UserCommands user, ObjectMapper objectMapper, ArrayNode outputs){
				Status status = new Status();
				status.setCommand("status");
				status.setUser(comm.getUsername());
				status.setTimestamp(comm.getTimestamp());
				Stats stats = new Stats();
				stats.setName(user.getSelectedSong());
				if (user.getStats().isPaused() == false) {
					if (user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp() > 0)
						stats.setRemainedTime(user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp());
					else {
						stats.setRemainedTime(0);
						stats.setName("");
					}
				} else {
					stats.setRemainedTime(user.getStats().getRemainedTime());
				}
				user.getStats().setRemainedTime(stats.getRemainedTime());
				user.setLastTimestamp(comm.getTimestamp());
				stats.setRepeat(user.getStats().getRepeat());
				stats.setPaused(user.getStats().isPaused());
				stats.setShuffle(user.getStats().isShuffle());
				if (stats.getRemainedTime() == 0)
						stats.setPaused(true);
				status.setStats(stats);
				JsonNode node = objectMapper.valueToTree(status);
              	outputs.add(node);
//			}
//		}
	}

	public void podcastStatusShow(Comanda comm, UserCommands user, ObjectMapper objectMapper, ArrayNode outputs) {
				Status status = new Status();
				status.setCommand("status");
				status.setUser(comm.getUsername());
				status.setTimestamp(comm.getTimestamp());
				Stats stats = new Stats();
				stats.setName(user.getSelectedSong());
				user.setStats(stats);
				if (user.getStats().isPaused() == false) {
					if (user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp() > 0)
						stats.setRemainedTime(user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp());
					else {
						stats.setRemainedTime(0);
						stats.setName("");
					}
				} else {
					stats.setRemainedTime(user.getStats().getRemainedTime());
				}
				user.getStats().setRemainedTime(stats.getRemainedTime());
				user.setLastTimestamp(comm.getTimestamp());
				stats.setRepeat(user.getStats().getRepeat());
				stats.setPaused(user.getStats().isPaused());
				stats.setShuffle(user.getStats().isShuffle());
				if (stats.getRemainedTime() == 0)
					stats.setPaused(true);
				status.setStats(stats);
				JsonNode node = objectMapper.valueToTree(status);
				outputs.add(node);
			}
}
