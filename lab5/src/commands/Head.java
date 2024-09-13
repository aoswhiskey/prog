package commands;

import managers.CollectionManager;
import utility.Console;
/**
 * Команда 'head'. Выводит первый элемент коллекции.
 */
public class Head extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Head(Console console, CollectionManager collectionManager) {
        super("head", "вывести первый элемент коллекции");
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

        if (collectionManager.getCollection().isEmpty()) {
            console.println("Коллекция пуста!");
        } else {
            console.println("Первый элемент коллекции:");
            console.println(collectionManager.getFirst());
        }

        return true;
    }
}