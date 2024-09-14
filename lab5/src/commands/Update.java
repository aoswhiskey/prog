package commands;

import exceptions.*;
import managers.CollectionManager;
import models.LabWork;
import models.ask.AskBreak;
import models.ask.AskLabWork;
import utility.Console;
/**
 * Команда 'update'. Обновляет значение элемента коллекции, id которого равен заданному.
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update id {element}", "обновить значение элемента коллекции, id которого равен заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) {
                console.println("Использование команды '" + getName() + "' подразумевает аргументов");
                return false;
            }
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            var id = Integer.parseInt(arguments[1]);
            var labWork = collectionManager.byId(id);
            if (labWork == null) throw new NotFoundException();

            console.println("* Введите данные обновленного продукта:");

            LabWork newLabWork = AskLabWork.askLabWork(console, id);
            collectionManager.update(newLabWork);

            console.println("Продукт успешно обновлен.");
            return true;

        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("LabWork с таким ID в коллекции нет!");
        } catch (AskBreak e) {
            throw new RuntimeException(e);
        } catch (RuntimeException ignored) {
            console.printError("Прерывание ввода данных");
        }
        return false;
    }
}
