package cn.leaqi.drawerapp.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import cn.leaqi.drawerapp.R;

/**
 * ImageView 图片圆角
 */
public class RadiusImage extends AppCompatImageView {

    private int leftTopRadius = 0;
    private int rightTopRadius = 0;
    private int rightBottomRadius = 0;
    private int leftBottomRadius = 0;

    private Paint paint;
    private Paint paint2;

    public RadiusImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public RadiusImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RadiusImage(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attrArr = context.obtainStyledAttributes(attrs, R.styleable.RadiusImage);
            int allRadius = attrArr.getDimensionPixelSize(R.styleable.RadiusImage_radius, 0);
            if(allRadius > 0){
                leftTopRadius = allRadius;
                rightTopRadius = allRadius;
                leftBottomRadius = allRadius;
                rightBottomRadius = allRadius;
            }
            attrArr.recycle();
        }

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    @Override
    public void draw(Canvas canvas) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmap);
            super.draw(canvas2);
            if(leftTopRadius > 0)drawLeftTop(canvas2);
            if(rightTopRadius > 0)drawRightTop(canvas2);
            if(leftBottomRadius > 0)drawLeftBottom(canvas2);
            if(rightBottomRadius > 0)drawRightBottom(canvas2);
            canvas.drawBitmap(bitmap, 0, 0, paint2);
            bitmap.recycle();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    private void drawLeftTop(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, leftTopRadius);
        path.lineTo(0, 0);
        path.lineTo(leftTopRadius, 0);
        path.arcTo(new RectF(0, 0, leftTopRadius * 2, leftTopRadius * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightTop(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), rightTopRadius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - rightTopRadius, 0);
        path.arcTo(new RectF(getWidth() - rightTopRadius * 2, 0, getWidth(), rightTopRadius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLeftBottom(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - leftBottomRadius);
        path.lineTo(0, getHeight());
        path.lineTo(leftBottomRadius, getHeight());
        path.arcTo(new RectF(0, getHeight() - leftBottomRadius * 2, leftBottomRadius * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightBottom(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - rightBottomRadius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - rightBottomRadius);
        path.arcTo(new RectF(getWidth() - rightBottomRadius * 2, getHeight() - rightBottomRadius * 2, getWidth(), getHeight()), -0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

}