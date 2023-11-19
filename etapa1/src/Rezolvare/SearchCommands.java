package Rezolvare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserCommands;

import java.util.ArrayList;

public class SearchCommands extends Output{
	ArrayList<String> results;
	public ArrayList<String> getResults() {return results;}
	public void setResults(ArrayList<String> results) {this.results = results;}
	public SearchCommands() {
	}
	public ArrayList<String> SearchSongs(LibraryInput libraryInput, Comanda comanda) {
		ArrayList<SongInput> songs = libraryInput.getSongs();
		int nrElements = 0;
		results = new ArrayList<String>();
		for (SongInput song : songs) {
			results.add(song.getName());
			if (comanda.getFilters().getAlbum() != null && !comanda.getFilters().getAlbum().equalsIgnoreCase(song.getAlbum()))
				results.remove(song.getName());
			if (comanda.getFilters().getArtist() != null && !comanda.getFilters().getArtist().equalsIgnoreCase(song.getArtist()))
				results.remove(song.getName());
			if (comanda.getFilters().getGenre() != null && !comanda.getFilters().getGenre().equalsIgnoreCase(song.getGenre()))
				results.remove(song.getName());
			if (comanda.getFilters().getName() != null && !song.getName().startsWith(comanda.getFilters().getName()))
				results.remove(song.getName());
			if (comanda.getFilters().getLyrics() != null && !song.getLyrics().contains(comanda.getFilters().getLyrics()))
				results.remove(song.getName());
			if (comanda.getFilters().getTags() != null) {
				for (String str : comanda.getFilters().getTags())
					if (song.getTags().contains(str) == false) {
						results.remove(song.getName());
						break;
					}
			}
			if (comanda.getFilters().getReleaseYear() != null) {
				char sign = comanda.getFilters().getReleaseYear().charAt(0);
				String year1 = comanda.getFilters().getReleaseYear().substring(1);
				int an1 = Integer.parseInt(year1);
				int an2 = song.getReleaseYear();
				if (sign == '>' && an1 >= an2)
					results.remove(song.getName());
				if (sign == '<' && an1 <= an2)
					results.remove(song.getName());
			}
			nrElements++;
		}
		if (nrElements >= 5) {
			ArrayList<String> finaList = new ArrayList<String>();
			int i = 0;
			for (String nume : results) {
				finaList.add(nume);
				i++;
				if (i == 5)
					break;
			}
			results = finaList;
		}
		return results;
	}
	public ArrayList<String> SearchPodcast(LibraryInput libraryInput, Comanda comanda) {
		ArrayList<PodcastInput> podcasts = libraryInput.getPodcasts();
		int nrElements = 0;
		ArrayList<String> results = new ArrayList<>();
		for (PodcastInput podcast : podcasts) {
			results.add(podcast.getName());
			if (comanda.getFilters().getName() != null && !podcast.getName().startsWith(comanda.getFilters().getName())) {
				results.remove(podcast.getName());
				continue;
			}
			if (comanda.getFilters().getOwner() != null && !comanda.getFilters().getOwner().equalsIgnoreCase(podcast.getOwner())) {
				results.remove(podcast.getName());
				continue;
			}
			nrElements++;
		}
		if (nrElements >= 5) {
			ArrayList<String> finaList = new ArrayList<String>();
			int i = 0;
			for (String nume : results) {
				finaList.add(nume);
				i++;
				if (i == 5)
					break;
			}
			results = finaList;
		}
		return results;
	}

