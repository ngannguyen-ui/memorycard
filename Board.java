import java.util.ArrayList;
import java.util.Collections;
public class Board {
    private Card[][] cards;
    private int rows=4;
    private int columns=4;
    private ArrayList <String>imagePath;
    public Board(){
        this.cards=new Card[rows][columns];
        this.imagePath=new ArrayList<>();
        addImage();
        shuffleCard();
    }
    public void addImage(){
        imagePath.clear();
        imagePath.add("c:\\Users\\admin\\Downloads\\strawberry.jpg");
        imagePath.add("c:\\Users\\admin\\Downloads\\papaya.jpg");
        imagePath.add("c:\\Users\\admin\\Downloads\\grape.jpg");
        imagePath.add("c:\\Users\\admin\\Downloads\\blueberry.jpg");
        imagePath.add("c:\\Users\\admin\\Downloads\\avocado.jpg");
        imagePath.add("c:\\Users\\admin\\Downloads\\apple.jpg");
        imagePath.add("c:\\Users\\admin\\Downloads\\passionfruit.jpg");
        imagePath.add("c:\\Users\\admin\\Downloads\\pomergranate.jpg");
    }
    public ArrayList<Card> generateCards(){
        ArrayList<Card>tempList=new ArrayList<>();
        int numbercard=rows*columns;
        int numberpair=numbercard/2;
        int inde=0;
        for (int i=0;i<numberpair;i++){
            String imagePaths=imagePath.get(i);
            tempList.add(new Card(inde,imagePaths,i));
            inde++;
            tempList.add(new Card(inde,imagePaths,i));
        }
        return tempList;
        }
    public void shuffleCard(){
        ArrayList<Card>list=generateCards();
        Collections.shuffle(list);
        int index=0;
        for (int k=0;k<rows;k++){
            for (int z=0;z<rows;z++){
                cards[k][z]=list.get(index);
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
        addImage();
        shuffleCard();
    }
    public void resetBoard(){
        shuffleCard();
        
    }
}
