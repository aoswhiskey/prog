package models.ask;

import models.LabWork;
import utility.Console;

import java.util.NoSuchElementException;

import static models.ask.AskAuthor.askAuthor;
import static models.ask.AskCoordinates.askCoordinates;
import static models.ask.AskDifficulty.askDifficulty;
/**
 * Отвечает за прием вводимых пользователем данных о лабораторной работе.
 */
public class AskLabWork {
    /**
     * Принимает вводимые пользователем данных о лабораторной работе.
     * @param console Консоль для ввода данных.
     * @return Объект класса LabWork.
     */
    public static LabWork askLabWork(Console console, int id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (name.isEmpty()) console.printError("Имя не может быть пустым. Повторите ввод.");
                else break;
            }
            var coordinates = askCoordinates(console);
            float minimalPoint;
            while (true) {
                console.print("minimalPoint (должно быть > 0): ");
                var line = console.readln().trim();
                if (!line.equals("")) {
                    try {
                        minimalPoint = Float.parseFloat(line);
                        if (minimalPoint > 0) break;
                        else console.printError("minimalPoint должен быть больше 0.");
                    } catch (NumberFormatException e) {
                        console.printError("Введите корректное число.");
                    }
                } else {
                    console.printError("Минимальная оценка не может быть пустой!");
                }
            }
            var difficulty = askDifficulty(console);
            var author = askAuthor(console);
            return new LabWork(id, name, coordinates, minimalPoint, difficulty, author);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        } catch (AskBreak e) {
            throw new RuntimeException(e);
        }
    }
}
