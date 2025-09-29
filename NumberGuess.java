import java.util.Scanner;
// import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;;

public class NumberGuess {
    public static void main(String[] args) {
        final int MIN = 1, MAX = 100;
        Scanner sc = new Scanner(System.in);
        // Random random = new Random();

        System.out.println("I'm thinking of a number between 1 and 100. Can you guess it?");

        String answer;
        int secret;
        do {

            secret = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            int guess = 0;
            int attempts = 0;

            while (guess != secret) {
                System.out.print("Enter your guess: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid number.");
                    sc.next();
                    System.out.print("Enter your Guess: ");
                }
                guess = sc.nextInt();
                attempts++;

                if (guess < MIN || guess > MAX) {
                    System.out.println("Out of range! Guess between " + MIN + " and " + MAX + ".");
                    continue;
                }

                if (guess > secret) {
                    System.out.println("Too High!");
                } else if (guess < secret) {
                    System.out.println("Too Low!");   
                }
            }

            System.out.println("\nCorrect! The number was " + secret);
            System.out.println("You guessed it in " + attempts + " " + (attempts == 1 ? "try" : "tries") + "!");

            System.out.print("\nDo you want to play the game again? (y/n): ");
            answer = sc.next();

        } while (!answer.equalsIgnoreCase("n"));

        System.out.println("\nAlright see you next time! :)");

        sc.close();
    }
}