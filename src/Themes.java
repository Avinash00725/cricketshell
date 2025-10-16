package src;

import java.io.PrintWriter;

public class Themes {
    public static void printWelcomeBanner() {
        System.out.println("""
Welcome to CricketShell Stadium!
Get ready for a high-scoring CLI innings!
Type `umpire` for command list.
""");
    }

    public static void printHelp(PrintWriter out) {
        out.println("""
CricketShell Commands:
- field : List files
- walk <dir> : Change directory
- innings : Show current directory
- duck <file> : Delete file
- bowl <command> : Run system command
- replay <file> : Run script
- lineup VAR=VAL : Set variable
- jersey alias=command : Alias command
- scorecard : Command history
- score : Show system stats
- umpire : Help
- retire : Exit
- jobs : List background jobs
- fg <jobid> : Resume job in foreground
- bg <jobid> : Resume job in background
- kill <jobid> : Terminate job
""");
    }
}