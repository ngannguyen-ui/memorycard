public class GameManager {
    private Board board;
    private Player player;
    private Card firstSelectedCard;
    private Card SecondSelectedCard;
    private GameStatus status;
    private GameObserver observer;
    private long startTime;
    private long totalTime;
    private long bestTime;
    private int moveCount=0;
    private int MAX_Moves=7;
    private int comboStack;
    private final int BASE_SCORE=10;
    private GameLevel currentLevel; 
    public GameManager(Board board,Player player){
        this.board=board;
        this.player=player;
        this.firstSelectedCard=null;
        this.SecondSelectedCard=null;
        this.moveCount=0;
        this.status=GameStatus.PLAYING;
    }
    public void setObserver(GameObserver observer){
        this.observer=observer;
    }
    public void notifyObserver(){
        if (observer!=null){
            observer.onGameUpdate();
        }
    }
    public Board getBoard(){
        return this.board;
    }
    public Player getPlayer(){
        return this.player;
    }
    public Card getfirstSelectedCard(){
        return this.firstSelectedCard;
    }
    public Card getsecondSelectedCard(){
        return this.SecondSelectedCard;
    }
    public GameStatus gameStatus(){
        return this.status;
    }
    public int getMoveCount(){
        return this.moveCount;
    }
    public int getComboStack(){
        return this.comboStack;
    }
    public int getBaseScore(){
        return this.BASE_SCORE;
    }
    public void setGameLevel(GameLevel level){
        this.currentLevel=level;
    }
    public void startGame(){
        int previewTime=currentLevel.getPreviewTime();
        MAX_Moves=currentLevel.getMaxMoves();
        startPreview(previewTime);
    }
    public void startPreview(int miliseconds){
        status=GameStatus.WAITING;
        board.showBoard();
        notifyObserver();
        javax.swing.Timer previewTimer=new javax.swing.Timer(miliseconds,e->{
            board.hideAllCard();
            this.status=GameStatus.PLAYING;
            notifyObserver();
        });
        previewTimer.setRepeats(false);
        previewTimer.start();
    }
    public void startTime(){
        if (this.startTime==0){
            this.startTime=System.currentTimeMillis();
        }
    }
    public long getElapsedTime(){
         if (status==GameStatus.FINISHED){
            return totalTime;
         }
         return (System.currentTimeMillis()-startTime)/1000;
    }
    public void processMatch(){
        firstSelectedCard.setMatch(true);
        SecondSelectedCard.setMatch(true);
        comboStack++;
        player.addMatchPair(SecondSelectedCard, firstSelectedCard);
        int earnedPoints=BASE_SCORE*comboStack;
        player.addScore(earnedPoints);
        firstSelectedCard=null;
        SecondSelectedCard=null;
        if (board.allMatched()){
            status=GameStatus.FINISHED;
            if (observer!=null) {
                observer.onGameOver("WINNING");
            }
        }
        if (observer!=null){
            observer.onScoreAdded(earnedPoints,comboStack);
        }
        notifyObserver();
    }
    public void MisMatch(){
        comboStack=0;
        status=GameStatus.WAITING;
        javax.swing.Timer timer=new javax.swing.Timer(1000, e->{
            firstSelectedCard.hide();
            SecondSelectedCard.hide();
            firstSelectedCard=null;
            SecondSelectedCard=null;
            status=GameStatus.PLAYING;
            notifyObserver();
        });
        timer.setRepeats(false);
        timer.start();
    }
    public void SelectedCard(int r,int c){
        Card currentCard=board.getCard(r, c);
        if (currentCard==null||status!=GameStatus.PLAYING||currentCard.isMatch()||currentCard.isFlipped()){
            return;
        }
        currentCard.flip();
        notifyObserver();
        if (firstSelectedCard==null){
            firstSelectedCard=currentCard;
        }
        else{
            SecondSelectedCard=currentCard;
            SecondSelectedCard.flip();
            moveCount++;
        if (SecondSelectedCard.checkMatch(firstSelectedCard)){
            processMatch();
            
        } else{
            MisMatch();
        }
        if (moveCount>=MAX_Moves&&!board.allMatched()&&status!=GameStatus.WAITING) {
            status=GameStatus.LOOSE;
            if (observer!=null){
                observer.onGameOver("GAME OVER");
            }
        }
    
    }
}public void resetGame(){
    moveCount=0;
    this.firstSelectedCard=null;
    this.SecondSelectedCard=null;
    this.status=GameStatus.WAITING;
    board.resetBoard();
    startPreview(1000);

}
    
}
