package main;

import Rezolvare.*;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.UserCommands;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput,
                              final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);
        ArrayNode outputs = objectMapper.createArrayNode();
        // TODO add your implementation
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ArrayList<Comanda> commands = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePathInput), new TypeReference<>() {
		});
        ArrayList<UserCommands> userCommands = new ArrayList<>();
        for (Comanda comm : commands) {
            if (comm.getCommand().equals("search")) {
                SearchCommands searchCommands = new SearchCommands();
                searchCommands.searchFinal(comm, library, objectMapper, outputs, userCommands);
            }
            if (comm.getCommand().equals("select")) {
                Output output = new Output();
                output.selectTrack(comm, userCommands, objectMapper, outputs);
            }
                if (comm.getCommand().equals("load")) {
                    Output output = new Output();
                    output.loadCommand(comm, userCommands, library, outputs, objectMapper);
                }
                if (comm.getCommand().equals("status")) {
                    Status status = new Status();
                    status.StatusCommand(comm, userCommands,objectMapper, outputs);
                }
                if (comm.getCommand().equals("playPause")) {
                    PlayPause playPause = new PlayPause();
                    playPause.playPauseCommand(comm, userCommands, objectMapper, outputs);
                }
                if (comm.getCommand().equals("createPlaylist")) {
                    CretatePlaylist cretatePlaylist = new CretatePlaylist();
                    cretatePlaylist.playlistInitialize(comm, objectMapper, outputs, userCommands);
                }
                if (comm.getCommand().equals("addRemoveInPlaylist")) {
                    AddRemoveInPlaylist addRemoveInPlaylist = new AddRemoveInPlaylist();
                    addRemoveInPlaylist.addInPlaylist(comm, userCommands, objectMapper, outputs, library);
                }
                if (comm.getCommand().equals("like")) {
                    Like like = new Like();
                    like.LikeUnlikeSongs(objectMapper, comm, outputs, userCommands);
                }
                if (comm.getCommand().equals("showPlaylists")) {
                    ShowPlaylists showPlaylists = new ShowPlaylists();
                    showPlaylists.ShowUserPlaylists(outputs, comm, userCommands, objectMapper);
                }
                if (comm.getCommand().equals("showPreferredSongs")) {
                    ShowPreferredSongs showPreferredSongs = new ShowPreferredSongs();
                    showPreferredSongs.preferedSongs(outputs,userCommands, comm, objectMapper);
                }
            }
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}
