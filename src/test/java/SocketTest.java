import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 21:44
 */
public class SocketTest {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(8088);
		Socket accept = socket.accept();

		InputStream is = accept.getInputStream();
		StringBuilder builder = new StringBuilder();
		int data;
		while ((data = is.read()) != -1)
			builder.append((char) data);

		System.out.println(builder.toString());
	}
}
