package models.ask;

import models.Difficulty;
import utility.Console;
import utility.Interrogator;

import java.util.NoSuchElementException;

/**
 * Отвечает за прием вводимых пользователем данных о сложности.
 */
public class AskDifficulty {
    /**
     * Принимает вводимые пользователем данные о сложности.
     * @param console Консоль для ввода данных.
     * @return Объект класса Difficulty.
     */
    public static Difficulty askDifficulty(Console console) throws AskBreak {
        try {
            Difficulty difficulty;
            if (Interrogator.fileMode()) {
                difficulty = Difficulty.valueOf(Interrogator.getUserScanner().nextLine().trim().toUpperCase());
            } else {
                while (true) {
                    console.print("Difficulty (" + Difficulty.names() + "): ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.equals("")) {
                        try {
                            difficulty = Difficulty.valueOf(line.toUpperCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            console.printError("Некорректное значение. Введите одно из: " + Difficulty.names());
                        }
                    } else {
                        console.printError("Difficulty не может быть пустым. Пожалуйста, введите значение.");
                    }
                }
            }
            return difficulty;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
