package controller;

import domain.game.Food;
import domain.game.Snake;
import domain.mess.Message;
import model.Game;
import utils.GameUtil;
import utils.MessageUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 22:45
 */
public class GameController {
	// game的实例
	private final Game game;

	// game pusher的实例
	private final GamePusher pusher;

	public GameController() {
		game = Game.getInstance();
		pusher = new GamePusher(this);
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
		try {
			if (game.getState() == 1) {
				MessageUtil.sendMess(new Message(4, -1, "the game has been started!"), socket);
				return;
			}
			if (type == 0) {
				if (game.getFood() != null) {
					MessageUtil.sendMess(
							new Message(4, -1,
							"you want to be the food, but the food is played by someone XD."),
							socket);
					return;
				}
				game.initialFood(socket);
			} else {
				if (game.getSnake() != null) {
					MessageUtil.sendMess(
							new Message(4, -1,
									"you want to be the snake, but the snake is played by someone XD."),
							socket);
					return;
				}
				game.initialSnake(socket);
			}

			// 判断游戏是否进入START_WAITING
			if (game.getSnake() != null && game.getFood() != null) {
				game.setState(2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// todo: deal with when the state is PLAY_WAITING but the player exit
	// ...

	// 某个玩家准备开始玩游戏!
	public void preparePlaying(int type) {
		if (type == 0) {
			game.getSnakePlayer().setPrepared(true);
		} else {
			game.getFoodPlayer().setPrepared(true);
		}

		try {
			// 双方都准备好就开始游戏
			if (game.getSnakePlayer().isPrepared() && game.getFoodPlayer().isPrepared()) {
				game.setState(1);

				// change the game state
				game.setState(1);

				// tell every one the game is going to start.
				Message mess = new Message(0, -1, "game start!");
				game.getSnakePlayer().sendMess(mess);
				game.getFoodPlayer().sendMess(mess);

				// open the thread to push the snake and food
				pusher.makeStartedAble();
				new Thread(pusher).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// 比赛过程中通知玩家更新游戏视图
	public void notifyPlayer() {
		game.notifyObservers();
	}

	// 当游戏开始的时候接收移动信息
	public void move(int type, int direction) {
		try {
			Food food = game.getFood();
			Snake snake = game.getSnake();
			if (type == 0) {
				// 移动方向和蛇的方向相反直接返回
				if (direction == GameUtil.reserveDirection(snake.getDirection()))
					return;
				// 下一步撞墙直接返回
				if (GameUtil.hitWall(direction, snake.getHead()) ||
						GameUtil.hitSnake(direction, snake.getHead(), snake.getBody())) {

					Message mess = new Message(2, -1, "the snake lose and the food won !!!XD");
					game.getFoodPlayer().sendMess(mess);
					game.getSnakePlayer().sendMess(mess);

					pusher.stop();
					game.setState(2);
					game.gameOver();
					// todo: tell someone else...
					return;
				}
				// 下一步撞到食物就是赢了
				if (GameUtil.hitFood(direction, snake.getHead(), food.getPos())) {
					Message mess = new Message(2, -1, "the snake won and the food lose !!!XD");
					game.getFoodPlayer().sendMess(mess);
					game.getSnakePlayer().sendMess(mess);

					pusher.stop();
					game.setState(2);
					game.gameOver();
					// todo: tell someone else...
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
