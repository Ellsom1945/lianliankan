package top.ysccx.myfirstapp;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class GameView extends View {
    /**
     * 游戏逻辑的实现类
     */
    private GameService gameService;
    /**
     * 保存当前已经被选中的方块
     */
    private Piece selectedPiece;
    /**
     * 连接信息对象
     */
    private LinkInfo linkInfo;
    /**
     * 画笔Paint
     */
    private Paint paint;
    /**
     * 选中标识的图片对象
     */
    private Bitmap selectImage;

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        // 设置连接线的颜色
        this.paint.setColor(Color.rgb(88,203,162));
        this.paint.setTextSize(34);
        // 设置连接线的粗细
        this.paint.setStrokeWidth(3);
        // 初始化被选中的图片
        //this.selectImage = ImageUtil.getSelectImage(context);
    }
    public void setSelectImage(Bitmap bitmap){
        this.selectImage = bitmap;
    }
    /**
     * 设置连接信息对象
     *
     * @param linkInfo
     */
    public void setLinkInfo(LinkInfo linkInfo) {
        this.linkInfo = linkInfo;
    }

    /**
     * 设置当前选中方块
     *
     * @param piece
     */
    public void setSelectedPiece(Piece piece) {
        this.selectedPiece = piece;
    }

    /**
     * 设置游戏逻辑的实现类
     *
     * @param gameService
     */
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * 绘制图形的方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.gameService == null)
            return;
        Piece[][] pieces = gameService.getPieces();
        if (pieces != null) {
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] != null) {
                        Piece piece = pieces[i][j];
                        Bitmap bm = piece.getPieceImage().getImage();
                        canvas.drawBitmap(bm,piece.getBeginX(),piece.getBeginY(),null);
                    }
                }
            }
        }
        if (this.linkInfo != null) {
            // 绘制连接线
            drawLine(this.linkInfo, canvas);
            // 处理完后清空linkInfo对象
            this.linkInfo = null;
        }
        // 画选中标识的图片
        if (this.selectedPiece != null) {
            canvas.drawBitmap(this.selectImage, this.selectedPiece.getBeginX(),
                    this.selectedPiece.getBeginY(), null);
        }
    }

    /**
     * 根据LinkInfo绘制连接线的方法。
     *
     * @param linkInfo
     *            连接信息对象
     * @param canvas
     *            画布
     */
    private void drawLine(LinkInfo linkInfo, Canvas canvas) {
        // 获取LinkInfo中封装的所有连接点
        List<Point> points = linkInfo.getLinkPoints();
        // 依次遍历linkInfo中的每个连接点
        for (int i = 0; i < points.size() - 1; i++) {
            // 获取当前连接点与下一个连接点
            Point currentPoint = points.get(i);
            Point nextPoint = points.get(i + 1);
            // 绘制连线
            canvas.drawLine(currentPoint.x, currentPoint.y, nextPoint.x,
                    nextPoint.y, this.paint);
        }
    }

    // 开始游戏方法
    public void startGame() {
        this.gameService.start();
        this.postInvalidate();
    }
}
