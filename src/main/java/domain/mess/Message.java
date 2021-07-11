package domain.mess;

import java.util.StringJoiner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 0:16
 */
public class Message {

	// 消息类型
	// 0 - join 消息
	// 1 - move 消息
	private final int type;

	// 玩家类型
	private final int playerType;

	// 消息内容
	private  final String context;

	public Message(int type, int playerType, String context) {
		this.type = type;
		this.playerType = playerType;
		this.context = context;
	}

	public int getType() {
		return type;
	}

	public int getPlayerType() {
		return playerType;
	}

	public String getContext() {
		return context;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Message.class.getSimpleName() + "[", "]")
				.add("type=" + type)
				.add("playerType=" + playerType)
				.add("context='" + context + "'")
				.toString();
	}
}
