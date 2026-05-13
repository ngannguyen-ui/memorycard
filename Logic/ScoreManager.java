package Logic;

import java.util.*;

public class ScoreManager {
    private int earnedPoints;
    private int BASE_SCORE;
    private int comboStack;
    private List<Player> leaderboard = new ArrayList<>();
    public ScoreManager(){
        this.earnedPoints=0;
        this.comboStack=0;
        this.BASE_SCORE=10;
    }

    public int getBaseScore(){
        return this.BASE_SCORE;
    }
    
    public int getComboStack(){
        return this.comboStack;
    }

    public int getEarnedPoints(){
        return this.earnedPoints;
    }

    public void addMatchPoints(){
        this.comboStack++;
        this.earnedPoints+=BASE_SCORE*comboStack;
    }

    public void resetCombo(){
        this.comboStack=0;
    }

    public void resetScore(){
        this.earnedPoints=0;
        this.comboStack=0;
    }

    public List<Player> getTopPlayers(int n){
        return leaderboard.subList(0, Math.min(n, leaderboard.size()));
    }

      public void saveToLeaderboard(Player player) {
        leaderboard.add(player);
        leaderboard.sort(Comparator.comparingInt(Player::getScore).reversed());
    }

    public void printLeaderboard(){
        System.out.print("===  LEARDERBOARD ===\n");
        List<Player> top = getTopPlayers(5);
        for (int i =0; i < top.size(); i++){
            System.out.println((i + 1) + "." + top.get(i));
        }
    }
}
    
