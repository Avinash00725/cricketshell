package src;

import java.util.Scanner;

public class CricketShell {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShellState shell = new ShellState();

        Themes.printWelcomeBanner();

        while (true) {
            System.out.print("cricketshell:" + shell.getCurrentDirectory() + " > ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            if (input.equals("retire")) {
                System.out.println("Match ended. Good innings!");
                break;
            }

            CommandHandler.executeCommand(input, shell);
        }
    }
}
