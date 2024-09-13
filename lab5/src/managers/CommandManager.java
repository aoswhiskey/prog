package managers;

import commands.Command;

import java.util.*;
/**
 * Управляет командами.
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    /**
     * Добавляет команду.
     * @param commandName Название команды.
     * @param command Команда.
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }
    /**
     * @return Словарь команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }
}
