package chukong.bawei.com.duodianchukong;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    private int imageX = 0;//计算图片x坐标
    private int imageY = 0;//计算图片y坐标
    private int imageWidth = 0;
    private int imageHeight = 0;
    private SurfaceHolder holder = null;
    private int sWidth = 0;
    private int sHeight = 0;
    private Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.sWidth = super.getWindowManager().getDefaultDisplay().getWidth();
        this.sHeight = super.getWindowManager().getDefaultDisplay().getHeight();
        this.bitmap = BitmapFactory.decodeResource(super.getResources(), R.drawable.bb);
        this.imageWidth = bitmap.getWidth();
        this.imageHeight = bitmap.getHeight();

        this.imageX = (this.sWidth - this.imageWidth) / 2;
        this.imageY = (this.sHeight - this.imageHeight) / 2;

        setContentView(new Myfaceview(this));
        getSupportActionBar().hide();
    }


    private class Myfaceview extends SurfaceView implements SurfaceHolder.Callback {

        public Myfaceview(Context context) {
            super(context);
            MainActivity.this.holder = super.getHolder();
            MainActivity.this.holder.addCallback(this);
            super.setFocusable(true);//获得焦点，进行触摸事件

        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            setImage(1, 440, 652);
        }


        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

    private void setImage(float scale, int width, int heigth) {
        Canvas canvas = holder.lockCanvas();//画布
        //画笔
        Paint paint = new Paint();
        canvas.drawRect(0, 0, sWidth, sHeight, paint);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap target = Bitmap.createBitmap(bitmap, 0, 0, width, heigth, matrix, true);
        imageHeight = target.getHeight();
        imageWidth = target.getWidth();
        imageX = (this.sWidth - this.imageWidth) / 2;
        imageY = (this.sHeight - this.imageHeight) / 2;
        canvas.translate(imageX, imageY);//平移指定位置
        canvas.drawBitmap(target, matrix, paint);
        holder.unlockCanvasAndPost(canvas);//解锁，提交图片

    }
    //重写onTouchEvent

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointCount = event.getAction();
        if (pointCount == 2) {
            float pointA = event.getY(0);
            float pointB = event.getY(1);
            if (pointA < pointB) {
                float temp = pointA;
                pointA = pointB;
                pointB = temp;
            }
            if (!(event.getAction() == MotionEvent.ACTION_UP)) {
                float scale = this.getScale(pointA,pointB);
                setImage(scale, 440, 652);
            }
        }
        return super.onTouchEvent(event);

    }

    private float getScale(float pointA, float pointB) {
        float scale = pointA/pointB;
        return scale;
    }
}
