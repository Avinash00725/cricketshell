package src;

public class Themes {
    public static void printWelcomeBanner() {
        System.out.println("""
Welcome to CricketShell Stadium!
Get ready for a high-scoring CLI innings!
Type `umpire` for command list.
""");
    }

    public static void printHelp() {
        System.out.println("""
CricketShell Commands:
- field            : List files
- walk <dir>       : Change directory
- innings          : Show current directory
- duck <file>      : Delete file
- bowl <command>   : Run system command
- replay <file>    : Run script
- lineup VAR=VAL   : Set variable
- jersey a=cmd     : Alias command
- scorecard        : Command history
- umpire           : Help
- retire           : Exit
""");
    }
}
