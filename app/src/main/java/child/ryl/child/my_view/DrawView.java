package child.ryl.child.my_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import child.ryl.child.R;


/**
 * http://www.2cto.com/kf/201404/291487.html
 * http://crackren.iteye.com/blog/789324
 */
public class DrawView extends View {
    private int mX = 10;
    private int mY = 15;
    private int no = 1;
    private float moveX = 0;//上一次移动的距离
    private float moveXX = 0;

    public DrawView(Context context) {
        super(context);
//        setOnClickListener(this);
//        setOnLongClickListener(this);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setOnLongClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (no == 1) {
            if (j == 0) {
                canvas.translate(moveX, 0);//把坐标系向右平移
            } else {
                canvas.translate(moveXX, 0);
            }
        }


        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawOval(new RectF(0, 0, getWidth(), getHeight()), paint);


//        canvas.drawRect(new Rect(mX * no, mY * no, getWidth()
//                +100, getHeight() + mY * no), paint);
//        canvas.drawOval(0,0,getWidth(),getHeight(),paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawRect(new Rect(10, 10, 100, 30), paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText("hello", 10 + mX * no, 50 + mY * no, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        if (k != 0) {
            Matrix m = new Matrix();
            /*
            以点px,py为原点缩放 >=0   1为正常大小
            如果是负数,图形就会翻转
            如果没设置原点坐标,默认以0,0点缩放(如果发现图片不见了,检查一下是不是翻转出了屏幕)
            例子:setScale(-0.5f, 1,180, 120);  //左右翻转并缩放到一半大小
             */
            m.postScale((float) 1.5 * k, (float) 1.5 * k);
            m.postRotate(10 + 10 * k);//旋转
            //是图片移动到某一个位置
            m.postTranslate(100 + 20 * k, 100 + 20 * k);
            //以点px,py为原点倾斜如果没有设置原点,则以0,0点为原点.
            //例子:setSkew(0, 1, 180, 120); //Y 方向拉伸
            canvas.drawBitmap(BitmapFactory.decodeResource(
                    getResources(), R.mipmap.ic_launcher), m, paint);
        } else {
            canvas.drawBitmap(bitmap, 10 + mX * no, 60 + mY * no, paint);
        }


        RectF re2 = new RectF(70, 200, 350, 260);
        // 绘制圆角矩形
        canvas.drawRoundRect(re2, 15, 15, paint);
        RectF re21 = new RectF(90, 140, 250, 170);
        // 绘制椭圆
        canvas.drawOval(re21, paint);

        Path path3 = new Path();
        path3.moveTo(90, 340);
        path3.lineTo(150, 340);
        path3.lineTo(120, 290);
        path3.close();
        // 绘制三角形
        canvas.drawPath(path3, paint);
        Path path4 = new Path();

        //弧线
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);

        float startAngle01 = 3;
        float sweepAngle01 = 86;

        RectF rect = new RectF(70, 280, 230, 540);
        canvas.drawArc(rect, startAngle01, sweepAngle01, true, paint);
        RectF rect2 = new RectF(270, 280, 430, 540);
        canvas.drawArc(rect2, startAngle01, sweepAngle01, false, paint);


        // ----------设置渐变器后绘制----------
        // 为Paint设置渐变器
        Shader mShader = new LinearGradient(0, 0, 40, 60, new int[]{
                Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}, null,
                Shader.TileMode.REPEAT);
        paint.setShader(mShader);


        // 绘制圆形
        canvas.drawCircle(200, 40, 120, paint);
//        if (no==1){
//            canvas.translate(getWidth() / 4, getHeight() /4);//把坐标系向右下角平移
//        }
        paint.setStyle(Paint.Style.FILL);


        // 设置阴影
        paint.setShadowLayer(45, 10, 10, Color.GRAY);
        path4.moveTo(106, 360);
        path4.lineTo(134, 360);
        path4.lineTo(150, 392);
        path4.lineTo(120, 420);
        path4.lineTo(90, 392);
        path4.close();
        // 绘制五角形
        canvas.drawPath(path4, paint);
//        canvas.drawOval(new RectF(0, 0, getWidth(), getHeight()), paint);

        // 绘制正方形
        canvas.drawRect(170, 180, 330, 340, paint);

    }

    private int k = 0;

    private float x0 = 0;
    private float x1 = 0;
    private int j = 0;

    public void setIcon() {
        k++;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (j == 0) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x0 = event.getX();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //当手指按下的时候
                x1 = event.getX();
                moveX = x1 - x0;
                invalidate();
//            y1 = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                j = 1;
                moveXX = moveX;
            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x0 = event.getX();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //当手指按下的时候
                x1 = event.getX() + moveX;
                moveXX = x1 - x0;
                no = 1;
                invalidate();
//            y1 = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                moveX = moveXX;
            }
        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if(y1 - y2 > 50) {
//                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
//            } else if(y2 - y1 > 50) {
//                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
//            } else if(x1 - x2 > 50) {
//                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
//            } else if(x2 - x1 > 50) {
//                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
//            }
//        }
        return true;
    }


}
