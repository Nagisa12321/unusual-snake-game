package domain.game;

import domain.Toy;

import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 23:30
 */
public class Player {
	// 玩家的链接
	private Socket socket;
	// 玩家类型
	private int type;

	// 对所玩的东西的引用
	private Toy toy;

	public Player(Toy toy, int type, Socket socket) {
		this.toy = toy;
		this.type = type;
		this.socket = socket;
	}

	public int getType() {
		return type;
	}

	public Toy getToy() {
		return toy;
	}
}
