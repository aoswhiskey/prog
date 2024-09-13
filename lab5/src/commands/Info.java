package commands;

import managers.CollectionManager;
import utility.Console;

import java.time.LocalDateTime;
/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
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

        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString().substring(0, 8);

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString().substring(0, 8);

        console.println("Сведения о коллекции:");
        console.println(" Тип: " + collectionManager.collectionType());
        console.println(" Количество элементов: " + collectionManager.collectionSize());
        console.println(" Дата последнего сохранения: " + lastSaveTimeString);
        console.println(" Дата последней инициализации: " + lastInitTimeString);
        return true;
    }
}
