package commands;

import managers.CollectionManager;
import models.LabWork;
import models.Person;
import models.ask.AskAuthor;
import models.ask.AskBreak;
import utility.Console;

import java.util.Objects;

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
            LabWork labWork = null;
            for (var e : collectionManager.getCollection()) {
                if (author != null && (labWork == null || e.getAuthor().compareTo(author) < 0)) labWork = e;
            }
            console.println(Objects.requireNonNullElse(labWork, "Лабораторных работ не обнаружено."));
            return true;
        } catch (RuntimeException ignored) {} catch (AskBreak e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
