import Models.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RockPaperScissors {
  private LinkedList<String> startingDeck = new LinkedList<>(Arrays
      .asList("ROCK" ,"ROCK", "ROCK", "PAPER", "PAPER", "PAPER","SCISSORS","SCISSORS","SCISSORS"));
  private Scanner scanner = new Scanner(System.in);
  private Player player = new Player();
  private Player bot = new Player();
  private final int handSize = 3;
  private final Random random = new Random();

  /**
   * Create player hands
   */
  public RockPaperScissors(){
    // Ensure cards are randomized before handing out
    Collections.shuffle(startingDeck);

    for (int i = 0; i < handSize; i++) {
      player.hand.add(startingDeck.pop());
      bot.hand.add(startingDeck.pop());
    }
  }

  /**
   * Runs a game to completion
   * @return true if player wins, false otherwise
   */
  public boolean playGame(){
    String playerMove;
    String botMove;

    while (Math.max(player.score, bot.score) < (handSize/2+1) && player.hand.size() + bot.hand.size() != 0){
      System.out.println("Your hand is: " + player.hand);
      System.out.println("Select the index of the card to play, options: " + getListOfArrayListIndexes(player.hand));

      int selectedIndex = scanner.nextInt() - 1;
      try {
        playerMove = player.hand.remove(selectedIndex);;
      } catch (IndexOutOfBoundsException e){
        System.out.println("Illegal input");
        continue;
      }
      botMove = bot.hand.remove(random.nextInt(bot.hand.size()));

      System.out.println("You played " + playerMove + ", Bot played " + botMove);

      // Rock paper scissors logic
      if (playerMove.equals(botMove)){
        System.out.println("It's a tie!");
      } else if ((playerMove.equals("ROCK") && botMove.equals("SCISSORS")) ||
          (playerMove.equals("PAPER") && botMove.equals("ROCK")) ||
          (playerMove.equals("SCISSORS") && botMove.equals("PAPER"))){
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
      return true;
    } else {
      System.out.println("Hi Caleb");
    }
    return false;
  }

  private List<Integer> getListOfArrayListIndexes(ArrayList<String> arrayList){
    return IntStream.rangeClosed(1, arrayList.size())
        .boxed()
        .collect(Collectors.toList());
  }
}
