package commands;

import managers.CollectionManager;
import utility.Console;
/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 */
public class RemoveFirst extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public RemoveFirst(Console console, CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
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
            collectionManager.remove(collectionManager.getFirst().getId());
            console.println("Первый элемент коллекции удален!");
        }

        return true;
    }
}
