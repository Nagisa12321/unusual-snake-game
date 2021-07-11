package controller;

import domain.game.Food;
import domain.game.Snake;
import model.Game;
import utils.GameUtil;

import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 22:45
 */
public class GameController {
	// game的实例
	private final Game game;

	public GameController() {
		game = Game.getInstance();
	}

	// 推动食物前进
	public void pushFood() {
		Food food = game.getFood();
		food.move(food.getDirection());

	}

	// 推动蛇前进
	public void pushSnake() {
		Snake snake = game.getSnake();
		snake.move(snake.getDirection());
	}

	// type
	// 0 - food
	// 1 - snake
	public void joinGame(int type, Socket socket) {
		if (game.getState() == 1) {
			// todo: send that the game is start

			return;
		}
		if (type == 0) {
			if (game.getFood() != null) {
				// todo: send the failed

				return;
			}
			game.initialFood(socket);
		} else {
			if (game.getSnake() != null) {
				// todo: send the failed

				return;
			}
			game.initialSnake(socket);
		}

		// 判断游戏是否能开始
		if (game.getSnake() != null && game.getFood() != null) {
			game.setState(1);

			// todo: start the game
		}
	}


	// 比赛过程中通知玩家更新游戏视图
	public void notifyPlayer() {
		// todo: 通知玩家更新

	}

	// 当游戏开始的时候接收移动信息
	public void move(int type, int direction) {
		Food food = game.getFood();
		Snake snake = game.getSnake();
		if (type == 0) {
			// 移动方向和蛇的方向相反直接返回
			if (direction == GameUtil.reserveDirection(snake.getDirection()))
				return;
			// 下一步撞墙直接返回
			if (GameUtil.hitWall(direction, snake.getHead()) ||
					GameUtil.hitSnake(direction, snake.getHead(), snake.getBody())) {
				// todo: the snake lose

				return;
			}
			// 下一步撞到食物就是赢了
			if (GameUtil.hitFood(direction, snake.getHead(), food.getPos())) {
				// todo: the snake win

				return;
			}

			// 如果什么都没发生, 蛇开始移动一格
			snake.move(direction);
		} else {
			// 食物撞到墙, 什么都没发生
			if (GameUtil.hitWall(direction, food.getPos()))
				return;
			// 食物撞到蛇, 什么也没发生
			if (GameUtil.hitSnake(direction, food.getPos(), snake.getBody()))
				return;
			// 如果什么也没发生, 食物朝着方向移动一格
			food.move(direction);
		}


		// 真正移动成功才通知玩家
		notifyPlayer();
	}
}
