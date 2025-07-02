package src;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShellUtils {
    public static void listFiles(ShellState shell) {
        File dir = new File(shell.getCurrentDirectory());
        String[] files = dir.list();
        System.out.println("files");
        if (files != null) Arrays.stream(files).forEach(f -> System.out.println(" - " + f));
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

    public static void deleteFile(String[] args, ShellState shell) {
        if (args.length == 0) {
            System.out.println("usage: duck <file>");
            return;
        }
        File file = new File(shell.getCurrentDirectory(), args[0]);
        if (file.exists() && file.delete()) {
            System.out.println(" " + args[0] + " ducked out!");
        } else {
            System.out.println("could not delete: " + args[0]);
        }
    }

    public static void showSystemStats() {
        long total = Runtime.getRuntime().totalMemory();
        long free = Runtime.getRuntime().freeMemory();
        System.out.printf("Match Stats:\n- Total: %.2f MB\n- Free: %.2f MB\n",
                total / 1024.0 / 1024, free / 1024.0 / 1024);
    }

    public static void defineVariable(String[] args, ShellState shell) {
        if (args.length == 0 || !args[0].contains("=")) {
            System.out.println("usage: lineup VAR=VALUE");
            return;
        }
        String[] parts = args[0].split("=", 2);
        shell.setVariable(parts[0], parts[1]);
        System.out.println(" " + parts[0] + " lined up as " + parts[1]);
    }

    public static void createAlias(String[] args, ShellState shell) {
        if (args.length == 0 || !args[0].contains("=")) {
            System.out.println("usage: jersey alias=command");
            return;
        }
        String[] parts = args[0].split("=", 2);
        shell.setAlias(parts[0], parts[1].split("\\s+"));
        System.out.println("Jersey No" + parts[0] + " = " + parts[1]);
    }

    public static void runSystemCommand(String[] args, ShellState shell) {
        if (args.length == 0) {
            System.out.println("usage: bowl <command>");
            return;
        }

        if (shell.hasAlias(args[0])) {
            String[] aliasCmd = shell.getAlias(args[0]);
            args = merge(aliasCmd, Arrays.copyOfRange(args, 1, args.length));
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("$")) {
                args[i] = shell.getVariable(args[i].substring(1));
            }
        }

        try {
            ProcessBuilder pb = new ProcessBuilder(args);
            pb.directory(new File(shell.getCurrentDirectory()));
            pb.inheritIO();
            pb.start().waitFor();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void showHistory(ShellState shell) {
        List<String> history = shell.getHistory();
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }

    private static String[] merge(String[] a, String[] b) {
        String[] result = new String[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }
}
