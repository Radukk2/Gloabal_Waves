package Rezolvare;

import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
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
}


