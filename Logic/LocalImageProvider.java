package Logic;
import java.util.ArrayList;

public class LocalImageProvider implements ImageProvider{
    @Override
    public ArrayList<String>imagePath(){
        ArrayList<String>images=new ArrayList<>();
        images.add("UI/images/dragonfruit.png");
        images.add("UI/images/lemon.png");
        images.add("UI/images/cherry.png");
        images.add("UI/images/melon.png");
        images.add("UI/images/coconut.png");
        images.add("UI/images/watermelon.png");
        images.add("UI/images/starfruit.png");
        images.add("UI/images/mangosteen.png");
        return images;
    }
}