package src;

import java.io.*;
import java.util.Arrays;

public class ScriptRunner {
    public static void runScript(String[] args, ShellState shell) {
        if (args.length == 0) {
            System.out.println("usage: replay <scriptfile>");
            return;
        }
        File script = new File(shell.getCurrentDirectory(), args[0]);
        if (!script.exists()) {
            System.out.println("script not found: " + args[0]);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(script))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("> " + line);
                CommandHandler.executeCommand(line.trim(), shell);
            }
        } catch (IOException e) {
            System.out.println("error reading script: " + e.getMessage());
        }
    }
}
