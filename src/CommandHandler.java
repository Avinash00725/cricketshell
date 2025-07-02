package src;

import java.io.File;
import java.util.Arrays;

public class CommandHandler {
    public static void executeCommand(String input, ShellState shell) {
        shell.addToHistory(input);

        String[] tokens = input.split("\\s+");
        String command = tokens[0];
        String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

        switch (command) {
            case "field" -> ShellUtils.listFiles(shell);
            case "walk" -> ShellUtils.changeDirectory(args, shell);
            case "innings" -> System.out.println(shell.getCurrentDirectory());
            case "duck" -> ShellUtils.deleteFile(args, shell);
            case "score" -> ShellUtils.showSystemStats();
            case "scorecard" -> ShellUtils.showHistory(shell);
            case "lineup" -> ShellUtils.defineVariable(args, shell);
            case "jersey" -> ShellUtils.createAlias(args, shell);
            case "bowl" -> ShellUtils.runSystemCommand(args, shell);
            case "replay" -> ScriptRunner.runScript(args, shell);
            case "umpire" -> Themes.printHelp();
            default -> System.out.println("Unknown cricket command: " + command);
        }
    }
}
