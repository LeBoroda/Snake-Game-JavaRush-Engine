import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private Snake snake;
    private Apple apple;
    private BlueApple blueApple;
    private int turnDelay;
    private boolean isGameStopped;
    private int score;

    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        isGameStopped = false;
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        blueApple = null;
        turnDelay = 300;
        score = 0;
        drawScene();
        setTurnTimer(turnDelay);
        setScore(score);
    }

    private void drawScene() {
        for (int x = 0 ;x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
        if (blueApple != null) blueApple.draw(this);
    }

    private void createNewApple() {
        int x = getRandomNumber(WIDTH);
        int y = getRandomNumber(HEIGHT);

        apple = new Apple(x, y);
        if(snake.checkCollision(apple)){
            createNewApple();
        }
    }

    private void createNewBlueApple() {
        int x = getRandomNumber(WIDTH);
        int y = getRandomNumber(HEIGHT);

        blueApple = new BlueApple(x, y);
        if(snake.checkCollision(blueApple)){
            createNewBlueApple();
        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "ТЫ ПРОИГРАЛ!", Color.BLACK, 52);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, ("ПОБЕДА! Счёт: " + score), Color.BLACK, 52);
    }

    @Override
    public void onTurn(int step) {
        if(blueApple != null) {
            snake.move(apple, blueApple);
        } else {
            snake.move (apple);
        }
        if(!apple.isAlive){
            score = score + 5;
            setScore(score);
            if (score != 0 && score%15 == 0) createNewBlueApple();
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if(blueApple != null) {
            if(!blueApple.isAlive) {
                score = score +50;
                setScore(score);
                blueApple = null;
                if (score != 0 && score%15 == 0) createNewBlueApple();
            }
        }
        if(!snake.isAlive) {
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if(key.equals(Key.UP)){
            snake.setDirection(Direction.UP);
        } else if (key.equals(Key.DOWN)) {
            snake.setDirection(Direction.DOWN);
        } else if (key.equals(Key.LEFT)) {
            snake.setDirection(Direction.LEFT);
        } else if (key.equals(Key.RIGHT)) {
            snake.setDirection(Direction.RIGHT);
        }

        if(isGameStopped && key.equals(Key.SPACE)) {
            createGame();
        }
    }
}
