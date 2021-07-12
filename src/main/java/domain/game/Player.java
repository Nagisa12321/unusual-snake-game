package domain.game;

import domain.Toy;
import domain.mess.Message;
import model.Game;
import obsaver.Observer;
import utils.MessageUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.StringJoiner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 23:30
 */
public class Player implements Observer {
	// 玩家的链接
	private final Socket socket;

	// 玩家类型
	private final int type;

	// game唯一实例
	private final Game game;

	// 对所玩的东西的引用
	private final Toy toy;

	// 玩家是否准备开始玩
	private boolean prepared;

	public Player(Toy toy, int type, Socket socket) {
		this.toy = toy;
		this.type = type;
		this.socket = socket;
		this.game = Game.getInstance();
		this.prepared = false;
	}

	public boolean isPrepared() {
		return prepared;
	}

	public void setPrepared(boolean prepared) {
		this.prepared = prepared;
	}

	// 把自己注册为观察者
	public void register() {
		game.register(this);
	}

	// 取消注册自己为观察者
	public void undoRegister() {
		game.removeObserver(this);
	}

	public int getType() {
		return type;
	}

	public Toy getToy() {
		return toy;
	}

	// 主要用于更新地图操作
	@Override
	public void update() {
		try {
			StringBuilder contextBuffer = new StringBuilder();
			int[][] map = game.getMap();
			contextBuffer.append((char) (map.length));
			contextBuffer.append((char) (map[0].length));
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					contextBuffer.append((char) map[i][j]);
				}
			}
			Message message = new Message(1, -1, contextBuffer.toString());
			sendMess(message);

			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMess(Message message) throws IOException {
		MessageUtil.sendMess(message, socket);
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Player.class.getSimpleName() + "[", "]")
				.add("socket=" + socket)
				.add("type=" + type)
				.add("prepared=" + prepared)
				.toString();
	}
}
