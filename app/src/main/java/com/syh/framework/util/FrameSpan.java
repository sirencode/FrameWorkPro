package com.syh.framework.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Created bg shenyonghe on 2018/6/5.
 */
public class FrameSpan extends ReplacementSpan {

    private final Paint paint;
    private int width;
    private int color;
    private int textSize;

    public FrameSpan(int textColor, int size) {
        this.textSize = size;
        this.color = textColor;
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.color);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        width = (int) paint.measureText(text, start, end);
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        paint.setTextSize(this.textSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(color);
        RectF rect = new RectF((int) x + 6, top + 6, (int) x + width - 6, bottom - 6);
        canvas.drawRoundRect(rect, 5, 5, this.paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float topT = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottomT = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (rect.centerY() - topT / 2 - bottomT / 2);//基线中间点的y轴计算公式
        canvas.drawText(text.toString().substring(0, 2), rect.centerX(), baseLineY, paint);
    }
}

