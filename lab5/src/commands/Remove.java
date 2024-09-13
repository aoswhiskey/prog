package commands;

import exceptions.CollectionIsEmptyException;
import exceptions.NotFoundException;
import managers.CollectionManager;
import models.LabWork;
import utility.Console;
/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по его id.
 */
public class Remove extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public Remove(Console console, CollectionManager collectionManager) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.console = console;
        this.collectionManager = collectionManager;
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
        try {
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            var id = Integer.parseInt(arguments[1]);
            LabWork labWork = collectionManager.byId(id);
            if (labWork == null) throw new NotFoundException();

            collectionManager.remove(id);
            console.println("Продукт успешно удален.");
            return true;

        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("LabWork с таким ID в коллекции нет!");
        }
        return false;
    }
}
