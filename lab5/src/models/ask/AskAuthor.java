package models.ask;

import models.Person;
import utility.Console;
import utility.Interrogator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import static models.ask.AskLocation.askLocation;
/**
 * Отвечает за прием вводимых пользователем данных автора.
 */
public class AskAuthor {
    /**
     * Принимает вводимые пользователем данные автора.
     * @param console Консоль для ввода данных.
     * @return Объект класса Person.
     */
    public static Person askAuthor(Console console) throws AskBreak {
        try {
            String name;
            if (Interrogator.fileMode()) {
                name = Interrogator.getUserScanner().nextLine().trim();
            } else {
                while (true) {
                    console.print("author.name: ");
                    name = console.readln().trim();
                    if (name.equals("exit")) throw new AskBreak();
                    if (!name.isEmpty()) break;
                    else console.printError("Имя автора не может быть пустым.");
                }
            }

            LocalDateTime birthday;
            if (Interrogator.fileMode()) {
                birthday = LocalDateTime.parse(Interrogator.getUserScanner().nextLine().trim(), DateTimeFormatter.ISO_DATE_TIME);
            } else {
                while (true) {
                    console.print("author.birthday (Пример: " +
                            LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " или 2023-03-11): ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.isEmpty()) {
                        try {
                            birthday = LocalDateTime.parse(line, DateTimeFormatter.ISO_DATE_TIME);
                            break;
                        } catch (DateTimeParseException e) {
                            try {
                                birthday = LocalDateTime.parse(line + "T00:00:00", DateTimeFormatter.ISO_DATE_TIME);
                                break;
                            } catch (DateTimeParseException ex) {
                                console.printError("Неверный формат. Введите дату в формате ISO (например, 2023-03-11).");
                            }
                        }
                    } else {
                        console.printError("Дата рождения не может быть пустой!");
                    }
                }
            }

            Float height;
            if (Interrogator.fileMode()) {
                height = Float.parseFloat(Interrogator.getUserScanner().nextLine().trim());
            } else {
                while (true) {
                    console.print("author.height (больше 0): ");
                    var line = console.readln().trim();

                    if (line.equals("exit")) throw new AskBreak();

                    if (line.equals("")) {
                        height = null;
                        break;
                    } else {
                        try {
                            height = Float.parseFloat(line);
                            if (height > 0) {
                                break;
                            } else {
                                console.printError("Рост должен быть больше 0.");
                            }
                        } catch (NumberFormatException e) {
                            console.printError("Введите корректное число.");
                        }
                    }
                }
            }

            var location = askLocation(console);
            return new Person(name, birthday, height, location);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        } catch (AskBreak e) {
        throw new RuntimeException(e);
        }
    }
}
