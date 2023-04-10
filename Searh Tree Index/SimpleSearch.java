package com.example.searchtreeindex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleSearch {

    public static void main(String[] args) throws IOException {
            Scanner scan = new Scanner(System.in);

            System.out.println("Please provide a file name as an argument.");
            String fileName = scan.nextLine();


        SimpleMap<String, List<String>> map = new CustomBinaryTreeSimpleMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;
        int lineNumber = 1;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("\\W+");
            for (String word : words) {
                String value = lineNumber + ": " + line.trim();
                List<String> lines = map.get(word.toLowerCase());
                if (lines == null) {
                    lines = new ArrayList<>();
                    map.put(word.toLowerCase(), lines);
                }
                lines.add(value);
            }
            lineNumber++;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nPlease enter a search term (blank to exit): ");
            String searchTerm = scanner.nextLine().toLowerCase();
            if (searchTerm.isEmpty()) {
                break;
            }

            List<String> results = map.get(searchTerm);
            if (results != null) {
                for (String result : results) {
                    System.out.println(result);
                }
            } else {
                System.out.println("No results found.");
            }
        }
    }
}
