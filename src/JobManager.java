package src;

import java.io.*;
import java.util.*;

public class JobManager {
    private static List<Job> jobs = new ArrayList<>();
    private static int jobIdCounter = 1;

    static class Job {
        int jobId;
        String command;
        Process process;
        boolean isRunning;

        Job(int jobId, String command, Process process) {
            this.jobId = jobId;
            this.command = command;
            this.process = process;
            this.isRunning = true;
        }
    }

    public static void initialize() {
        // No initialization needed for now
    }

    public static void addJob(String command, Process process) {
        Job job = new Job(jobIdCounter++, command, process);
        jobs.add(job);
        System.out.println("[" + job.jobId + "] " + process.pid() + " " + command);
    }

    public static void listJobs(PrintWriter out, boolean append) {
        for (Job job : jobs) {
            out.println("[" + job.jobId + "] " + (job.isRunning ? "Running" : "Stopped") + " " + job.command);
        }
    }

    public static void resumeJob(int jobId) throws IOException, InterruptedException {
        Job job = jobs.stream().filter(j -> j.jobId == jobId).findFirst().orElse(null);
        if (job == null) {
            System.out.println("fg: no such job: " + jobId);
            return;
        }
        if (job.isRunning) {
            job.process.waitFor();
        } else {
            System.out.println("fg: job is not running: " + jobId);
        }
    }

    public static void resumeJobInBackground(int jobId) {
        Job job = jobs.stream().filter(j -> j.jobId == jobId).findFirst().orElse(null);
        if (job == null) {
            System.out.println("bg: no such job: " + jobId);
            return;
        }
        job.isRunning = true;
        System.out.println("[" + job.jobId + "] " + job.command + " &");
    }

    public static void killJob(int jobId) {
        Job job = jobs.stream().filter(j -> j.jobId == jobId).findFirst().orElse(null);
        if (job == null) {
            System.out.println("kill: no such job: " + jobId);
            return;
        }
        job.process.destroy();
        job.isRunning = false;
        System.out.println("[" + job.jobId + "] Terminated: " + job.command);
    }

    public static String getJobStatusPrompt() {
        long runningJobs = jobs.stream().filter(j -> j.isRunning).count();
        return runningJobs > 0 ? " (" + runningJobs + " jobs)" : "";
    }
}