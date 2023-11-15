package Rezolvare;

import java.util.ArrayList;

class Filters {
    String name;
    String album;
    ArrayList<String> tags;
    String lyrics;
    String genre;
    String releaseYear;
    String artist;
    String owner;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }
    public ArrayList<String> getTags() { return tags; }
    public void setTags(ArrayList<String> tags) { this.tags = tags; }
    public String getLyrics() { return lyrics; }
    public void setLyrics(String lyrics) { this.lyrics = lyrics; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getReleaseYear() { return releaseYear; }
    public void setReleaseYear(String releaseYear) { this.releaseYear = releaseYear; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

}

public class Comanda {
    String command;
    String username;
    int timestamp;
    int itemNumber;
    int playlistId;
    String type;
    Filters filters;
    String playlistName;
    int seed;
    public int getSeed() {return seed;}
    public void setSeed(int seed) {this.seed = seed;}
    public int getPlaylistId() {return playlistId;}
    public void setPlaylistId(int playlistId) {this.playlistId = playlistId;}
    public String getPlaylistName() {return playlistName;}
    public void setPlaylistName(String playlistName) {this.playlistName = playlistName;}
    public Filters getFilters() { return filters; }
    public void setFilters(Filters filters) { this.filters = filters; }
    public String getCommand() { return command; }
    public String getUsername() { return username; }
    public int getTimestamp() { return timestamp; }

    public void setCommand(String command) { this.command = command; }
    public void setUsername(String username) { this.username = username; }
    public void setTimestamp(int timestamp) { this.timestamp = timestamp; }

    public int getItemNumber() { return itemNumber; }
    public void setItemNumber(int itemNumber) { this.itemNumber = itemNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Comanda() {}
}
 class SongComands extends Comanda{

 }