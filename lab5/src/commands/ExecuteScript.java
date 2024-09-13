package commands;

import utility.Console;
/**
 * Команда 'execute_script'. Выполняет скрипт из файла.
 */
public class ExecuteScript extends Command {
    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script file_name", "исполнить скрипт из указанного файла");
        this.console = console;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Использование команды '" + getName() + "' подразумевает аргументов");
            return false;
        }
        console.println("Выполнение скрипта '" + arguments[1] + "'...");
        return true;
    }
}