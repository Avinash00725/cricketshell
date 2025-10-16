# CricketShell

CricketShell is a Unix-like command-line shell with a cricket theme, implemented in Java. It provides a robust CLI experience with support for command parsing, process management, I/O redirection, built-in commands, and job control, all wrapped in a fun cricket-inspired interface.

## Features

CricketShell enhances a basic shell with the following capabilities:

1. **Command Parsing**:
   - **Tokenization**: Handles quoted strings and spaces correctly for accurate command input.
   - **Argument Parsing**: Supports commands with multiple arguments.
   - **Alias Resolution**: Resolves aliases (via `jersey`) before executing commands.

2. **Process Management**:
   - **Fork/Exec Simulation**: Uses Java's `ProcessBuilder` to execute external commands (via `bowl`).
   - **Background Jobs**: Supports running commands in the background with the `&` suffix, tracked with unique job IDs.
   - **Job Tracking**: Maintains a list of background jobs with process information.

3. **I/O Redirection**:
   - **File Redirection**: Supports output redirection (`>`, `>>`) and input redirection (`<`) for both built-in and external commands.
   - **Built-in Command Support**: Commands like `field` and `scorecard` write to files when redirected.
   - **External Command Support**: Uses `ProcessBuilder` for seamless redirection.

4. **Built-in Commands**:
   - Implements a rich set of commands:
     - `field`: List files (like `ls`).
     - `walk <dir>`: Change directory (like `cd`).
     - `innings`: Print current directory (like `pwd`).
     - `duck <file>`: Delete a file.
     - `lineup VAR=VAL`: Set a shell variable.
     - `jersey alias=command`: Define an alias.
     - `bowl <cmd>`: Run a system command.
     - `scorecard`: View command history.
     - `replay <file>`: Execute a script file of commands.
     - `score`: View memory usage.
     - `umpire`: Show help.
     - `retire`: Exit the shell.
     - `jobs`: List background jobs.
     - `fg <jobid>`: Resume a job in the foreground.
     - `bg <jobid>`: Resume a job in the background.
     - `kill <jobid>`: Terminate a job.
   - Adds a custom `mood` command to change prompt styles (e.g., `aggressive`, `calm`, `exciting`, `default`).

5. **Job Control**:
   - **Job Listing**: Displays running and stopped jobs with `jobs`.
   - **Job Resumption**: Resumes jobs in the foreground (`fg`) or background (`bg`).
   - **Job Termination**: Terminates jobs with `kill` using `Process.destroy()`.
   - **Job Status**: Tracks whether jobs are running or stopped.
