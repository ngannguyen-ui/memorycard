public class CardAnimation {
    private String value;
    private String imagePath;
    private boolean matched;
    private boolean faceUp;
    public CardAnimation(String value, String imagePath){
        this.value = value;
        this.imagePath = imagePath;
        this.matched = false;
        this.faceUp = false;
    }

    public String getValue(){
        return value;
    }

    public String getImagePath(){
        return imagePath;
    }

    public boolean isMatched(){
        return matched;
    }

    public boolean isFaceUp(){
        return faceUp;
    }

    public void setFaceUp(boolean faceUp){
        this.faceUp = faceUp;
    }

    public void setMatched(boolean matched){
        this.matched = matched;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public void reSet(){
        this.matched = false;
        this.faceUp = false;
    }

    @Override
    public String toString(){
        return "Card={" + value + "}" + ", " + "matched: " + matched + ", " + "face up: " + faceUp;
    }

}
