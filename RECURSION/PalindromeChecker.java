package com.example.palindromesrecursion;

import java.util.Scanner;

public class PalindromeChecker {
            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a sentence: ");
                String sentence = scanner.nextLine();
                scanner.close();

                if (isPalindrome(sentence)) {
                    System.out.println("The sentence is a palindrome.");
                } else {
                    System.out.println("The sentence is not a palindrome.");
                }
            }

            public static boolean isPalindrome(String s) {
                // Remove white space and punctuation, and convert to lowercase
                s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

                // Base case
                if (s.length() <= 1) {
                    return true;
                }

                // Recursive case
                if (s.charAt(0) == s.charAt(s.length() - 1)) {
                    return isPalindrome(s.substring(1, s.length() - 1));
                } else {
                    return false;
                }
            }
        }