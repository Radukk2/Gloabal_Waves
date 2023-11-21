package Rezolvare;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.EpisodeInput;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserCommands;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;

public class Status extends Output {
	Stats stats;

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public void StatusCommand(Comanda comm, ArrayList<UserCommands> userCommands, ObjectMapper objectMapper, ArrayNode outputs) {
		Status status = new Status();
		for (UserCommands user : userCommands) {
			if (user.getUsername().equals(comm.getUsername()) && user.getTrack().equals("song"))
				status.showSongStatus(comm, user, objectMapper, outputs);
			if (user.getUsername().equals(comm.getUsername()) && user.getTrack().equals("podcast")) {
				status.podcastStatusShow(comm, user, objectMapper, outputs);
			}
			if (user.getUsername().equals(comm.getUsername()) && user.getTrack().equals("playlist")) {
				status.playlistStatusShow(comm, user, objectMapper, outputs);
			}
		}
	}

	public void showSongStatus(Comanda comm, UserCommands user, ObjectMapper objectMapper, ArrayNode outputs) {
		Status status = new Status();
		status.setCommand("status");
		status.setUser(comm.getUsername());
		status.setTimestamp(comm.getTimestamp());
		Stats stats = new Stats();
		stats.setName(user.getSelectedSong());
		if (!user.getStats().isPaused()) {
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

	public void podcastStatusShow(Comanda comm, UserCommands user, ObjectMapper objectMapper, ArrayNode outputs) {
		Status status = new Status();
		status.setCommand("status");
		status.setUser(comm.getUsername());
		status.setTimestamp(comm.getTimestamp());
		Stats stats = new Stats();
		stats.setName(user.getStats().getName());
		if (user.getPastPodcasts() != null) {
			for (Podcast podcast : user.getPastPodcasts())
				if (user.getPodcast().getPodcastInput().getName().equals(podcast.getPodcastInput().getName())) {
					if (user.getStats().isPaused() == false) {
						if (!user.getStats().isPaused()) {
							if (user.getStats().getRemainedTime() - comm.getTimestamp() + user.getLastTimestamp() > 0) {
								stats.setRemainedTime(podcast.getRemainingTime() - comm.getTimestamp() + user.getLastTimestamp());
//								podcast.setRemainingTime(stats.getRemainedTime());
							} else {
								EpisodeInput episodeInput = new EpisodeInput();
								boolean ok = false;
								for (EpisodeInput episodeInput1 : user.getPodcast().getPodcastInput().getEpisodes()) {
									int index = user.getPodcast().getPodcastInput().getEpisodes().indexOf(episodeInput1);
									if (episodeInput1.getName().equals(user.getCurrentEpisode())) {
										for (int i = index + 1; i < user.getPodcast().getPodcastInput().getEpisodes().size(); i++) {
											EpisodeInput episodeInput2 = user.getPodcast().getPodcastInput().getEpisodes().get(i);
											stats.setName(episodeInput2.getName());
											user.setCurrentEpisode(episodeInput2.getName());
											user.getStats().setRemainedTime(user.getStats().getRemainedTime() + episodeInput2.getDuration());
											if (user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp() > 0) {
												ok = true;
												stats.setRemainedTime(user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp());
												break;
											}
										}
									}
								}
								if (!ok) {
									stats.setRemainedTime(0);
									stats.setName("");
								}
							}
						} else {
							stats.setRemainedTime(user.getStats().getRemainedTime());
						}
						user.getStats().setRemainedTime(stats.getRemainedTime());
						user.setLastTimestamp(comm.getTimestamp());
						stats.setRepeat(user.getStats().getRepeat());
						stats.setPaused(user.getStats().isPaused());
						stats.setShuffle(user.getStats().isShuffle());
						user.setStats(stats);
						if (stats.getRemainedTime() == 0)
							stats.setPaused(true);
						status.setStats(stats);
						JsonNode node = objectMapper.valueToTree(status);
						outputs.add(node);
					}
					user.getPastPodcasts().remove(podcast);
					return;
				}
		}
		if (!user.getStats().isPaused()) {
			if (user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp() > 0) {
				stats.setRemainedTime(user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp());
			} else {
				EpisodeInput episodeInput = new EpisodeInput();
				boolean ok = false;
				for (EpisodeInput episodeInput1 : user.getPodcast().getPodcastInput().getEpisodes()) {
					int index = user.getPodcast().getPodcastInput().getEpisodes().indexOf(episodeInput1);
					if (episodeInput1.getName().equals(user.getCurrentEpisode())) {
						for (int i = index + 1; i < user.getPodcast().getPodcastInput().getEpisodes().size(); i++) {
							EpisodeInput episodeInput2 = user.getPodcast().getPodcastInput().getEpisodes().get(i);
							stats.setName(episodeInput2.getName());
							user.getStats().setName(episodeInput2.getName());
							user.getStats().setRemainedTime(user.getStats().getRemainedTime() + episodeInput2.getDuration());
							if (user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp() > 0) {
								ok = true;
								stats.setRemainedTime(user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp());
								break;
							}
						}
					}
				}
				if (!ok) {
					stats.setRemainedTime(0);
					stats.setName("");
				}
			}
		} else {
			stats.setRemainedTime(user.getStats().getRemainedTime());
		}
		user.getStats().setRemainedTime(stats.getRemainedTime());
		user.setLastTimestamp(comm.getTimestamp());
		stats.setRepeat(user.getStats().getRepeat());
		stats.setPaused(user.getStats().isPaused());
		stats.setShuffle(user.getStats().isShuffle());
		user.setStats(stats);
		if (stats.getRemainedTime() == 0)
			stats.setPaused(true);
		status.setStats(stats);
		JsonNode node = objectMapper.valueToTree(status);
		outputs.add(node);
	}

	private void playlistStatusShow(Comanda comm, UserCommands user, ObjectMapper objectMapper, ArrayNode outputs) {
		Status status = new Status();
		status.setCommand("status");
		status.setUser(comm.getUsername());
		status.setTimestamp(comm.getTimestamp());
		Stats stats = new Stats();
		Playlist newPlaylist = new Playlist();
		if (user.getPlaylist() != null)
			for (Playlist playlist : user.getPlaylist()) {
				if (playlist.getPlaylistName().equals(user.getPlayingPlaylist())) {
					newPlaylist = playlist;
					break;
				}
			}
		if (newPlaylist.getSongs() != null) {
			stats.setName(user.getSelectedSong());
			if (user.getStats().isPaused() == false) {
				if (user.getStats().getRemainedTime() - comm.getTimestamp() + user.getLastTimestamp() > 0)
					stats.setRemainedTime(user.getStats().getRemainedTime() - comm.getTimestamp() + user.getLastTimestamp());
				else {
//					SongInput songInput = new SongInput();
					int index = 0;
					for (SongInput songInput1 : newPlaylist.getSongs()) {
						if (songInput1.getName().equals(user.getSelectedSong())) {
//							songInput = songInput1;
							index++;
						}
					}
					index--;
//					boolean ok = false;
					boolean ok = false;
					for (int i = index + 1; i < newPlaylist.getSongs().size(); i++) {
						SongInput songInput = newPlaylist.getSongs().get(i);
						stats.setName(songInput.getName());
						user.getStats().setRemainedTime(user.getStats().getRemainedTime() + songInput.getDuration());
						if (user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp() > 0) {
							stats.setRemainedTime(user.getStats().getRemainedTime() - status.getTimestamp() + user.getLastTimestamp());
							ok = true;
							break;
						}
					}
					if (ok == false) {
						stats.setRemainedTime(0);
						stats.setName("");
					}

				}
			} else
				stats.setRemainedTime(user.getStats().getRemainedTime());

		}
		user.getStats().setRemainedTime(stats.getRemainedTime());
		user.setLastTimestamp(comm.getTimestamp());
		stats.setRepeat(user.getStats().getRepeat());
		stats.setPaused(user.getStats().isPaused());
		stats.setShuffle(user.getStats().isShuffle());
		user.setStats(stats);
		if (stats.getRemainedTime() == 0)
			stats.setPaused(true);
		status.setStats(stats);
		JsonNode node = objectMapper.valueToTree(status);
		outputs.add(node);
	}
}