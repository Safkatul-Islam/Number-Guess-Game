import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class NumberGuess {
    private static int gamesPlayed = 0;
    private static int totalAttempts = 0;
    private static int bestScore = Integer.MAX_VALUE;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        printWelcome();
        
        String answer;
        do {
            int difficulty = selectDifficulty(sc);
            int[] range = getDifficultyRange(difficulty);
            int maxHints = getMaxHints(difficulty);
            
            playGame(sc, range[0], range[1], maxHints);
            
            // Validate replay input
            while (true) {
                System.out.print("\nPlay again? (y/n): ");
                answer = sc.nextLine().trim().toLowerCase();
                if (answer.equals("y") || answer.equals("n")) {
                    break;
                }
                System.out.println("Please enter 'y' or 'n'.");
            }
            
            if (answer.equals("y")) {
                printSeparator();
            }

        } while (answer.equals("y"));

        printFarewell();
        sc.close();
    }
    
    private static void playGame(Scanner sc, int min, int max, int maxHints) {
        int secret = ThreadLocalRandom.current().nextInt(min, max + 1);
        int guess = 0;
        int attempts = 0;
        int hintsUsed = 0;
        
        System.out.println("\nI'm thinking of a number between " + min + " and " + max + ".");
        System.out.println("You have " + maxHints + " hint(s) available (type 'hint' to use one).\n");

        while (guess != secret) {
            System.out.print("Enter your guess: ");
            String input = sc.nextLine().trim();
            
            // Check for hint request
            if (input.equalsIgnoreCase("hint")) {
                if (hintsUsed < maxHints) {
                    giveHint(secret, min, max);
                    hintsUsed++;
                    System.out.println("Hints remaining: " + (maxHints - hintsUsed));
                } else {
                    System.out.println("No hints remaining!");
                }
                continue;
            }
            
            // Validate numeric input
            if (!isNumeric(input)) {
                System.out.println("Please enter a valid number (or 'hint').");
                continue;
            }
            
            guess = Integer.parseInt(input);
            
            // Validate range BEFORE incrementing attempts
            if (guess < min || guess > max) {
                System.out.println("Out of range! Guess between " + min + " and " + max + ".");
                continue;
            }
            
            attempts++;
            
            if (guess > secret) {
                int diff = guess - secret;
                System.out.println("Too high!" + getProximityHint(diff));
            } else if (guess < secret) {
                int diff = secret - guess;
                System.out.println("Too low!" + getProximityHint(diff));
            }
        }
        
        // Game won - update statistics
        gamesPlayed++;
        totalAttempts += attempts;
        if (attempts < bestScore) {
            bestScore = attempts;
            System.out.println("\n----- NEW BEST SCORE! -----");
        }
        
        System.out.println("\nCorrect! The number was " + secret + ".");
        System.out.println("You guessed it in " + attempts + " " + (attempts == 1 ? "attempt" : "attempts") + "!");
        
        // Performance evaluation
        int optimalAttempts = (int) Math.ceil(Math.log(max - min + 1) / Math.log(2));
        if (attempts <= optimalAttempts) {
            System.out.println("Excellent! That's optimal or near-optimal!");
        } else if (attempts <= optimalAttempts + 3) {
            System.out.println("→ Good job! Close to optimal.");
        } else {
            System.out.println("-> Not bad! Optimal would be around " + optimalAttempts + " attempts.");
        }
        
        printStatistics();
    }
    
    private static int selectDifficulty(Scanner sc) {
        System.out.println("\nSelect Difficulty:");
        System.out.println("1. Easy (1-50)");
        System.out.println("2. Medium (1-100)");
        System.out.println("3. Hard (1-200)");
        System.out.println("4. Expert (1-500)");
        
        int choice;
        while (true) {
            System.out.print("\nEnter choice (1-4): ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine(); // Clear buffer
                if (choice >= 1 && choice <= 4) {
                    break;
                }
            } else {
                sc.nextLine(); // Clear invalid input
            }
            System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
        }
        
        return choice;
    }
    
    private static int[] getDifficultyRange(int difficulty) {
        switch (difficulty) {
            case 1: return new int[]{1, 50};
            case 2: return new int[]{1, 100};
            case 3: return new int[]{1, 200};
            case 4: return new int[]{1, 500};
            default: return new int[]{1, 100};
        }
    }
    
    private static int getMaxHints(int difficulty) {
        switch (difficulty) {
            case 1: return 3;
            case 2: return 2;
            case 3: return 1;
            case 4: return 1;
            default: return 2;
        }
    }
    
    private static void giveHint(int secret, int min, int max) {
        int range = max - min + 1;
        int hintType = ThreadLocalRandom.current().nextInt(3);
        
        switch (hintType) {
            case 0: // Even/Odd hint
                System.out.println("\n -> Hint: The number is " + (secret % 2 == 0 ? "EVEN" : "ODD"));
                break;
            case 1: // Range hint
                int midpoint = (min + max) / 2;
                if (secret <= midpoint) {
                    System.out.println("\n -> Hint: The number is in the LOWER half (" + min + "-" + midpoint + ")");
                } else {
                    System.out.println("\n -> Hint: The number is in the UPPER half (" + (midpoint + 1) + "-" + max + ")");
                }
                break;
            case 2: // Divisibility hint
                if (secret % 5 == 0) {
                    System.out.println("\n -> Hint: The number is divisible by 5");
                } else if (secret % 3 == 0) {
                    System.out.println("\n -> Hint: The number is divisible by 3");
                } else {
                    System.out.println("\n -> Hint: The number is NOT divisible by 3 or 5");
                }
                break;
        }
    }
    
    private static String getProximityHint(int difference) {
        if (difference <= 5) {
            return " (Very close!)";
        } else if (difference <= 10) {
            return " (Close!)";
        } else if (difference <= 20) {
            return " (Getting warmer...)";
        } else {
            return " (Far off!)";
        }
    }
    
    private static void printStatistics() {
        System.out.println("\n--- STATISTICS ---");
        System.out.println("Games Played: " + gamesPlayed);
        System.out.println("Best Score: " + (bestScore == Integer.MAX_VALUE ? "N/A" : bestScore + " attempts"));
        if (gamesPlayed > 0) {
            double average = (double) totalAttempts / gamesPlayed;
            System.out.printf("Average Attempts: %.1f\n", average);
        }
        System.out.println("------------------");
    }
    
    private static void printWelcome() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     NUMBER GUESSING GAME v2.0         ║");
        System.out.println("║  Guess the number using strategy!     ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    private static void printFarewell() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   Thanks for playing!                 ║");
        System.out.println("║   Final Statistics:                   ║");
        System.out.println("╚════════════════════════════════════════╝");
        printStatistics();
        System.out.println("\nSee you next time!");
    }
    
    private static void printSeparator() {
        System.out.println("\n" + "═".repeat(42) + "\n");
    }
    
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}