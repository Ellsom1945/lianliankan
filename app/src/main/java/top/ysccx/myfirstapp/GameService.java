package top.ysccx.myfirstapp;


public interface GameService {
	/**
	 * 控制游戏开始的方法
	 */
	public void start();

	/**
	 * 定义一个接口方法, 用于返回一个二维数组
	 * 
	 * @return 存放方块对象的二维数组
	 */
	public Piece[][] getPieces();

	/**
	 * 判断参数Piece[][]数组中是否还存在非空的Piece对象
	 * 
	 * @return 如果还剩Piece对象返回true, 没有返回false
	 */
	public boolean hasPieces();

	public void shuffle();
	/**
	 * 根据鼠标的x座标和y座标, 查找出一个Piece对象
	 * 
	 * @param touchX
	 *            鼠标点击的x座标
	 * @param touchY
	 *            鼠标点击的y座标
	 * @return 返回对应的Piece对象, 没有返回null
	 */
	public Piece findPiece(float touchX, float touchY);

	/**
	 * 判断两个Piece是否可以相连, 可以连接, 返回LinkInfo对象
	 * 
	 * @param p1
	 *            第一个Piece对象
	 * @param p2
	 *            第二个Piece对象
	 * @return 如果可以相连，返回LinkInfo对象, 如果两个Piece不可以连接, 返回null
	 */
	public LinkInfo link(Piece p1, Piece p2);
}
