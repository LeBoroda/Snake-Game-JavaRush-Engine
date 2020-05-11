import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

public class BlueApple extends GameObject {
    private static final String APPLE_SIGN = "\uD83C\uDF50";

    public boolean isAlive = true;

    public BlueApple(int x, int y){
        super(x, y);
    }

    public void draw(Game game){
        game.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, Color.BLUE, 75 );
    }
}
