package src;

import java.util.*;

public class CommandParser {
    public static class ParsedCommand {
        String command;
        String[] args;
        String outputFile;
        String inputFile;
        boolean append;
        boolean background;

        ParsedCommand(String command, String[] args, String outputFile, String inputFile, boolean append, boolean background) {
            this.command = command;
            this.args = args;
            this.outputFile = outputFile;
            this.inputFile = inputFile;
            this.append = append;
            this.background = background;
        }
    }

    public static ParsedCommand parse(String input) {
        String outputFile = null;
        String inputFile = null;
        boolean append = false;
        boolean background = input.endsWith("&");

        if (background) {
            input = input.substring(0, input.length() - 1).trim();
        }

        if (input.contains(">")) {
            String[] parts = input.split(">");
            input = parts[0].trim();
            outputFile = parts[1].trim();
            append = input.contains(">>");
            if (append) input = input.replace(">>", ">");
        }

        if (input.contains("<")) {
            String[] parts = input.split("<");
            input = parts[0].trim();
            inputFile = parts[1].trim();
        }

        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        boolean inQuotes = false;

        for (char c : input.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (c == ' ' && !inQuotes) {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token = new StringBuilder();
                }
                continue;
            }
            token.append(c);
        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }

        if (tokens.isEmpty()) {
            return new ParsedCommand("", new String[0], outputFile, inputFile, append, background);
        }

        String command = tokens.get(0);
        String[] args = tokens.subList(1, tokens.size()).toArray(new String[0]);
        return new ParsedCommand(command, args, outputFile, inputFile, append, background);
    }
}