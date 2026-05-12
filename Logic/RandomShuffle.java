package Logic;

import java.util.ArrayList;
import java.util.Collections;

public class RandomShuffle implements ShuffleStrategy{
    @Override
    public ArrayList<Card> shuffle(ArrayList<Card>cards){
        Collections.shuffle(cards);
        return cards;
    }
}

