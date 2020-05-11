import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

public class Obstacle extends GameObject {
    private static final String STOP_SIGN = "\u26D4";

    public boolean isAlive = true;

    public Obstacle (int x, int y){
        super(x, y);
    }

    public void draw(Game game) {
        game.setCellValueEx(x, y, Color.NONE, STOP_SIGN, Color.RED, 75 );
    }
}
