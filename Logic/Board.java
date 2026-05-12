package Logic;
import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private Card[][] Cards;
    private int rows=4;
    private int columns=4;
    private ImageProvider imageProvider;
    private ShuffleStrategy shuffleStrategy;
    public Board(ImageProvider Provider,ShuffleStrategy strategy){
        this.Cards=new Card[rows][columns];
        this.imageProvider=Provider;
        this.shuffleStrategy=strategy;
        initBoard();
    }
    public ArrayList<Card> generateCards(){
        ArrayList<Card>tempList=new ArrayList<>();
        ArrayList<String>images=new LocalImageProvider().imagePath();
        int numbercard=rows*columns;
        int numberpair=numbercard/2;
        int inde=0;
        for (int i=0;i<numberpair;i++){
            String imagePaths=images.get(i);
            tempList.add(new Card(inde,imagePaths,i));
            inde++;
            tempList.add(new Card(inde,imagePaths,i));
        }
        return tempList;
        }

    public void shuffleCard(RandomShuffle randomShuffle){
        ArrayList<Card> shuffledCards = randomShuffle.shuffle(generateCards());
        int index=0;
        if (index>=shuffledCards.size()){
            return;
        }
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                Cards[i][j]=shuffledCards.get(index);
                index++;
            }
        }
    }

    public Card getCard(int row,int col){
        if (row<0||row>=rows||col<0||col>=columns){
            return null;
        }
        return Cards[row][col];

    }

    public void showBoard(){
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if (Cards[i][j].isFlipped()||Cards[i][j].isMatch()){
                    continue;
                }Cards[i][j].flip();
            }
        }
        }
        
    public void hideAllCard(){
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if (!Cards[i][j].isMatch()){
                Cards[i][j].hide();
            }
        }
    }
    }
    public boolean allMatched(){
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if (!Cards[i][j].isMatch()){
                    return false;
                }

            }
        }
        return true;
    }
    public void initBoard(){
        shuffleCard(new RandomShuffle());
    }
    public void resetBoard(){
        shuffleCard(new RandomShuffle());
        
    }
}
