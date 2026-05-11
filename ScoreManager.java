public class ScoreManager {
    private int earnedPoints;
    private int BASE_SCORE;
    private int comboStack;
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
}
    
