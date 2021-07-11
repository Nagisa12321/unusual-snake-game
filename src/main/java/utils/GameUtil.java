package utils;

import model.Game;
import org.graalvm.compiler.lir.alloc.lsra.LinearScan;

import java.util.List;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 23:41
 */
public class GameUtil {

	// 0 up
	// 1 down
	// 2 left
	// 3 right
	public static int reserveDirection(int direction) {
		if (direction == 0)
			return 1;
		if (direction == 1)
			return 0;
		if (direction == 2)
			return 3;
		if (direction == 3)
			return 2;
		return -1;
	}

	// 判断向着direction移动会不会撞墙
	public static boolean hitWall(int direction, int[] pos) {
		if (direction == 0 && pos[0] == 0)
			return true;
		if (direction == 1 && pos[0] == Game.MAP_HEIGHT - 1)
			return true;
		if (direction == 2 && pos[1] == 0)
			return true;
		if (direction == 3 && pos[1] == Game.MAP_WIDTH - 1)
			return true;
		return false;
	}

	// 判断向着direction会不会撞到食物?
	public static boolean hitFood(int direction, int[] head, int[] food) {
		return posEquals(nextPos(direction, head), food);
	}

	// 判断两个位置是否相等
	private static boolean posEquals(int[] p1, int[] p2) {
		return p1[0] == p2[0] && p1[1] == p2[1];
	}

	// 下一个位置
	public static int[] nextPos(int direction, int[] pos) {
		if (direction == 0)
			return new int[]{pos[0] - 1, pos[1]};
		if (direction == 1)
			return new int[]{pos[0] + 1, pos[1]};
		if (direction == 2)
			return new int[]{pos[0], pos[1] - 1};
		if (direction == 3)
			return new int[]{pos[0], pos[1] + 1};
		return new int[2];
	}

	// 食物/蛇 是否会撞到蛇
	public static boolean hitSnake(int direction, int[] pos, List<int[]> body) {
		for (int[] p : body) {
			if (posEquals(pos, p))
				return true;
		}
		return false;
	}
}
