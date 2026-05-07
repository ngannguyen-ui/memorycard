import java.util.ArrayList;
import java.util.Collections;
import javax.print.DocFlavor.STRING; 
interface ImageProvider{
    ArrayList<String>imagePath();
}
interface ShuffleStragegy{
    void shuffle(ArrayList<Card>cards);
}
class RandomShuffle implements ShuffleStragegy{
    @Override
    public void shuffle(ArrayList<Card>cards){
        Collections.shuffle(cards);
      
            }
}

class LocalImageProvider implements ImageProvider{
    @Override
    public ArrayList<String>imagePath(){
        ArrayList<String>images=new ArrayList<>();
        images.add("c:\\Users\\admin\\Downloads\\watermelon.jpg");
        images.add("c:\\Users\\admin\\Downloads\\startfruit.jpg");
        images.add("c:\\Users\\admin\\Downloads\\mangosteen.jpg");
        images.add("c:\\Users\\admin\\Downloads\\lemon.jpg");
        images.add("c:\\Users\\admin\\Downloads\\grape.jpg");
        images.add("c:\\Users\\admin\\Downloads\\coconut.jpg");
        images.add("c:\\Users\\admin\\Downloads\\cherry.jpg");
        images.add("c:\\Users\\admin\\Downloads\\Cantaloupe.jpg");
        return images;
    }
}
public class Board {
    private Card[][] cards;
    private int rows=4;
    private int columns=4;
    private ImageProvider imageProvider;
    private ShuffleStragegy shuffleStragegy;
    public Board(ImageProvider Provider,ShuffleStragegy stragegy){
        this.cards=new Card[rows][columns];
        this.imageProvider=Provider;
        this.shuffleStragegy=stragegy;
        initBoard();
    }
    public ArrayList<Card> generateCards(){
        ArrayList<Card>tempList=new ArrayList<>();
        ArrayList<String>images=imageProvider.imagePath();
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
    public void shuffelCard(){
        ArrayList<Card>templist=generateCards();
        shuffleStragegy.shuffle(templist);
        int index=0;
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                cards[i][j]=templist.get(index);
                index++;
            }
        }
    }
    public Card getCard(int row,int col){
        if (row<0||row>=rows||col<0||col>=columns){
            return null;
        }
        return cards[row][col];

    }
    public void showBoard(){
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if (cards[i][j].isFlipped()||cards[i][j].isMatch()){
                    continue;
                }cards[i][j].flip();
            }
        }
        }
    public void hideAllCard(){
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if (!cards[i][j].isMatch()){
                cards[i][j].hide();
            }
        }
    }
    }
    public boolean allMatched(){
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if (!cards[i][j].isMatch()){
                    return false;
                }

            }
        }
        return true;
    }
    public void initBoard(){
        shuffleCard();
    }
    public void resetBoard(){
        shuffleCard();
        
    }
}
