package Models;

import java.util.ArrayList;

public class Player {
  public ArrayList<String> hand;
  public int score = 0;

  public Player(ArrayList<String> playerDeck){
    this.hand = playerDeck;
  }

  public Player(){
    this.hand = new ArrayList<>();
  }
}
