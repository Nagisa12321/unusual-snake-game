package utils;

import java.io.*;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 16:54
 */
public class LogUtil {
	private static final String logAddr = "src/main/resources/log.txt";

	static {
		// 创建 + 清空文件内容
		File file = new File(logAddr);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void log(String log) {
		System.out.println('\n' + log);
		try (FileOutputStream fos = new FileOutputStream(logAddr);) {
			fos.write(log.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getLog() {
		StringBuilder builder = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(logAddr)) {
			int data;
			while ((data = fis.read()) != -1) {
				builder.append((char) data);
			}
			return builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
