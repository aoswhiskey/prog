package commands;

import managers.CollectionManager;
import models.LabWork;
import utility.Console;

import java.util.Objects;

/**
 * Команда 'max_by_minimal_point'. Выводит любой объект из коллекции, значение поля minimalPoint которого является максимальным.
 */
public class MaxByMinimalPoint extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public MaxByMinimalPoint(Console console, CollectionManager collectionManager) {
        super("max_by_minimal_point", "вывести любой объект из коллекции, значение поля minimalPoint которого является максимальным");
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

        LabWork labWork = null;
        for (var e : collectionManager.getCollection()) {
            if (labWork == null || e.getMinimalPoint() > labWork.getMinimalPoint()) labWork = e;
        }
        console.println(Objects.requireNonNullElse(labWork, "Лабораторных работ не обнаружено."));
        return true;
    }
}