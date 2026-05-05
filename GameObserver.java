public interface GameObserver {
    void onGameUpdate();
    void onGameOver(String message);
    void onScoreAdded(int point,int combostack);
}