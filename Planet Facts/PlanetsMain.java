// Nauman Choudhary
// Java 2
// Exercise: Planet Facts


package com.example.planetfacts;

import java.util.*;

/** 
 * Prints facts about planets - practice using Collections methods
 */
public class PlanetsMain {

    public static void main(String[] args) {

        // Source https://nssdc.gsfc.nasa.gov/planetary/factsheet/
        List<Planet> planets = new ArrayList<>(9);
        planets.add(new Planet("Mercury", 0.33, 58));
        planets.add(new Planet("Venus", 4.87, 108));
        planets.add(new Planet("Earth", 5.97, 150));
        planets.add(new Planet("Mars", .642, 228));
        planets.add(new Planet("Jupiter", 1898, 779));
        planets.add(new Planet("Saturn", 568, 1434));
        planets.add(new Planet("Uranus", 86.8, 2873));
        planets.add(new Planet("Neptune", 102, 4495));
        planets.add(new Planet("Pluto", 0.0146, 5906));

        System.out.println("Orignal order:");
        printPlanets(planets);

        // Randomize the order of the planets
        Collections.shuffle(planets);
        
        System.out.println("Planets in a random order:");
        printPlanets(planets);

        // Remove pluto
        planets.removeIf(planet -> planet.getName().equals("Pluto"));
                
        System.out.println("Removed Pluto:");
        printPlanets(planets);

        // Sort by distance from sun using Natural Ordering (Hint: Implement Planet.compareTo)

        System.out.println("Sorted by distance from sun:");
        printPlanets(planets);

        // Sort alphabetically (Hint: you need a Comparator class)
        class NameComparator implements Comparator<Planet> {
            @Override
            public int compare(Planet p1, Planet p2) {
                return p1.getName().compareTo(p2.getName());
            }
        }

        Collections.sort(planets, new NameComparator());
        
        System.out.println("Sorted alphabetically:");
        printPlanets(planets);

        // Sort by mass (Hint: you need a Comparator class)
        class MassComparator implements Comparator<Planet> {
            @Override
            public int compare(Planet p1, Planet p2) {
                return Double.compare(p1.getMass(), p2.getMass());
            }
        }

        Collections.sort(planets, new MassComparator());
        
        System.out.println("Sorted by mass:");
        printPlanets(planets);

        // Add each planet in your list to a Map (name => Planet)
        Map<String, Planet> planetMap = new HashMap<>();
        // Hint - use an enhanced for loop
        for (Planet planet : planets) {
            planetMap.put(planet.getName(), planet);
        }

        System.out.println("Earth:" + planetMap.get("Earth"));
        // Retrieve and print Mars
        Planet mars = planetMap.get("Mars");
        System.out.println("Mars: " + mars);

    }

    /**
     * Print the planet list in a table
     */
    private static void printPlanets(List<Planet> planets) {

        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s%-15s%-10s%n%n", "Planet", "Mass", "Distance");

        for (Planet planet : planets) {
            System.out.printf("%-10s%-15.3f%-10d%n", planet.getName(),
                    planet.getMass(), planet.getDistance());
        }

        System.out.println("------------------------------------------------------------\n\n");
    }

}
