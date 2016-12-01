package com.chris.utopia.common.view;

/**
 * Created by jianjianhong on 2016/11/29.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.chris.utopia.R;
import com.chris.utopia.common.util.CommonUtil;

import static android.graphics.PorterDuff.Mode.CLEAR;

public final class DisplayLeakConnectorView extends View {
    private static final Paint iconPaint = new Paint(1);
    private static final Paint rootPaint = new Paint(1);
    private static final Paint leakPaint = new Paint(1);
    private static final Paint clearPaint = new Paint(1);
    private static final Paint ignorePaint = new Paint(1);
    private static final Paint delayPaint = new Paint(1);
    private DisplayLeakConnectorView.Type type;
    private int status;
    private Bitmap cache;
    public Context context;

    public DisplayLeakConnectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.type = DisplayLeakConnectorView.Type.NODE;
        this.status = 0;
    }

    protected void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();
        if(this.cache != null && (this.cache.getWidth() != width || this.cache.getHeight() != height)) {
            this.cache.recycle();
            this.cache = null;
        }

        if(this.cache == null) {
            this.cache = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas cacheCanvas = new Canvas(this.cache);
            float halfWidth = (float)width / 2.0F;
            float halfHeight = (float)height / 2.0F;
            float thirdWidth = (float)width / 3.0F;
            float strokeSize = CommonUtil.dipToPixels(4.0F);
            iconPaint.setStrokeWidth(strokeSize);
            rootPaint.setStrokeWidth(strokeSize);
            switch(this.type.ordinal()) {
                case 0:
                    float radiusClear = halfWidth - strokeSize / 2.0F;
                    cacheCanvas.drawRect(0.0F, 0.0F, (float)width, radiusClear, rootPaint);
                    cacheCanvas.drawCircle(0.0F, radiusClear, radiusClear, clearPaint);
                    cacheCanvas.drawCircle((float)width, radiusClear, radiusClear, clearPaint);
                    cacheCanvas.drawLine(halfWidth, 0.0F, halfWidth, halfHeight, rootPaint);
                    cacheCanvas.drawLine(halfWidth, halfHeight, halfWidth, (float)height, iconPaint);
                    cacheCanvas.drawCircle(halfWidth, halfHeight, halfWidth, iconPaint);
                    //cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, clearPaint);
                    if(status == 0) {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, rootPaint);
                    }else if(status == 1) {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, ignorePaint);
                    }else if(status == 2) {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, delayPaint);
                    }else {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, clearPaint);
                    }
                    break;
                case 1:
                    cacheCanvas.drawLine(halfWidth, 0.0F, halfWidth, (float)height, iconPaint);
                    cacheCanvas.drawCircle(halfWidth, halfHeight, halfWidth, iconPaint);

                    if(status == 0) {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, rootPaint);
                    }else if(status == 1) {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, ignorePaint);
                    }else if(status == 2) {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, delayPaint);
                    }else {
                        cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, clearPaint);
                    }
                    break;
                default:
                    cacheCanvas.drawLine(halfWidth, 0.0F, halfWidth, halfHeight, iconPaint);
                    cacheCanvas.drawCircle(halfWidth, halfHeight, thirdWidth, leakPaint);
            }
        }

        canvas.drawBitmap(this.cache, 0.0F, 0.0F, (Paint)null);
    }

    public void setType(DisplayLeakConnectorView.Type type, int status) {
        if(type != this.type || this.status != status) {
            this.type = type;
            this.status = status;
            if(this.cache != null) {
                this.cache.recycle();
                this.cache = null;
            }

            this.invalidate();
        }

    }

    static {
        iconPaint.setColor(CommonUtil.getApplicationContext().getResources().getColor(R.color.divider_text_dark_color));
        rootPaint.setColor(CommonUtil.getApplicationContext().getResources().getColor(R.color.primary));
        leakPaint.setColor(CommonUtil.getApplicationContext().getResources().getColor(R.color.accent));
        ignorePaint.setColor(CommonUtil.getApplicationContext().getResources().getColor(R.color.today_task_ignore));
        delayPaint.setColor(CommonUtil.getApplicationContext().getResources().getColor(R.color.today_task_delay));
        clearPaint.setColor(0);
        clearPaint.setXfermode(new PorterDuffXfermode(CLEAR));
    }

    public static enum Type {
        START,
        NODE,
        END;

        private Type() {
        }
    }
}
