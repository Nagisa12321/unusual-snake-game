package domain.game;

import domain.Toy;
import utils.GameUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/11 22:25
 */
public class Snake implements Toy {
	// 蛇的身体组成的队列
	private final Queue<int[]> body;

	// 蛇头的方向
	// 0 up
	// 1 down
	// 2 left
	// 3 right
	private int direction;

	// 蛇头的坐标
	private int[] head;

	public Snake() {
		// 默认构造长度为4的
		body = new LinkedList<>();
		body.offer(new int[]{0, 0});
		body.offer(new int[]{0, 1});
		body.offer(new int[]{0, 2});
		head = new int[]{0, 3};
		body.offer(head);

		// 方向是right
		direction = 3;
	}

	// 朝着某个点的方向移动, 尾部缩短
	public void move(int direction) {
		moveAndGrow(direction);
		body.poll();
	}

	// 尾部不会缩短
	public void moveAndGrow(int direction) {
		int[] nextPos = GameUtil.nextPos(direction, head);
		body.offer(nextPos);
		head = nextPos;
		this.direction = direction;
	}

	// 获取蛇头的坐标
	public int[] getHead() {
		return new int[]{head[0], head[1]};
	}

	// 获取蛇的全身坐标
	public List<int[]> getBody() {
		return new ArrayList<>(body);
	}

	// 获得当前蛇的方向
	public int getDirection() {
		return direction;
	}
}
