package Demo;

import Demo.Models.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RockPaperScissors {
  private final LinkedList<CARDS> startingDeck = new LinkedList<>(Arrays
      .asList(CARDS.ROCK, CARDS.PAPER, CARDS.SCISSORS));
  private final Scanner scanner = new Scanner(System.in);
  private final Player player = new Player();
  private final Player bot = new Player();
  private final int handSize = 3;
  private final Random random = new Random();

  public RockPaperScissors(){
  }

  /**
   * Runs a game to completion
   */
  public void playGame(){
    generateHands();
    
    // Should only loop once
    while ( player.hand.size() + bot.hand.size() >= handSize*2){
      System.out.println("Your hand is: " + player.hand);
      System.out.println("Select the index of the card to play, options: " + getListOfArrayListIndexes(player.hand));

      try {
        int selectedIndex = scanner.nextInt() - 1;
        player.move = player.hand.remove(selectedIndex);
      } catch (IndexOutOfBoundsException | InputMismatchException e){
        System.out.println("Illegal input");
        continue;
      }
      bot.move = bot.hand.remove(random.nextInt(bot.hand.size()));

      System.out.println("You played " + player.move + ", Bot played " + bot.move);

      // Rock paper scissors logic
      if (player.move.equals(bot.move)){
        System.out.println("It's a tie!");
      } else if ((player.move.equals(CARDS.ROCK) && bot.move.equals(CARDS.SCISSORS)) ||
          (player.move.equals(CARDS.PAPER) && bot.move.equals(CARDS.ROCK)) ||
          (player.move.equals(CARDS.SCISSORS) && bot.move.equals(CARDS.PAPER))){
        System.out.println("You won the round!");
        player.score++;
      } else {
        System.out.println("You lost the round");
        bot.score++;
      }

      System.out.println("Score is " + player.score + "-" + bot.score);
    }

    // Check to see who won
    if (player.score == bot.score){
      System.out.println("You tied this game");
    } else if (player.score > bot.score){
      System.out.println("Congrats winning the game!");
    } else {
      System.out.println("Hi Caleb");
    }
  }
  
  /**
   * Create player hands
   */
  private void generateHands(){
    for (int i = 0; i < handSize; i++) {
      while (true) {
        System.out.println("Select 3 cards to add to the deck\nOn card " + (i + 1) + " of " + handSize);
        System.out.println(Arrays.asList(CARDS.values()));
        try {
          int selectedIndex = scanner.nextInt() - 1;
          startingDeck.add(
                  Arrays.asList(CARDS.values()).get(selectedIndex)
          );
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
          System.out.println("Illegal input");
          continue;
        }
        break;
      }

      // Bot adds random card
      startingDeck.add(startingDeck.get(random.nextInt(startingDeck.size())));
    }

    // Ensure cards are randomized before handing out
    Collections.shuffle(startingDeck);

    // Distribute cards to players
    for (int i = 0; i < handSize; i++) {
      player.hand.add(startingDeck.pop());
      bot.hand.add(startingDeck.pop());
    }
  }

  private List<Integer> getListOfArrayListIndexes(ArrayList<CARDS> arrayList){
    return IntStream.rangeClosed(1, arrayList.size())
        .boxed()
        .collect(Collectors.toList());
  }

  /**
   * Represents cards in a deck that a player use
   */
  public enum CARDS{
    ROCK, PAPER, SCISSORS
  }
}