	public ArrayList<String> SearchPlaylist(Comanda comanda, ArrayList<UserCommands> userCommands) {
		ArrayList<Playlist> playlistArrayList = new ArrayList<Playlist>();
		results = new ArrayList<String>();
		for (UserCommands userCommands1 : userCommands) {
			if (userCommands1.getPlaylist() != null) {
				for (Playlist playlist : userCommands1.getPlaylist())
					playlistArrayList.add(playlist);
			}
		}
		int nrElements = 0;
		for (Playlist playlist : playlistArrayList) {
			results.add(playlist.getPlaylistName());
			if (comanda.getFilters().getName() != null && !playlist.getPlaylistName().startsWith(comanda.getFilters().getName()))
				results.remove(playlist.getPlaylistName());
			if (comanda.getFilters().getOwner() != null && !playlist.getPlaylistOwner().equals(comanda.getFilters().getOwner()))
				results.remove(playlist.getPlaylistName());
			nrElements++;
		}
		if (nrElements >= 5) {
			ArrayList<String> finaList = new ArrayList<String>();
			int i = 0;
			for (String nume : results) {
				finaList.add(nume);
				i++;
				if (i == 5)
					break;
			}
			results = finaList;
		}
		return results;
	}
	public void searchFinal (Comanda comm, LibraryInput library, ObjectMapper objectMapper,
							 ArrayNode outputs, ArrayList<UserCommands> userCommands) {
		SearchCommands searchCommands = new SearchCommands();
		searchCommands.setTimestamp(comm.getTimestamp());
		searchCommands.setCommand(comm.getCommand());
		searchCommands.setUser(comm.getUsername());
		if (comm.getType().equals("song")) {
			searchCommands.setResults(searchCommands.SearchSongs(library, comm));
			if (searchCommands.SearchSongs(library, comm) != null) {
				ArrayList<String> str = searchCommands.SearchSongs(library, comm);
				int contor = str.size();
				searchCommands.setMessage("Search returned " + contor + " results");
			}
			JsonNode node = objectMapper.valueToTree(searchCommands);
			outputs.add(node);
		}
		if (comm.getType().equals("podcast")) {
			searchCommands.setResults(searchCommands.SearchPodcast(library, comm));
			if (searchCommands.SearchPodcast(library, comm) != null) {
				ArrayList<String> str = searchCommands.SearchPodcast(library, comm);
				int contor = str.size();
				searchCommands.setMessage("Search returned " + contor + " results");
			}
			JsonNode node = objectMapper.valueToTree(searchCommands);
			outputs.add(node);
		}
		if (comm.getType().equals("playlist") == true) {
			searchCommands.setResults(searchCommands.SearchPlaylist(comm, userCommands));
			if (searchCommands.SearchPodcast(library, comm) != null) {
				ArrayList<String> str = searchCommands.SearchPlaylist(comm, userCommands);
				int contor = str.size();
				searchCommands.setMessage("Search returned " + contor + " results");
			}
			JsonNode node = objectMapper.valueToTree(searchCommands);
			outputs.add(node);
		}
		boolean ok = false;
		for (UserCommands user : userCommands) {
			if (user.getUsername().equals(comm.getUsername())) {
				user.setTrack(comm.getType());
				if (user.getTrack().equals("song"))
					user.setResults(searchCommands.SearchSongs(library, comm));
				if (user.getTrack().equals("podcast"))
					user.setResults(searchCommands.SearchPodcast(library, comm));
				if (user.getTrack().equals("playlist"))
					user.setResults(searchCommands.SearchPlaylist(comm, userCommands));
				user.setLastCommand("search");
				ok = true;
				break;
			}
		}
		if (!ok) {
			UserCommands userCommands1 = new UserCommands();
			userCommands1.setLastCommand("search");
			userCommands1.setUsername(comm.getUsername());
			userCommands1.setTrack(comm.getType());
			userCommands.add(userCommands1);
			if (userCommands1.getTrack().equals("song"))
				userCommands1.setResults(searchCommands.SearchSongs(library, comm));
			if (userCommands1.getTrack().equals("podcast"))
				userCommands1.setResults(searchCommands.SearchPodcast(library, comm));
			if (userCommands1.getTrack().equals("playlist"))
				userCommands1.setResults(searchCommands.SearchPlaylist( comm, userCommands));
		}
	}
}


