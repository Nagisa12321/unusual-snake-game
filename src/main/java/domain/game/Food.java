package domain.game;

import domain.Toy;
import model.Game;
import utils.GameUtil;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 22:58
 */
public class Food implements Toy {
	// 当前食物的方向
	// 0 up
	// 1 down
	// 2 left
	// 3 right
	private int direction;

	// 当前食物的位置
	private int[] pos;

	public Food() {
		direction = 2;
		pos = new int[]{Game.MAP_HEIGHT - 1, Game.MAP_WIDTH - 1};
	}

	// 获得当前食物的方向
	public int getDirection() {
		return direction;
	}

	// 获得当前食物的位置
	public int[] getPos() {
		return pos;
	}

	// 移动一格子
	public void move(int direction) {
		pos = GameUtil.nextPos(direction, pos);
		this.direction = direction;
	}
}
