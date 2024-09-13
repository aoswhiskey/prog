package managers;

import java.time.LocalDateTime;
import java.util.*;

import com.google.common.collect.Iterables;
import models.LabWork;
/**
 * Оперирует коллекцией.
 */
public class CollectionManager {
    private int currentId = 1;
    private final PriorityQueue<LabWork> collection = new PriorityQueue<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;

        init();
    }
    /**
     * @return Первый элемент коллекции.
     */
    public LabWork getFirst() {
        if (collection.isEmpty()) return null;
        return collection.peek();
    }
    /**
     * @return Последний элемент коллекции.
     */
    public LabWork getLast() {
        if (collection.isEmpty()) return null;
        return Iterables.getLast(collection);
    }
    /**
     * @param index index элемента.
     * @return Элемент коллекции по позиции в коллекции (индексу).
     */
    public LabWork getElementByIndex(int index) {
        LabWork[] array = collection.toArray(new LabWork[0]);

        if (index >= 0 && index < array.length) {
            return array[index];
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
    /**
     * @param id ID элемента.
     * @return Элемент коллекции по ID.
     */
    public LabWork byId(int id) {
        for (LabWork element : collection) {
            if (element.getId() == id) return element;
        }
        return null;
    }
    /**
     * @return Время последней инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }
    /**
     * @return Время последнего сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }
    /**
     * @return Коллекцию.
     */
    public PriorityQueue<LabWork> getCollection() {
        return collection;
    }
    /**
     * @return Имя типа коллекции.
     */
    public String collectionType() {
        return collection.getClass().getName();
    }
    /**
     * @return Размер коллекции.
     */
    public int collectionSize() {
        return collection.size();
    }
    /**
     * @return Свободный ID.
     */
    public int getFreeId() {
        while (byId(currentId) != null) {
            currentId++;
        }
        return currentId;
    }
    /**
     * Добавляет элемент в коллекцию.
     * @param labWork Элемент для добавления.
     */
    public void add(LabWork labWork) {
        collection.add(labWork);
        sort();
    }
    /**
     * Обновляет элемент в коллекции.
     * @param labWork Элемент для обновления.
     */
    public void update(LabWork labWork) {
        collection.remove(byId(labWork.getId()));
        collection.add(labWork);
        sort();
    }
    /**
     * Удаляет элемент из коллекции.
     * @param id ID элемента для удаления.
     */
    public void remove(int id) {
        var labWork = byId(id);
        if (labWork == null) return;
        collection.remove(labWork);
        sort();
    }
    /**
     * Сортирует коллекцию.
     */
    public void sort() {
        List<LabWork> sortedList = new ArrayList<>(collection);
        Collections.sort(sortedList);
        collection.clear();
        collection.addAll(sortedList);
    }
    /**
     * Инициализирует коллекцию, загружая ее из файла.
     */
    public void init() {
        collection.addAll(dumpManager.readCollection());
        lastInitTime = LocalDateTime.now();
        sort();
    }
    /**
     * Сохраняет коллекцию в файл.
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }
    /**
     * Очищает коллекцию.
     */
    public void clearCollection() {
        collection.clear();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";
        var last = getLast();

        StringBuilder info = new StringBuilder();
        for (LabWork labWork : collection) {
            info.append(labWork);
            if (labWork != last) {
                info.append("\n\n");
            }
        }
        return info.toString();
    }
}
