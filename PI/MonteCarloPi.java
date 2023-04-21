package com.example.estimatingpi;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Driver for Monte Carlo Pi estimation
 */
public class MonteCarloPi {

    public static final int NUM_THREADS = 8;
    public static final int NUM_ITERATIONS = 100_000_000;

    public static void main(String[] args) {

        System.out.printf("Available Processors: %s\n", Runtime.getRuntime().availableProcessors());

        int iterationsPerThread = NUM_ITERATIONS / NUM_THREADS;
        List<PiCalculatorThread> threads = new ArrayList<>(NUM_THREADS);

        Instant startTime = Instant.now();
        for (int i = 0; i < NUM_THREADS; i++) {
            PiCalculatorThread thread = new PiCalculatorThread(iterationsPerThread);
            threads.add(thread);
            thread.start();
        }

        try {
            for (PiCalculatorThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        int circlePoints = 0;
        int squarePoints = 0;

        for (PiCalculatorThread thread : threads) {
            circlePoints += thread.getCirclePoints();
            squarePoints += thread.getSquarePoints();
        }

        double piEstimate = 4.0 * ((double)circlePoints / (double)squarePoints);

        Instant finishTime = Instant.now();

        long timeInMilliseconds = Duration.between(startTime, finishTime).toMillis();
        System.out.printf("Approximation of Pi using %d iterations and %d thread(s) completed in %d milliseconds: %f\n",
                NUM_ITERATIONS, NUM_THREADS, timeInMilliseconds, piEstimate);

    }

    /**
     * Thread that uses a Monte Carlo method to estimate Pi
     */
    public static class PiCalculatorThread extends Thread {

        private final int iterations;
        private int circlePoints;
        private int squarePoints;

        public PiCalculatorThread(int iterations) {
            this.iterations = iterations;
        }

        public int getCirclePoints() {
            return circlePoints;
        }

        public int getSquarePoints() {
            return squarePoints;
        }

        @Override
        public void run() {
            final Random random = new Random();

            for (int i = 0; i < iterations; i++) {
                double x = random.nextDouble();
                double y = random.nextDouble();
                double d = x * x + y * y;

                if (d <= 1) {
                    circlePoints++;
                }
                squarePoints++;
            }
        }
    }
}
