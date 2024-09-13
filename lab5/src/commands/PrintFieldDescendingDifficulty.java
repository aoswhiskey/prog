package commands;

import managers.CollectionManager;
import models.Difficulty;
import models.LabWork;
import utility.Console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Команда 'print_field_descending_difficulty'. Выводит значения поля difficulty всех элементов в порядке убывания.
 */
public class PrintFieldDescendingDifficulty extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintFieldDescendingDifficulty(Console console, CollectionManager collectionManager) {
        super("print_field_descending_difficulty", "вывести значения поля difficulty всех элементов в порядке убывания");
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

        var labWorks = collectionManager.getCollection();

        if (labWorks.isEmpty()) {
            System.out.println("Коллекция пуста!");
            return false;
        }

        List<Difficulty> difficulties = new ArrayList<>();
        for (LabWork labWork : labWorks) {
            difficulties.add(labWork.getDifficulty());
        }

        difficulties.sort(Collections.reverseOrder());

        for (Difficulty difficulty : difficulties) {
            console.println(difficulty);
        }

        return true;
    }
}
