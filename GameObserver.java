public interface GameObserver {
    void onGameUpdate();}
public interface stateGameObserver{
    void onGameOver(String message);}
public interface scoreGameObserver{
    void onScoreAdded(int point,int combostack);
}