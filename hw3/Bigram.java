// guk il kim 

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Bigram {
    // selected bigrams to output
    private static final String[] SELECTED_BIGRAMS = {
            "computer science",
            "information retrieval",
            "power politics",
            "los angeles",
            "bruce willis"
    };

    // user input requirements
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: Bigram <input directory path> <output file path>");
            System.exit(1);
        }
        //requires two conditions
        String inputDirPath = args[0];
        String outputFilePath = args[1];

        // Use TreeMap for alphabetical ordering
        TreeMap<String, HashMap<String, Integer>> bigramIndex = new TreeMap<>();

        File inputDir = new File(inputDirPath);
        if (!inputDir.isDirectory()) {
            System.err.println("The specified input path is not a directory.");
            System.exit(1);
        }

        // Process each file in the input directory
        for (File file : inputDir.listFiles()) {
            if (file.isFile()) {
                try (FileReader fr = new FileReader(file)) {
                    StringBuilder contentBuilder = new StringBuilder();
                    int character;
                    while ((character = fr.read()) != -1) {
                        contentBuilder.append((char) character);
                    }

                    String docID = file.getName().replace(".txt", "");
                    String content = contentBuilder.toString().toLowerCase().replaceAll("[^a-zA-Z ]", " ");
                    String[] words = content.split("\\s+");

                    // Populate the bigram index
                    for (int i = 0; i < words.length - 1; i++) {
                        if (!words[i].isEmpty() && !words[i + 1].isEmpty()) {
                            String bigram = words[i] + " " + words[i + 1];
                            if (isSelectedBigram(bigram)) {
                                bigramIndex.putIfAbsent(bigram, new HashMap<>());
                                bigramIndex.get(bigram).put(docID, bigramIndex.get(bigram).getOrDefault(docID, 0) + 1);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while reading the file: " + e.getMessage());
                }
            }
        }

        // Write the bigram index to the output file in alphabetical order
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            for (Map.Entry<String, HashMap<String, Integer>> entry : bigramIndex.entrySet()) {
                String bigram = entry.getKey();
                HashMap<String, Integer> docMap = entry.getValue();
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, Integer> docEntry : docMap.entrySet()) {
                    String docID = docEntry.getKey();
                    Integer count = docEntry.getValue();
                    sb.append(docID).append(":").append(count).append(" ");
                }
                writer.write(bigram + "\t" + sb.toString().trim() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    // Helper function to check if the bigram is one of the selected bigrams
    private static boolean isSelectedBigram(String bigram) {
        for (String selectedBigram : SELECTED_BIGRAMS) {
            if (selectedBigram.equals(bigram)) {
                return true;
            }
        }
        return false;
    }
}