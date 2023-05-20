package ninyo.questiontwo;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class WordsCounterJob implements Runnable {

    private static final String SPACE = " ";
    private String fileName;
    private Map<String, AtomicInteger> frequencyMap;
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public WordsCounterJob(String fileName, Map<String, AtomicInteger> frequencyMap) {
        this.fileName = fileName;
        this.frequencyMap = frequencyMap;
    }

    @Override
    public void run() {
        String line = "";
        try (InputStream in = getClass().getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            while ((line = br.readLine()) != null) {
                addToMap(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void addToMap(String line) {
        for (String word : line.split(SPACE)) {
            frequencyMap.computeIfAbsent(word, k -> new AtomicInteger()).incrementAndGet();
        }
    }

//    private void addToMap(String line) {
//        for (String word : line.split(SPACE)) {
//            frequencyMap.putIfAbsent(word, new AtomicInteger(0));
//            frequencyMap.get(word).incrementAndGet();
//        }
//    }
//
//    frequencyMap.putIfAbsent(word, new AtomicInteger(0));
//    = (as an atomic action)
//    AtomicInteger currentValue = frequencyMap.get(word);
//    if (currentValue == null) {
//        frequencyMap.put(word, new AtomicInteger(0));
//    }
//
//    a bad solution with locking:
//    private void addToMap(String line) {
//        for (String word : line.split(SPACE)) {
//            rwLock.readLock().lock();
//            try {
//                AtomicInteger currentValue = frequencyMap.get(word);
//                if (currentValue == null) {
//                    AtomicInteger newValue = new AtomicInteger(0);
//                    AtomicInteger oldValue = frequencyMap.putIfAbsent(word, newValue);
//                    currentValue = oldValue != null ? oldValue : newValue; //if in the meantime another thread has put some value with the kwr 'word', oldValue won't be null
//                }
//                currentValue.incrementAndGet();
//            } finally {
//                rwLock.readLock().unlock();
//            }
//        }
//    }

}
