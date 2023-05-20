package ninyo.questiontwo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class WordsCounter {

    private Map<String, AtomicInteger> frequencyMap = new ConcurrentHashMap<>();

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        WordsCounter wc = new WordsCounter();

        // load text files in parallel
        wc.load("file1.txt", "file2.txt", "file3.txt");

        // display words statistics
        wc.displayStatus();
    }

    public void load(String... fileNames) {
        if (fileNames == null) {
            return;
        }
        List<Callable<Object>> wordsCounterJobs = new ArrayList<>();
        for (String fileName : fileNames) {
            wordsCounterJobs.add(Executors.callable(new WordsCounterJob(fileName, frequencyMap)));
        }
        try {
            executor.invokeAll(wordsCounterJobs);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        awaitTerminationAfterShutdown(executor);
    }

    public void displayStatus() {

        // TreeMap in order to display it sorted by the keys
        TreeMap<String, AtomicInteger> treeMap = new TreeMap<>();
        treeMap.putAll(frequencyMap);

        int total = 0;
        for (Map.Entry<String, AtomicInteger> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            total += entry.getValue().get();
        }
        System.out.print("** total: " + total);
    }

    private void awaitTerminationAfterShutdown(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
