package src;

import java.io.*;
import java.util.*;

public class ShellUtils {
    public static void listFiles(ShellState shell, PrintWriter out) {
        File dir = new File(shell.getCurrentDirectory());
        String[] files = dir.list();
        out.println("files");
        if (files != null) Arrays.stream(files).forEach(f -> out.println(" - " + f));
    }

    public static void changeDirectory(String[] args, ShellState shell) {
        if (args.length == 0) {
            System.out.println("usage: walk <directory>");
            return;
        }
        File newDir = new File(shell.getCurrentDirectory(), args[0]);
        if (newDir.exists() && newDir.isDirectory()) {
            shell.setCurrentDirectory(newDir.getAbsolutePath());
            System.out.println("out of the park");
        } else {
            System.out.println("no such directory: " + args[0]);
        }
    }

    public static void deleteFile(String[] args, ShellState shell, PrintWriter out) {
        if (args.length == 0) {
            out.println("usage: duck <file>");
            return;
        }
        File file = new File(shell.getCurrentDirectory(), args[0]);
        if (file.exists() && file.delete()) {
            out.println(" " + args[0] + " ducked out!");
        } else {
            out.println("could not delete: " + args[0]);
        }
    }

    public static void showSystemStats(PrintWriter out) {
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        out.printf("Match Stats:\n- Total: %.2f MB\n- Free: %.2f MB\n",
                total / 1024.0 / 1024, free / 1024.0 / 1024);
    }

    public static void defineVariable(String[] args, ShellState shell, PrintWriter out) {
        if (args.length == 0 || !args[0].contains("=")) {
            out.println("usage: lineup VAR=VALUE");
            return;
        }
        String[] parts = args[0].split("=", 2);
        shell.setVariable(parts[0], parts[1]);
        out.println(" " + parts[0] + " lined up as " + parts[1]);
    }

    public static void createAlias(String[] args, ShellState shell, PrintWriter out) {
        if (args.length == 0 || !args[0].contains("=")) {
            out.println("usage: jersey alias=command");
            return;
        }
        String[] parts = args[0].split("=", 2);
        shell.setAlias(parts[0], parts[1].split("\\s+"));
        out.println("Jersey No" + parts[0] + " = " + parts[1]);
    }

    public static void runSystemCommand(String[] args, ShellState shell, String outputFile, String inputFile, boolean append, boolean background) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("usage: bowl <command>");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("$")) {
                args[i] = shell.getVariable(args[i].substring(1));
            }
        }
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.directory(new File(shell.getCurrentDirectory()));
        if (inputFile != null) pb.redirectInput(new File(inputFile));
        if (outputFile != null) {
            pb.redirectOutput(append ? ProcessBuilder.Redirect.appendTo(new File(outputFile)) : ProcessBuilder.Redirect.to(new File(outputFile)));
        } else {
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        }
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process = pb.start();
        if (background) {
            JobManager.addJob(String.join(" ", args), process);
        } else {
            process.waitFor();
        }
    }

    public static void showHistory(ShellState shell, PrintWriter out) {
        List<String> history = shell.getHistory();
        for (int i = 0; i < history.size(); i++) {
            out.println((i + 1) + ". " + history.get(i));
        }
    }

    public static String[] merge(String[] a, String[] b) {
        String[] result = new String[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}