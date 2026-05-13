package Logic;

import javax.swing.SwingUtilities;

public class GameManager {
    private Board board;
    private Player player;
    private Card firstSelectedCard;
    private Card secondSelectedCard;
    private GameStatus status;
    private GameObserver observer;
    private StateGameObserver stateObserver;
    private ScoreGameObserver scoreObserver;
    private ScoreManager scoreManager;
    private TimeManager timeManager;
    private int moveCount=0;
    private int MAX_Moves=7;
    private final int BASE_SCORE=10;
    private GameLevel currentLevel;
    public GameManager(Board board,Player player){
        this.board=board;
        this.player=player;
        this.scoreManager= new ScoreManager();
        this.firstSelectedCard=null;
        this.secondSelectedCard=null;
        this.moveCount=0;
        this.status=GameStatus.PLAYING;
        this.timeManager=new TimeManager();
    }

    public void setObserver(GameObserver observer){
        this.observer = observer;
    }
    public void setStateObserver(StateGameObserver stateObserver){
        this.stateObserver= stateObserver;
    }
    public void setScoreObserver(ScoreGameObserver observer){
        this.scoreObserver=observer;
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
        return this.secondSelectedCard;
    }
    public GameStatus gameStatus(){
        return this.status;
    }
    public int getMoveCount(){
        return this.moveCount;
    }
    public int getComboStack(){
        return this.scoreManager.getComboStack();
    }
    public int getBaseScore(){
        return this.scoreManager.getBaseScore();
    }
    public TimeManager getTimeManager() {
        return this.timeManager;
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
        SwingUtilities.invokeLater(() -> {
            notifyObserver();
        });
        javax.swing.Timer previewTimer=new javax.swing.Timer(miliseconds,e->{
            board.hideAllCard();
            this.status=GameStatus.PLAYING;
            notifyObserver();
        });
        previewTimer.setRepeats(false);
        previewTimer.start();
    }
    public void processMatch(){
        firstSelectedCard.setMatch(true);
        secondSelectedCard.setMatch(true);
        scoreManager.addMatchPoints();
        player.addMatchPair(secondSelectedCard, firstSelectedCard);
        int Point=scoreManager.getEarnedPoints();
        player.addScore(Point);
        if (board.allMatched()){
            status=GameStatus.FINISHED;
            timeManager.stop();
            scoreManager.saveToLeaderboard(player);
            scoreManager.printLeaderboard();
            if (stateObserver!=null) {
                stateObserver.onGameOver("WINNING");
            }
        }
        if (scoreObserver!=null){
            scoreObserver.onScoreAdded(scoreManager.getEarnedPoints(), scoreManager.getComboStack());
        }
        notifyObserver();
    }
    public void MisMatch(){
        scoreManager.resetCombo();
        status=GameStatus.WAITING;
        javax.swing.Timer timer=new javax.swing.Timer(1000, e->{
            firstSelectedCard.hide();
            secondSelectedCard.hide();
            resetSelectedCards();
            status=GameStatus.PLAYING;
            notifyObserver();
        });
        timer.setRepeats(false);
        timer.start();
    }
    public long getElapsedTime(){
        return timeManager.getElapsedTime();
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
            secondSelectedCard=currentCard;
            secondSelectedCard.flip();
            moveCount++;
            
            if (secondSelectedCard.checkMatch(firstSelectedCard)){
                processMatch();
            }
            else{
                MisMatch();
            }
            
            if (moveCount>=MAX_Moves&&!board.allMatched()&&status!=GameStatus.WAITING) {
                status=GameStatus.LOOSE;
                timeManager.stop();
                if (stateObserver!=null){
                    stateObserver.onGameOver("GAME OVER");
                }
            }
        }
    }

    public void resetSelectedCards(){
        this.firstSelectedCard=null;
        this.secondSelectedCard=null;
    }

    public void resetGame(){
    moveCount=0;
    scoreManager.resetScore();
    resetSelectedCards();
    this.status=GameStatus.WAITING;
    board.resetBoard();
    startPreview(1000);
}
   
}