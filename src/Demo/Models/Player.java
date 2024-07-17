package Demo.Models;

import Demo.RockPaperScissors;

import java.util.ArrayList;


public class Player {
  public ArrayList<RockPaperScissors.CARDS> hand;
  public int score = 0;
  public RockPaperScissors.CARDS move;
  public Player(ArrayList<RockPaperScissors.CARDS> playerDeck){
    this.hand = playerDeck;
  }

  public Player(){
    this.hand = new ArrayList<>();
  }
}
