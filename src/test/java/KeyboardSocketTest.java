import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 14:35
 */
public class KeyboardSocketTest {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(8088);
		Socket accept = socket.accept();

		InputStream is = accept.getInputStream();
		StringBuilder builder;
		while (true) {
			builder = new StringBuilder();
			int data;
			while ((data = is.read()) != '#')
				builder.append((char) data);
			String s = builder.toString();
			if (s.equals("esc"))
				break;
			System.out.println(s);
		}
	}
}
