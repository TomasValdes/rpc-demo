package Demo;

import Demo.Models.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RockPaperScissors {
  private final ArrayList<CARDS> listOfAllCardTypes =
      new ArrayList<>(Arrays.asList(CARDS.ROCK, CARDS.PAPER, CARDS.SCISSORS));
  private final Scanner scanner = new Scanner(System.in);
  private final int HAND_SIZE = 3;
  private final int CARDS_TO_REVEAL = 2;
  private final Random random = new Random();
  private Player player;
  private Player bot;
  private LinkedList<CARDS> startingDeck;
  private LinkedList<CARDS> currentDeck;
  boolean revealTopCard = false;

  public RockPaperScissors() {
  }

  /**
   * Runs a game to completion
   */
  public void playGame() {
    player = new Player();
    bot = new Player();
    startingDeck = new LinkedList<>(listOfAllCardTypes);

    selectWinningCard();
    bot.winningCard = listOfAllCardTypes.get(random.nextInt(listOfAllCardTypes.size()));

    generateDeck();
    distributeCards();

    // Main game loop
    while (true) {
      playCard();
      bot.move = bot.hand.remove(random.nextInt(bot.hand.size()));

      System.out.println("You played " + player.move + ", Bot played " + bot.move);

      // Rock paper scissors logic
      if (player.move.equals(bot.move)) {
        System.out.println("It's a tie!");
      } else if (isPlayerVictory()) {
        if (player.winningCard.equals(player.move)) {
          System.out.println("🌟 Congrats on winning the game! 🌟");
          player.score++;
          break;
        } else {
          revealTopCard = true;
          System.out.println("You won the round!");
        }
      } else {
        if (bot.winningCard.equals(bot.move)) {
          System.out.println("Sorry, that's a loss");
          player.score++;
          break;
        } else {
          System.out.println("You lost the round");
        }
      }
      if (player.hand.isEmpty() && bot.hand.isEmpty()) {
        System.out.println("Shuffling and redistributing cards");
        distributeCards();
      }

      if (revealTopCard) {
        revealTopCard = false;
        System.out.println("The top " + CARDS_TO_REVEAL + " cards of the deck are " + currentDeck.subList(0, CARDS_TO_REVEAL));
      }
    }

    System.out.println("Score is " + player.score + "-" + bot.score);

    while (true) {
      System.out.println("Press 1 to play again, 2 to quit");
      try {
        int selectedIndex = scanner.nextInt() - 1;
        if (selectedIndex == 0)
          playGame();
        break;
      } catch (IndexOutOfBoundsException | InputMismatchException _ignore) {
        System.out.println("Illegal input");
      }
    }
  }

  /**
   * Create player hands
   */
  private void generateDeck() {
    for (int i = 0; i < HAND_SIZE; i++) {
      while (true) {
        System.out
            .println("Select 3 cards to add to the deck\nOn card " + (i + 1) + " of " + HAND_SIZE);
        System.out.println(listOfAllCardTypes);
        try {
          int selectedIndex = scanner.nextInt() - 1; 
          startingDeck.add(listOfAllCardTypes.get(selectedIndex));
        } catch (IndexOutOfBoundsException | InputMismatchException _ignore) {
          System.out.println("Illegal input");
          continue;
        }
        break;
      }

      // Bot adds random card
      startingDeck.add(startingDeck.get(random.nextInt(startingDeck.size())));
    }
  }

  private boolean isPlayerVictory(){
    return (player.move.equals(CARDS.ROCK) && bot.move.equals(CARDS.SCISSORS)) || (
        player.move.equals(CARDS.PAPER) && bot.move.equals(CARDS.ROCK)) || (
        player.move.equals(CARDS.SCISSORS) && bot.move.equals(CARDS.PAPER));
  }

  private void selectWinningCard() {
    while (player.winningCard == null) {
      System.out.println(
          "Pick your winning card (enter number 1-" + listOfAllCardTypes.size() + "): "
              + listOfAllCardTypes + " or enter " + (listOfAllCardTypes.size() + 1)
              + " to get an explanation of the game");
      try {
        int selectedIndex = scanner.nextInt() - 1;
        if (selectedIndex == listOfAllCardTypes.size()) {
          System.out.println("This game is played with a deck of 9, "
              + "3 of the cards in the deck will consist of 1 rock, 1 paper, and 1 scissors. "
              + "Players will then pick secretly which card type they will win with called a \"trump\"."
              + "Each player then secretly gets to decide 3 cards of any combination to put into the "
              + "deck. The deck is randomized and each player draws 3. You use your cards to play"
              + "rock paper scissors and if you win a round with your trump you win the game. "
              + "Otherwise a win allows you to look at two of the remaining cards in the deck."
              + "If no players win by the third round the deck is shuffled and each player gets "
              + "3 more cards.");
          continue;
        }
        player.winningCard = listOfAllCardTypes.get(selectedIndex);
      } catch (IndexOutOfBoundsException | InputMismatchException _ignore) {
        System.out.println("Illegal input");
      }
    }
  }

  private void playCard() {
    System.out.println("Your hand is: " + player.hand);
    System.out.println(
        "Select the index of the card to play, options: " + getListOfArrayListIndexes(player.hand));

    try {
      int selectedIndex = scanner.nextInt() - 1;
      player.move = player.hand.remove(selectedIndex);
    } catch (IndexOutOfBoundsException | InputMismatchException _ignore) {
      System.out.println("Illegal input");
      playCard();
    }
  }

  private void distributeCards() {
    // Ensure cards are randomized before handing out
    Collections.shuffle(startingDeck);

    currentDeck = new LinkedList<>(startingDeck);

    // Distribute cards to players
    for (int i = 0; i < HAND_SIZE; i++) {
      player.hand.add(currentDeck.pop());
      bot.hand.add(currentDeck.pop());
    }
  }

  private List<Integer> getListOfArrayListIndexes(ArrayList<CARDS> arrayList) {
    return IntStream.rangeClosed(1, arrayList.size()).boxed().collect(Collectors.toList());
  }

  /**
   * Represents cards in a deck that a player use
   */
  public enum CARDS {
    ROCK, PAPER, SCISSORS
  }
}
