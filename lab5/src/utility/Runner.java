package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import exceptions.ScriptRecursionException;
import managers.CommandManager;
import models.ask.AskBreak;

public class Runner {
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }

    private final Console console;
    private final CommandManager commandManager;
    private final Set<String> scriptStack = new HashSet<>();

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }
    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        var userScanner = Interrogator.getUserScanner();
        try {
            ExitCode commandStatus;
            String[] userCommand;

            do {
                console.prompt();
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != ExitCode.EXIT);

        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        } catch (NoSuchElementException exception) {
            console.printError("Завершение программы...");
            System.exit(1);
        } catch (AskBreak e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Режим для запуска скрипта.
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    public ExitCode scriptMode(String argument) {
        String[] userCommand;
        ExitCode commandStatus;
        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = Interrogator.getUserScanner();
            Interrogator.setUserScanner(scriptScanner);
            Interrogator.setFileMode();

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                console.println(console.getPrompt() + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    if (scriptStack.contains(userCommand[1])) {
                        throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == ExitCode.OK && scriptScanner.hasNextLine());

            Interrogator.setUserScanner(tmpScanner);
            Interrogator.setUserMode();

            if (commandStatus == ExitCode.ERROR && (!userCommand[0].equals("execute_script") || userCommand[1].isEmpty())) {
                console.println("Скрипт содержит некорректные данные!");
            }

            return commandStatus;

        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            console.printError("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            console.printError("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } catch (AskBreak e) {
            throw new RuntimeException(e);
        } finally {
            scriptStack.remove(argument);;
        }
        return ExitCode.ERROR;
    }
    /**
     * Исполняет команду.
     * @param userCommand Команда для запуска.
     * @return Код завершения.
     */
    private ExitCode launchCommand(String[] userCommand) throws AskBreak {
        if (userCommand[0].equals("")) return ExitCode.OK;
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) {
            console.printError("Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }

        switch (userCommand[0]) {
            case "exit" -> {
                if (!command.execute(userCommand)) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (!command.execute(userCommand)) return ExitCode.ERROR;
                else return scriptMode(userCommand[1]);
            }
            default -> { if (!command.execute(userCommand)) return ExitCode.ERROR; }
        }

        return ExitCode.OK;
    }
}