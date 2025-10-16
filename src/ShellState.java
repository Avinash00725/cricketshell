package src;

import java.util.*;

public class ShellState {
    private String currentDirectory = System.getProperty("user.dir");
    private final Map<String, String> variables = new HashMap<>();
    private final Map<String, String[]> aliases = new HashMap<>();
    private final List<String> history = new ArrayList<>();

    public String getCurrentDirectory() { return currentDirectory; }
    public void setCurrentDirectory(String dir) { currentDirectory = dir; }
    public void setVariable(String key, String value) { variables.put(key, value); }
    public String getVariable(String key) { return variables.getOrDefault(key, ""); }
    public void setAlias(String key, String[] value) { aliases.put(key, value); }
    public boolean hasAlias(String key) { return aliases.containsKey(key); }
    public String[] getAlias(String key) { return aliases.get(key); }
    public void addToHistory(String cmd) { history.add(cmd); }
    public List<String> getHistory() { return history; }
}