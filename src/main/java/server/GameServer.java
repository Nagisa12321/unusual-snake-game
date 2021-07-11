package server;

import controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 0:24
 */
public class GameServer {
	// everything start on this
	public static void main(String[] args) {

		// 大家(两人)公用相同的控制器也无所谓
		// 因为可以认为是代码序列
		// Game对象才是真正应该保持安全性的
		GameController controller = new GameController();
		try {
			ServerSocket serverSocket = new ServerSocket(8088);
			while (true) {
				Socket socket = serverSocket.accept();

				// 监听到用户链接, 启动线程
				new Thread(new GameSocketListener(controller, socket)).start();
			}
		} catch (IOException e) {
			System.out.println("> 监听8088端口失败, exit");
		}
	}
}
