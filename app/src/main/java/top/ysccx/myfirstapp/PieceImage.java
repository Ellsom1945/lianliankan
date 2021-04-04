package top.ysccx.myfirstapp;

import android.graphics.Bitmap;


public class PieceImage {
    /**
     * 图片
     */
    private Bitmap image;
    /**
     * 图片资源ID
     */
    private int imageId;

    /**
     * 构造函数
     *
     * @param image
     *            图片
     * @param imageId
     *            图片ID
     */
    public PieceImage(Bitmap image, int imageId) {
        super();
        this.image = image;
        this.imageId = imageId;
    }

    /**
     * @return 图片
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * 设置图片
     *
     * @param image
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * @return 图片ID
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * 设置图片ID
     *
     * @param imageId
     */
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

