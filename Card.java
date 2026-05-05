

public class Card {
    private int ID;
    private int pairID;
    private String imagePath;
    private boolean isFlipped;
    private boolean isMatch;
    public Card(int ID,String imagePath,int pairID){
        this.ID=ID;
        this.imagePath=imagePath;
        this.pairID=pairID;
        this.isFlipped=false;
        this.isMatch=false;
    }
    public int getID(){
        return this.ID;
    }
    public int getPairID(){
        return this.pairID;
    }
    public String imagePath(){
        return this.imagePath;
    }
    public boolean isFlipped(){
        return this.isFlipped;
    }
    public boolean isMatch(){
        return this.isMatch;
    }
    public void setMatch(boolean match){
        this.isMatch=match;

    }
    public void flip(){
        if (isMatch||isFlipped){
            return;
        }
        this.isFlipped=true;
    }
    public void hide(){
        if (!isMatch){
            isFlipped=false;
        }
    }
    public boolean checkMatch(Card other){
        return other!=null &&this.pairID==other.getPairID()&&this.ID!=other.getID();
    }
    public void Reset(){
        this.isFlipped=false;
        this.isMatch=false;
    }
    }
