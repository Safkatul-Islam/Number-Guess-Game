import java.util.Scanner;
import java.util.Random;

public class NumberGuess {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("I'm thinking of a number between 1 and 100. Can you guess it?");

        String answer;
        int secret;
        do {

            secret = random.nextInt(100) + 1;
            int guess = 0;
            int attempts = 0;

            while (guess != secret) {
                System.out.print("Enter your guess: ");
                guess = sc.nextInt();
                attempts++;

                if (guess > secret) {
                    System.out.println("Too High!");
                } else if (guess < secret) {
                    System.out.println("Too Low!");   
                }
            }

            System.out.println("\nCorrect! The number was " + secret);
            System.out.println("You guessed it in " + attempts + " tries!");

            System.out.print("\nDo you want to play the game again? (y/n): ");
            answer = sc.next();

        } while (!answer.equalsIgnoreCase("n"));

        System.out.println("\nAlright see you next time! :)");

        sc.close();
    }
}