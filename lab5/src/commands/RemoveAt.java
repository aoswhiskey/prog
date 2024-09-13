package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Console;
/**
 * Команда 'remove_at'. Удаляет элемент, находящийся в заданной позиции коллекции.
 */
public class RemoveAt extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveAt(Console console, CollectionManager collectionManager) {
        super("remove_at index", "удалить элемент, находящийся в заданной позиции коллекции (index)");
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
        int ind;
        try { ind = Integer.parseInt(arguments[1].trim()); } catch (NumberFormatException e) { console.println("index не распознан"); return false; }

        try {
            LabWork labWork = collectionManager.getElementByIndex(ind);
            collectionManager.remove(labWork.getId());
            console.println("LabWork под индексом " + ind + " успешно удалён!");
            return true;
        } catch (IndexOutOfBoundsException e) { console.printError("index за границами допустимых значений"); return false; }
    }
}