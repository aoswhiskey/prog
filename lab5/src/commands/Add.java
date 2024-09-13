package commands;

import managers.CollectionManager;
import models.LabWork;
import models.ask.AskBreak;
import models.ask.AskLabWork;
import utility.Console;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */

public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование команды '" + getName() + "' не подразумевает аргументов");
            return false;
        }
        try {
            console.println("* Создание нового продукта:");
            LabWork labWork = AskLabWork.askLabWork(console, collectionManager.getFreeId());
            collectionManager.add(labWork);
            console.println("Продукт успешно добавлен!");
            return true;
        } catch (AskBreak e) {
            throw new RuntimeException(e);
        } catch (RuntimeException exception) {
            console.printError("Прерывание ввода данных");
        }
        return false;
    }
}