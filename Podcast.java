package Rezolvare;

import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;

public class Podcast {
	PodcastInput podcastInput = new PodcastInput();
	EpisodeInput episodeInput = new EpisodeInput();
	int remainingTime;
	String currentEpisode;
	public PodcastInput getPodcastInput() {return podcastInput;}
	public void setPodcastInput(PodcastInput podcastInput) {this.podcastInput = podcastInput;}
	public int getRemainingTime() {return remainingTime;}
	public void setRemainingTime(int remainingTime) {this.remainingTime = remainingTime;}
	public EpisodeInput getEpisodeInput() {return episodeInput;}
	public void setEpisodeInput(EpisodeInput episodeInput) {this.episodeInput = episodeInput;}
	public String getCurrentEpisode() {return currentEpisode;}
	public void setCurrentEpisode(String currentEpisode) {this.currentEpisode = currentEpisode;}
}
