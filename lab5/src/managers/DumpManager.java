package managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import models.LabWork;
import utility.Console;
import utility.LocalDateAdapter;
import utility.LocalDateTimeAdapter;
/**
 * Использует файл для сохранения и загрузки коллекции.
 */
public class DumpManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())  // Адаптер для LocalDate
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())  // Адаптер для LocalDateTime
            .create();

    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }
    /**
     * Записывает коллекцию в файл.
     * @param collection Коллекция.
     */
    public void writeCollection(PriorityQueue<LabWork> collection) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            String json = gson.toJson(collection);
            fileWriter.write(json);
            console.println("Коллекция успешно сохранена в файл!");
        } catch (IOException exception) {
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }
    /**
     * Считывает коллекцию из файла.
     * @return Считанная коллекция.
     */
    public PriorityQueue<LabWork> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fileName))) {
                var collectionType = new TypeToken<PriorityQueue<LabWork>>() {}.getType();
                var reader = new BufferedReader(inputStreamReader);

                var jsonString = new StringBuilder();

                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.length() == 0) {
                    jsonString = new StringBuilder("[]");
                }

                PriorityQueue<LabWork> collection = gson.fromJson(jsonString.toString(), collectionType);

                console.println("Коллекция успешна загружена!");
                return collection;

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                console.printError("Ошибка при разборе JSON: " + exception.getMessage());
            } catch (IllegalStateException | IOException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new PriorityQueue<>();
    }
}
