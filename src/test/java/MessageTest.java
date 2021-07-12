import domain.mess.Message;
import utils.MessageUtil;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 17:27
 */
public class MessageTest {
	public static void main(String[] args) throws Exception {
		ServerSocket socket = new ServerSocket(8088);
		Socket accept = socket.accept();
		Message message = MessageUtil.receiveMess(accept);
		System.out.println(message);
		MessageUtil.sendMess(new Message(111, 11, "test send mess~~"), accept);
	}
}
