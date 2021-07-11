package model;

import domain.game.Food;
import domain.game.Player;
import domain.game.Snake;

import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 22:19
 */
public class Game {
	// 全局只有一个游戏, 用单例吧
	private static final Game game = new Game();

	// 游戏场景宽度
	public static final int MAP_WIDTH = 60;

	// 游戏场景高度
	public static final int MAP_HEIGHT = 30;

	// 多少毫秒推动食物
	public static final int PUSH_FOOD_TIME = 100;

	// 食物速度比蛇快多少
	public static final int FASTER_THAN_SNAKE = 2;

	// 获得唯一实例
	public static Game getInstance() { return game; }

	// 游戏状态
	// 0 waiting
	// 1 started
	private int state;

	// 蛇的引用, 只有在started才会用到
	private Player snakePlayer;

	// 食物坐标
	private Player foodPlayer;

	// 地图的缓存, 不用每次都new
	private final int[][] map;

	// 初始化游戏
	private Game() {
		map = new int[MAP_HEIGHT + 2][MAP_WIDTH + 2];
		state = 0;
	}

	// 获得当前游戏状态
	public int getState() {
		return state;
	}

	// 设置当前游戏状态
	public void setState(int newState) {
		state = newState;
	}

	// 玩家加入时进行初始化
	public void initialFood(Socket socket) {
		foodPlayer = new Player(new Food(), 1, socket);
	}

	// 玩家加入时进行初始化
	public void initialSnake(Socket socket) {
		snakePlayer = new Player(new Snake(), 0, socket);
	}

	// 游戏结束应该把应用变为null, 我是根据这个判断的
	public void gameOver() {
		foodPlayer = null;
		snakePlayer = null;
	}

	// 游戏地图
	// 1 - wall
	// 0 - empty
	// 2 - snake
	// 3 - food
	// 4 - snake head
	public int[][] getMap() {
		// 全部置为0
		for (int i = 0; i < MAP_HEIGHT + 2; i++)
			for (int j = 0; j < MAP_WIDTH + 2; j++)
				map[i][j] = 0;
		// 画出墙壁
		for (int i = 0; i < MAP_HEIGHT + 2; i++)
			for (int j = 0; j < MAP_WIDTH + 2; j++) {
				if (i == 0 || i == MAP_HEIGHT + 1)
					map[i][j] = 1;
				if (j == 0 || j == MAP_WIDTH + 1)
					map[i][j] = 1;
			}
		// 画出蛇
		for (int[] p : getSnake().getBody())
			map[p[0] + 1][p[1] + 1] = 2;
		// 画出food
		map[getFood().getPos()[0] + 1][getFood().getPos()[1] + 1] = 3;
		// 画出蛇头
		map[getSnake().getHead()[0] + 1][getSnake().getHead()[1] + 1] = 4;
		return map;
	}


	// 从snakePlayer身上拿取snake
	public Snake getSnake() {
		return (Snake) (snakePlayer.getToy());
	}

	// 从snakePlayer身上拿取snake
	public Food getFood() {
		return (Food) (foodPlayer.getToy());
	}
}
