package controller;

import model.Game;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 0:44
 */
public class GamePusher implements Runnable {

	// 对游戏控制器的引用
	private final GameController controller;

	// pusher线程运行/结束
	private volatile boolean running;

	public GamePusher(GameController controller) {
		this.controller = controller;
		this.running = true;
	}

	// 该线程负责推动蛇/食物前进
	@Override
	public void run() {
		try {
			while (running) {
				for (int i = 0; i < Game.FASTER_THAN_SNAKE; i++) {
					if (i == 0)
						controller.pushSnake();
					controller.pushFood();
					Thread.sleep(Game.PUSH_FOOD_TIME);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 停止pusher线程
	public void stop() {
		running = false;
	}
}
