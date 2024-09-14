package commands;

import managers.CollectionManager;
import models.LabWork;
import models.Person;
import models.ask.AskAuthor;
import models.ask.AskBreak;
import utility.Console;

import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Команда 'filter_less_than_author'. Выводит элементы, значение поля author которых меньше заданного.
 */
public class FilterLessThanAuthor extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterLessThanAuthor(Console console, CollectionManager collectionManager) {
        super("filter_less_than_author author", "вывести элементы, значение поля author которых меньше заданного");
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
            Person author = AskAuthor.askAuthor(console);
            PriorityQueue<LabWork> labWorks = new PriorityQueue<>();
            for (var e : collectionManager.getCollection()) {
                if (e.getAuthor().compareTo(author) < 0) labWorks.add(e);
            }
            if (labWorks.isEmpty()) {
                console.println("Лабораторных работ не обнаружено.");
            }
            else {
                for (LabWork e : labWorks) {
                    console.println("\n" + e);
                }
            }
            return true;
        } catch (AskBreak e) {
            throw new RuntimeException(e);
        } catch (RuntimeException ignored) {
            console.printError("Прерывание ввода данных");
        }
        return false;
    }
}
