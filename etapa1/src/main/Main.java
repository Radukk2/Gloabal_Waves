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

                }
                boolean ok = false;
                for (UserCommands user : userCommands) {
                    if (user.getUsername().equals(comm.getUsername())) {
                        user.setTrack(comm.getType());
                        if (user.getTrack().equals("song"))
                            user.setResults(searchCommands.SearchSongs(library, comm));
                        if (user.getTrack().equals("podcast"))
                            user.setResults(searchCommands.SearchPodcast(library, comm));
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
                }

            }
            if (comm.getCommand().equals("select")) {
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
                }
                else {
                    output.setMessage("Please conduct a search before making a selection.");
                }
                JsonNode node = objectMapper.valueToTree(output);
                outputs.add(node);
                userCommands1.setLastCommand("select");
            }
                if (comm.getCommand().equals("load")) {
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
                        userCommands1.LoadData(userCommands1, library);
                        userCommands1.setLastTimestamp(comm.getTimestamp());
                        output.setMessage("Playback loaded successfully.");
                    }
                    else
                        output.setMessage("Please select a source before attempting to load.");
                    JsonNode node = objectMapper.valueToTree(output);
                    outputs.add(node);
                }
                if (comm.getCommand().equals("status")) {
                    Status status = new Status();
                    status.showStatus(comm, userCommands,objectMapper, outputs);
                }
                if (comm.getCommand().equals("playPause")) {
                    PlayPause playPause = new PlayPause();
                    playPause.playPauseCommand(comm, userCommands, objectMapper, outputs);
                }
                if (comm.getCommand().equals("createPlaylist")) {

                }
            }
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}
