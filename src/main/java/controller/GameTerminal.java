package controller;

import model.Game;
import utils.LogUtil;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 15:26
 */
public class GameTerminal implements Runnable {

	// 对游戏模型的引用
	private final Game game;

	// 开头的标识
	private final String name;

	// 终端状态
	private volatile boolean running;

	public GameTerminal(String name) {
		game = Game.getInstance();
		this.name = name;
		this.running = true;
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.print(name + " > ");
		while (running && scanner.hasNext()) {
			String line = scanner.nextLine();
			if (line.equals("map")) {
				int[][] map = game.getMap();
				if (map == null) {
					System.out.println("the game is not started :)");
					System.out.print(name + " > ");
					continue;
				}
				System.out.println("the game map is like this: ");
				for (int i = 0; i < Game.MAP_HEIGHT; i++) {
					System.out.println(Arrays.toString(map[i]));
				}
			} else if (line.equals("log")) {
				System.out.println(LogUtil.getLog());
			} else if (line.equals("state")) {
				System.out.print("now the game state is ");
				int state = game.getState();
				if (state == 0) System.out.println("PLAYER_WAITING");
				if (state == 1) System.out.println("STARTED");
				if (state == 2) System.out.println("START_WAITING");
			} else if (line.equals("player")) {
				System.out.println("food player: " + game.getFoodPlayer());
				System.out.println("snake player: " + game.getSnakePlayer());
			} else if (line.equals("help")) {
				System.out.println(" === Unusual-Snake By JT-CHEN === ");
				System.out.println("\t[state]\t- get the game state.");
				System.out.println("\t[map]\t- get the game map.");
				System.out.println("\t[log]\t- to get the server log.");
				System.out.println("\t[player]\t- to get the player info.");
			} else {
				System.out.println("try [help] to get help :)");
			}

			System.out.print(name + " > ");
		}
	}

	public void stop() {
		running = false;
	}
}
