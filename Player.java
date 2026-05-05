public class Player {
    private String Name;
    private int Score;
    private int matchedPairs;
    public Player(String Name){
        this.Name=Name;
        this.Score=0;
        this.matchedPairs=0;
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
}