public class ScoreManager {
    private Player player;
    private int earnedPoints;
    private int BASE_SCORE;
    private int comboStack;
    public ScoreManager(Player player){
        this.player=player;
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
}
    
