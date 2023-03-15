package com.example.imdbmoviestats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Reads movie metadata from a file and searches for specific information
 */
public class Main {
    
    public static void main(String[] args) {
        
        // Parse Movies from a file
        Collection<Movie> movies = parseMovies();
        
        // Print the ten highest rated movies        
        List<Movie> topTenMovies = movies.stream()
                .sorted((m1, m2) -> // Sort by IMDB Score
                        Double.compare(m2.getImdbScore(), m1.getImdbScore())) // Reversed for ascending roder
                .limit(10) // Get the first 10
                .collect(Collectors.toList()); // Collect as a list
        printMovies("Top ten highest rated movies:", topTenMovies);
        
        // Print the eight highest rated action movies        
        List<Movie> topEightActionMovies = movies.stream()
                .filter(m -> m.getGenres().contains("Action"))
                .sorted((m1, m2) -> Double.compare(m2.getImdbScore(), m1.getImdbScore()))
                .limit(8)
                .collect(Collectors.toList());
        printMovies("Top eight highest rated action movies:", topEightActionMovies);        
        
        // Print the five longest Fantasy movies      
        List<Movie> fiveLongestFantasyMovies = movies.stream()
                        .filter(m -> m.getGenres().contains("Action"))
                        .sorted((m1, m2) -> Double.compare(m2.getImdbScore(), m1.getImdbScore()))
                        .limit(8)
                        .collect(Collectors.toList());
        printMovies("Five longest fantasy movies:", fiveLongestFantasyMovies);
        
        // Print the average duration of a Family movie
        double averageFamilyMovieDuration = movies.stream()
                .filter(m -> m.getGenres().contains("Family"))
                .mapToDouble(Movie::getDuration)
                .average()
                .orElse(0);
        System.out.println("Average length of a family movie: " + averageFamilyMovieDuration);

        // Print the average IMDB score of a Sci-Fi movie
        double averageSciFiScore = parseMovies().stream()
                .filter(movie -> movie.getGenres().contains("Sci-Fi"))
                .mapToDouble(Movie::getImdbScore)
                .average()
                .orElse(-1);
        System.out.println("Average IMDB Score for a Sci-Fi movie: " + averageSciFiScore);

        // Print the title of the film rated PG-13 with the highest IMDB score
        String highestRatedPG13Movie = movies.stream()
                .filter(movie -> movie.getRating().equals("PG-13")) // Filter for only PG-13 movies
                .max(Comparator.comparing(Movie::getImdbScore)) // Get the movie with highest IMDB score
                .map(Movie::getTitle) // Extract the title of the movie
                .orElse(""); // If no movie was found, return an empty string
        System.out.println("Best PG-13 movie: " + highestRatedPG13Movie);
        
        
    }
    
    /**
     * Parse a collection of movies from a CSV file
     */
    private static Collection<Movie> parseMovies() {
        // Create set to hold movies
        Set<Movie> movies = new HashSet<>();        
        
        // Craete Stream from file
        try ( Stream<String> stream = Files.lines(Paths.get("movie_metadata.csv"))) {

            // Parse a movie from each line
            stream
                    .skip(1) // Skip header
                    .forEach(line -> {
                        Movie movie = parseMovie(line); // parse movie
                        if (movie != null) {
                            movies.add(movie); // add to collection
                        }
                    });

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return movies;
    }
    
    
    /** Parse a line of movie metadata
     * 
     * @param line The input to parse
     * @return A Movie, or null if the line is invalid
     */
    private static Movie parseMovie(String line) {
        
        List<String> tokens = Arrays.asList(line.split(","));
        if (tokens.size() != 7) {
            return null;
        }
        
        // Parse director name
        String directorName = tokens.get(0);
        
        // Parse duration as int
        int duration = -1;
        try {
            if (!tokens.get(1).isEmpty()){
                duration = Integer.parseInt(tokens.get(1));
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Unable to parse duration: " + tokens.get(1));           
        }
        
        // Parse genres
        Set<String> genres = Set.of(tokens.get(2).split("\\|"));
        
        // Parse title
        String title = tokens.get(3);
        
        // Parse rating
        String rating = tokens.get(4);
        
        // Parse year as int
        int year = -1;
        try {
            if (!tokens.get(5).isEmpty()){
                year = Integer.parseInt(tokens.get(5));
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Unable to parse year: " + tokens.get(5));           
        }
                
        // Parse imdbScore as double
        double imdbScore = -1.0;
        try {
            if (!tokens.get(6).isEmpty()){
                imdbScore = Double.parseDouble(tokens.get(6));
            }
        }
        catch (NumberFormatException e) {
            System.err.println("Unable to parse IMDB Score: " + tokens.get(6));           
        }
        
        return new Movie(title, directorName, duration, genres, rating, year, imdbScore);
    }
    
    /**
     * Pretty-print a list of movies
     */
    private static void printMovies(String label, Collection<Movie> movies) {
        System.out.println(label + ": ");
        
        for (Movie movie: movies) {
            System.out.printf("\t%-40s%-6d%-10s%-5d%-5.1f%-10s%-30s%n",
                    movie.getTitle(),
                    movie.getYear(),
                    movie.getRating(),
                    movie.getDuration(),
                    movie.getImdbScore(),
                    movie.getDirectorName(),
                    movie.getGenres());
        }
        
        System.out.println("");
    }

}
