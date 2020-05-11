
import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    private int x;
    private int y;
    private Direction direction = Direction.LEFT;

    public boolean isAlive = true;

    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 0; i < 3; i++) {
            GameObject pieceOfSnake = new GameObject(x + i, y);
            snakeParts.add(pieceOfSnake);
        }
    }

    public void draw(Game game) {
        for (GameObject pieceOfSnake : snakeParts) {
            if (isAlive) {
                if (snakeParts.indexOf(pieceOfSnake) == 0) {
                    game.setCellValueEx(pieceOfSnake.x, pieceOfSnake.y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                } else {
                    game.setCellValueEx(pieceOfSnake.x, pieceOfSnake.y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
            } else {
                if (snakeParts.indexOf(pieceOfSnake) == 0) {
                    game.setCellValueEx(pieceOfSnake.x, pieceOfSnake.y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                } else {
                    game.setCellValueEx(pieceOfSnake.x, pieceOfSnake.y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }
    }

    public void setDirection(Direction direction) {
        switch (this.direction) {
            case LEFT:
                if (direction == Direction.RIGHT || snakeParts.get(0).x == snakeParts.get(1).x) {
                    break;
                }
                this.direction = direction;
            case RIGHT:
                if (direction == Direction.LEFT || snakeParts.get(0).x == snakeParts.get(1).x) {
                    break;
                }
                this.direction = direction;
            case UP:
                if (direction == Direction.DOWN || snakeParts.get(0).y == snakeParts.get(1).y) {
                    break;
                }
                this.direction = direction;
            case DOWN:
                if (direction == Direction.UP || snakeParts.get(0).y == snakeParts.get(1).y) {
                    break;
                }
                this.direction = direction;
        }
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x > SnakeGame.WIDTH - 1 || newHead.y < 0 || newHead.y > SnakeGame.HEIGHT - 1 || checkCollision(newHead)) {
            this.isAlive = false;
        } else if (apple.x == newHead.x && apple.y == newHead.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
        } else {
            snakeParts.add(0, newHead);
            removeTail();
        }
    }

    public void move(Apple apple, BlueApple blueApple) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x > SnakeGame.WIDTH - 1 || newHead.y < 0 || newHead.y > SnakeGame.HEIGHT - 1 || checkCollision(newHead)) {
            this.isAlive = false;
        } else if (apple.x == newHead.x && apple.y == newHead.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
        } else if(blueApple.x == newHead.x && blueApple.y == newHead.y) {
            blueApple.isAlive = false;
            snakeParts.add(0, newHead);
        } else {
            snakeParts.add(0, newHead);
            removeTail();
        }
    }

    public void move(Apple apple, BlueApple blueApple, Obstacle obstacle) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x > SnakeGame.WIDTH - 1 || newHead.y < 0 || newHead.y > SnakeGame.HEIGHT - 1 || checkCollision(newHead)) {
            this.isAlive = false;
        } else if (apple.x == newHead.x && apple.y == newHead.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
        } else if(blueApple.x == newHead.x && blueApple.y == newHead.y) {
            blueApple.isAlive = false;
            snakeParts.add(0, newHead);
        } else if(obstacle.x == newHead.x && obstacle.y == newHead.y) {
            this.isAlive = false;
        } else {
            snakeParts.add(0, newHead);
            removeTail();
        }
    }

    public GameObject createNewHead() {
        GameObject newHead;
        if (direction == Direction.LEFT) {
            newHead = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        } else if (direction == Direction.RIGHT) {
            newHead = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        } else if (direction == Direction.UP) {
            newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        } else {
            newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        }
        return newHead;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject object) {
        boolean collision = false;
        for(GameObject pieceOfSnake : snakeParts) {
            if (object.x == pieceOfSnake.x && object.y == pieceOfSnake.y) {
                collision = true;
                if(object instanceof Apple || object instanceof BlueApple || object instanceof Obstacle) {
                    break;
                } else {
                    isAlive = false;
                    break;
                }
            } else {
                collision = false;
            }
        }
        return collision;
    }

    public int getLength(){
        return snakeParts.size();
    }

}
