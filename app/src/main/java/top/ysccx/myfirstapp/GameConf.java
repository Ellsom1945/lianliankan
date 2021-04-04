package top.ysccx.myfirstapp;

import android.content.Context;
import android.util.DisplayMetrics;

public class GameConf {
	public static int PIECE_WIDTH = 45;
	public static int PIECE_HEIGHT = 45;
	public static int DEFAULT_TIME = 30;
	private int xSize;
	private int ySize;
	private int beginImageX;
	private int beginImageY;
	private long gameTime;
	private Context context;
	private int screenWidth;
	private int screenHeight;

	public GameConf(int xSize, int ySize, int screenWidth, int screenHeight,
			long gameTime, Context context) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.gameTime = gameTime;
		this.context = context;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		DisplayMetrics dm= new DisplayMetrics();
        float scale = context.getResources().getDisplayMetrics().density;
        PIECE_WIDTH = (int) (PIECE_WIDTH * scale + 0.5f);
        PIECE_HEIGHT = (int) (PIECE_HEIGHT * scale + 0.5f);
        setBeginImage();
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public void setBeginImage(){
        this.beginImageX = (screenWidth-PIECE_WIDTH*xSize)/2;
		this.beginImageY = (screenHeight-500-PIECE_HEIGHT*ySize)/2;
    }

	/**
	 * @return 游戏的总时间
	 */
	public long getGameTime() {
		return gameTime;
	}

	/**
	 * @return Piece[][]数组第一维的长度
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * @return Piece[][]数组第二维的长度
	 */
	public int getYSize() {
		return ySize;
	}

	/**
	 * @return Board中第一张图片出现的x座标
	 */
	public int getBeginImageX() {
		return beginImageX;
	}

	/**
	 * @return Board中第一张图片出现的y座标
	 */
	public int getBeginImageY() {
		return beginImageY;
	}

	/**
	 * @return 应用上下文
	 */
	public Context getContext() {
		return context;
	}
}
