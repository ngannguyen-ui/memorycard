package Logic;

import java.util.Random;
import javax.swing.JOptionPane;;

public class Player {
    private String Name;
    private int Score;
    private int matchedPairs;
    public Player(String Name){
        this.Name=playerName();
        this.Score=0;
        this.matchedPairs=0;
    }

     private String generateRandomName(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++){
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String playerName(){
        String name = JOptionPane.showInputDialog(
            null,
            "Input your name: ",
            JOptionPane.PLAIN_MESSAGE
        );
        if (name == null || name.trim().isEmpty()){
            name = generateRandomName();
        }
        return name.trim();
    }

    public String getName(){
        return this.Name;
    }
    public int getScore(){
        return this.Score;
    }
    public int getMatchedRepair(){
        return this.matchedPairs;
    }
    public void addScore(int point){
        this.Score+=point;
    }
    public void addMatchPair(Card currentCard,Card firstSeleCard){
        this.matchedPairs++;
        }
    
     @Override
    public String toString(){
        return Name + " | Score: " + Score;
    }
}