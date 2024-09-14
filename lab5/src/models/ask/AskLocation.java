package models.ask;

import models.Location;
import utility.Console;
import utility.Interrogator;

import java.util.NoSuchElementException;
/**
 * Отвечает за прием вводимых пользователем данных о локации.
 */
public class AskLocation {
    /**
     * Принимает вводимые пользователем данных о локации.
     * @param console Консоль для ввода данных.
     * @return Объект класса Location.
     */
    public static Location askLocation(Console console) throws AskBreak {
        try {
            Double x;
            if (Interrogator.fileMode()) {
                x = Double.parseDouble(Interrogator.getUserScanner().nextLine().trim());
            } else {
                while (true) {
                    console.print("location.coordinates.x: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.equals("")) {
                        try {
                            x = Double.parseDouble(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.printError("Введите корректное число.");
                        }
                    }else {
                        console.printError("Координата x не может быть пустой!");
                    }
                }
            }
            Long y;
            if (Interrogator.fileMode()) {
                y = Long.parseLong(Interrogator.getUserScanner().nextLine().trim());
            } else {
                while (true) {
                    console.print("location.coordinates.y: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.equals("")) {
                        try {
                            y = Long.parseLong(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.printError("Введите корректное число.");
                        }
                    } else {
                        console.printError("Координата y не может быть пустой!");
                    }
                }
            }
            Integer z;
            if (Interrogator.fileMode()) {
                z = Integer.parseInt(Interrogator.getUserScanner().nextLine().trim());
            } else {
                while (true) {
                    console.print("location.coordinates.z: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    if (!line.equals("")) {
                        try {
                            z = Integer.parseInt(line);
                            break;
                        } catch (NumberFormatException e) {
                            console.printError("Введите корректное целое число.");
                        }
                    } else {
                        console.printError("Координата z не может быть пустой!");
                    }
                }
            }
            String name;
            if (Interrogator.fileMode()) {
                name = Interrogator.getUserScanner().nextLine().trim();
            } else {
                console.print("location.name: ");
                name = console.readln().trim();
            }
            if (name.equals("exit")) throw new AskBreak();
            return new Location(x, y, z, name);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
