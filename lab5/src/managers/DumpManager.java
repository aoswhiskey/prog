package managers;

import com.google.gson.*;
import models.LabWork;
import utility.Console;
import utility.LabWorkValidator;
import utility.LocalDateAdapter;
import utility.LocalDateTimeAdapter;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DumpManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    public void writeCollection(PriorityQueue<LabWork> collection) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String json = gson.toJson(collection);
            fileWriter.write(json);
            console.println("Коллекция успешно сохранена в файл!");
        } catch (IOException exception) {
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }

    public PriorityQueue<LabWork> readCollection() {
        PriorityQueue<LabWork> validCollection = new PriorityQueue<>();

        if (fileName != null && !fileName.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

                for (JsonElement jsonElement : jsonArray) {
                    try {
                        LabWork labWork = gson.fromJson(jsonElement, LabWork.class);

                        if (LabWorkValidator.isValid(labWork)) {
                            validCollection.add(labWork);
                        } else {
                            console.printError("Невалидный элемент коллекции пропущен: " + labWork);
                        }
                    } catch (RuntimeException e) {
                        console.printError("Ошибка парсинга элемента: " + jsonElement);
                    }
                }

                console.println("Коллекция успешно загружена с игнорированием невалидных объектов!");

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (IOException exception) {
                console.printError("Непредвиденная ошибка!");
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }

        return validCollection;
    }
}
