package obsaver;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/7/12 13:47
 */
public interface Observable {

	// 注册观察者
	void register(Observer observer);

	// 通知观察者
	void notifyObservers();

	// 取消注册观察者
	void removeObserver(Observer observer);
}
