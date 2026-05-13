package Logic;

public enum GameLevel implements DifficultyLevel {
    EASY(3000,36,"Beginner"),
    MEDIUM(2000,26,"Intermediate"),
    HARD(1000,16,"Expert");
    private int previewTime;
    private int maxMoves;
    private String rankName;
    GameLevel(int previewTime,int maxMoves,String rankName){
        this.previewTime=previewTime;
        this.maxMoves=maxMoves;
        this.rankName=rankName;
    }
    public int getPreviewTime(){
        return this.previewTime;
    }
    public int getMaxMoves(){
        return this.maxMoves;
    }
    public String getRankName(){
        return this.rankName;
    }
    
}

