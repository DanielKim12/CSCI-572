// guk il kim 

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class Unigram {
    private static final Pattern pattern = Pattern.compile("[^a-zA-Z]+");

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: Unigram <input directory path> <output file path>");
            System.exit(1);
        }

        String inputDirPath = args[0];
        String outputFilePath = args[1];

        // Using TreeMap for alphabetical order
        TreeMap<String, HashMap<String, Integer>> unigramIndex = new TreeMap<>();

        File inputDir = new File(inputDirPath);
        if (!inputDir.isDirectory()) {
            System.err.println("The specified input path is not a directory.");
            System.exit(1);
        }

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
                    StringTokenizer itr = new StringTokenizer(content);

                    while (itr.hasMoreTokens()) {
                        String word = itr.nextToken();
                        unigramIndex.putIfAbsent(word, new HashMap<>());
                        unigramIndex.get(word).put(docID, unigramIndex.get(word).getOrDefault(docID, 0) + 1);
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred while reading the file: " + e.getMessage());
                }
            }
        }

        // Writing the unigram index in the desired format
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            for (Map.Entry<String, HashMap<String, Integer>> entry : unigramIndex.entrySet()) {
                String word = entry.getKey();
                HashMap<String, Integer> docMap = entry.getValue();

                StringBuilder sb = new StringBuilder();
                sb.append(word).append("\t"); // Append the word with a tab

                for (Map.Entry<String, Integer> docEntry : docMap.entrySet()) {
                    String docID = docEntry.getKey();
                    Integer count = docEntry.getValue();
                    sb.append(docID).append(":").append(count).append(" ");
                }

                writer.write(sb.toString().trim() + "\n"); // Write the line and trim trailing spaces
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}