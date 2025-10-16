// package src;

// import java.io.File;
// import java.util.Arrays;

// public class CommandHandler {
//     public static void executeCommand(String input, ShellState shell) {
//         shell.addToHistory(input);

//         String[] tokens = input.split("\\s+");
//         String command = tokens[0];
//         String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);

//         switch (command) {
//             case "field" -> ShellUtils.listFiles(shell);
//             case "walk" -> ShellUtils.changeDirectory(args, shell);
//             case "innings" -> System.out.println(shell.getCurrentDirectory());
//             case "duck" -> ShellUtils.deleteFile(args, shell);
//             case "score" -> ShellUtils.showSystemStats();
//             case "scorecard" -> ShellUtils.showHistory(shell);
//             case "lineup" -> ShellUtils.defineVariable(args, shell);
//             case "jersey" -> ShellUtils.createAlias(args, shell);
//             case "bowl" -> ShellUtils.runSystemCommand(args, shell);
//             case "replay" -> ScriptRunner.runScript(args, shell);
//             case "umpire" -> Themes.printHelp();
//             default -> System.out.println("Unknown cricket command: " + command);
//         }
//     }
// }


package src;

import java.io.*;
import java.util.Arrays;

public class CommandHandler {
    public static void executeCommand(String input, ShellState shell) throws IOException, InterruptedException {
        shell.addToHistory(input);
        CommandParser.ParsedCommand parsed = CommandParser.parse(input);
        String command = parsed.command;
        String[] args = parsed.args;
        String outputFile = parsed.outputFile;
        String inputFile = parsed.inputFile;
        boolean append = parsed.append;
        boolean background = parsed.background;

        // Resolve aliases
        if (shell.hasAlias(command)) {
            String[] aliasCmd = shell.getAlias(command);
            args = ShellUtils.merge(aliasCmd, args);
            command = aliasCmd[0];
            args = Arrays.copyOfRange(args, 1, args.length);
        }

        // Handle built-in commands
        if (executeBuiltInCommand(command, args, outputFile, inputFile, append, background, shell)) {
            return;
        }

        // Handle external commands
        ShellUtils.runSystemCommand(args, shell, outputFile, inputFile, append, background);
    }

    private static boolean executeBuiltInCommand(String command, String[] args, String outputFile, String inputFile, boolean append, boolean background, ShellState shell) throws IOException, InterruptedException {
        if (background && !command.equals("bowl")) {
            System.out.println("Background execution only supported for 'bowl' command.");
            return true;
        }

        PrintWriter out = outputFile != null ? new PrintWriter(new FileOutputStream(outputFile, append)) : new PrintWriter(System.out, true);
        BufferedReader in = inputFile != null ? new BufferedReader(new FileReader(inputFile)) : null;

        try {
            switch (command) {
                case "field":
                    ShellUtils.listFiles(shell, out);
                    return true;
                case "walk":
                    if (args.length > 0) ShellUtils.changeDirectory(args, shell);
                    else out.println("usage: walk <directory>");
                    return true;
                case "innings":
                    out.println(shell.getCurrentDirectory());
                    return true;
                case "duck":
                    if (args.length > 0) ShellUtils.deleteFile(args, shell, out);
                    else out.println("usage: duck <file>");
                    return true;
                case "score":
                    ShellUtils.showSystemStats(out);
                    return true;
                case "scorecard":
                    ShellUtils.showHistory(shell, out);
                    return true;
                case "lineup":
                    if (args.length > 0 && args[0].contains("=")) ShellUtils.defineVariable(args, shell, out);
                    else out.println("usage: lineup VAR=VALUE");
                    return true;
                case "jersey":
                    if (args.length > 0 && args[0].contains("=")) ShellUtils.createAlias(args, shell, out);
                    else out.println("usage: jersey alias=command");
                    return true;
                case "replay":
                    if (args.length > 0) ScriptRunner.runScript(args, shell);
                    else out.println("usage: replay <scriptfile>");
                    return true;
                case "umpire":
                    Themes.printHelp(out);
                    return true;
                case "retire":
                    System.exit(0);
                    return true;
                case "jobs":
                    JobManager.listJobs(out, append);
                    return true;
                case "fg":
                    if (args.length > 0) JobManager.resumeJob(Integer.parseInt(args[0]));
                    else out.println("usage: fg <jobid>");
                    return true;
                case "bg":
                    if (args.length > 0) JobManager.resumeJobInBackground(Integer.parseInt(args[0]));
                    else out.println("usage: bg <jobid>");
                    return true;
                case "kill":
                    if (args.length > 0) JobManager.killJob(Integer.parseInt(args[0]));
                    else out.println("usage: kill <jobid>");
                    return true;
                default:
                    return false;
            }
        } finally {
            // Close PrintWriter only if it was created for a file (output redirection)
            if (outputFile != null) out.close();
            if (in != null) in.close();
        }
    }
}