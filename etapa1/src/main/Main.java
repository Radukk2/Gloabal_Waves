package main;

import Rezolvare.Comanda;
import Rezolvare.Output;
import Rezolvare.SearchCommands;
import Rezolvare.SelectCommand;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.UserCommands;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Filter;

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
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        ArrayList<Comanda> commands = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePathInput),
                new TypeReference<ArrayList<Comanda>>() {});
        ArrayList<UserCommands> userCommands = new ArrayList<UserCommands>();
        for (Comanda comm : commands) {
            if (comm.getCommand().equals("search")) {
                SearchCommands searchCommands = new SearchCommands();
                searchCommands.setTimestamp(comm.getTimestamp());
                searchCommands.setCommand(comm.getCommand());
                searchCommands.setUser(comm.getUsername());
                if (comm.getType().equals("song") == true) {
                    searchCommands.setResults(searchCommands.SearchSongs(library, comm));
                    if (searchCommands.SearchSongs(library, comm) != null) {
                        ArrayList<String> str = searchCommands.SearchSongs(library, comm);
                        int contor = str.size();
                        searchCommands.setMessage("Search returned " + contor + " results");
                    }
                    JsonNode node = objectMapper.valueToTree(searchCommands);
                    outputs.add(node);
                }
                if (comm.getType().equals("podcast") == true) {
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
                UserCommands userCommands1 = new UserCommands();
                userCommands1.setLastCommand(comm.getCommand());
                userCommands1.setUsername(comm.getUsername());
                userCommands.add(userCommands1);
            }
            if (comm.getCommand().equals("select")) {
                Output output = new Output();
                int index = commands.indexOf(comm) - 1;
                Comanda prevComm = commands.get(index);
                output.setCommand("select");
                output.setTimestamp(comm.getTimestamp());
                output.setTimestamp(comm.getTimestamp());
                output.setUser(comm.getUsername());
                if (prevComm.getCommand().equals("search") == true) {
                    if (prevComm.getType().equals("song")) {
                        SearchCommands newSearch = new SearchCommands();
                        ArrayList<String> str = new ArrayList<String>();
                        str = newSearch.SearchSongs(library, prevComm);
                        if (str.size() < comm.getItemNumber())
                            output.setMessage("The selected ID is too high.");
                        else {
                            String elem = str.get(comm.getItemNumber() - 1);
                            output.setMessage("Successfully selected " + elem + ".");
                        }
                    }
                }
                else {
                    output.setMessage("Please conduct a search before making a selection.");
                }
                JsonNode node = objectMapper.valueToTree(output);
                outputs.add(node);
            }
            if (comm.getCommand().equals("load") == true) {
                Output output = new Output();
                int index = commands.indexOf(comm) - 1;
                Comanda prevComm = commands.get(index);
                if (prevComm.getCommand().equals("select")) {

                }
            }
        }
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}
