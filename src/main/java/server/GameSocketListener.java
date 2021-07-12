package server;

import controller.GameController;
import domain.mess.Message;
import utils.LogUtil;
import utils.MessageUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 0:27
 */
public class GameSocketListener implements Runnable {

	// 游戏控制器
	private final GameController controller;

	// 玩家链接
	private final Socket socket;

	// pusher线程运行/结束
	private volatile boolean running;

	public GameSocketListener(GameController controller, Socket socket) {
		this.controller = controller;
		this.socket = socket;
		this.running = true;
	}

	@Override
	public void run() {
		LogUtil.log("game socket listener start to listen the sock: " + socket);
		while (running) {
			try {
				Message message = MessageUtil.receiveMess(socket);
				LogUtil.log("get the message: " + message);
				int type = message.getType();
				int playerType = message.getPlayerType();
				// join 消息
				if (type == 0) {
					controller.joinGame(playerType, socket);
				}
				// move消息
				if (type == 1) {
					controller.move(playerType, Integer.parseInt(message.getContext()));
				}
				// prepared消息
				if (type == 2) {
					controller.preparePlaying(playerType);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	// 停止listener线程
	public void stop() {
		running = false;
	}
}
