package utils;

import domain.mess.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 0:13
 */
public class MessageUtil {
	// 消息结束符
	public static final char END_CHAR = '#';

	// 发送一个消息
	public static void sendMess(Message mess, Socket socket) throws IOException {
		OutputStream os = socket.getOutputStream();
		os.write(mess.getType());
		os.write(mess.getPlayerType());
		os.write(mess.getContext().getBytes());
		os.write(END_CHAR);
		os.flush();
	}

	// 接收一个消息
	public static Message receiveMess(Socket socket) throws IOException {
		InputStream is = socket.getInputStream();
		int type = is.read();
		int playerType = is.read();
		int data;
		StringBuilder builder = new StringBuilder();
		while ((data = is.read()) != END_CHAR) {
			builder.append((char) data);
		}
		return new Message(type, playerType, builder.toString());
	}
}
