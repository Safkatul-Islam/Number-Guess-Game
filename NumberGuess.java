import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGuess {
    public static void main(String[] args) {
        final int MIN = 1, MAX = 100;
        Scanner sc = new Scanner(System.in);

        System.out.println("I'm thinking of a number between " + MIN + " and " + MAX + ". Can you guess it?");

        String answer;
        do {
            int secret = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            int guess = 0;
            int attempts = 0;

            while (guess != secret) {
                System.out.print("Enter your guess: ");

                // Validate numeric input
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid number.");
                    sc.next();
                    System.out.print("Enter your guess: ");
                }

                guess = sc.nextInt();
                sc.nextLine(); // Clear buffer

                // Validate range BEFORE incrementing attempts
                if (guess < MIN || guess > MAX) {
                    System.out.println("Out of range! Guess between " + MIN + " and " + MAX + ".");
                    continue; // Don't count this as an attempt
                }

                attempts++;

                if (guess > secret) {
                    System.out.println("Too high!");
                } else if (guess < secret) {
                    System.out.println("Too low!");   
                }
            }

            System.out.println("\nCorrect! The number was " + secret + ".");
            System.out.println("You guessed it in " + attempts + " " + (attempts == 1 ? "attempt" : "attempts") + "!");

            // Show performance hint
            int optimalAttempts = (int) Math.ceil(Math.log(MAX - MIN + 1) / Math.log(2));
            if (attempts <= optimalAttempts) {
                System.out.println("Excellent! You're a Cool Guesser");
            }

            // Validate replay input
            while (true) {
                System.out.print("\nDo you want to Play again? (y/n): ");
                answer = sc.nextLine().trim().toLowerCase();
                if (answer.equals("y") || answer.equals("n")) {
                    break;
                }
                System.out.println("Please enter 'y' or 'n'.");
            }

        } while (answer.equals("y"));

        System.out.println("\nThanks for playing! See you next time! :)");
        sc.close();
    }
}