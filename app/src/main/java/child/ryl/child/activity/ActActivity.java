package child.ryl.child.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import child.ryl.child.R;

/**
 * 属性动画的应用
 * http://blog.csdn.net/smartbetter/article/details/54708200
 * <p>
 * VectorDrawable，矢量图动画。使用需要添加兼容库，在app的build.gradle文件相关节点下添加：
 * android {
 * defaultConfig {
 * vectorDrawables.useSupportLibrary = true
 * }
 * }
 */
public class ActActivity extends Activity implements View.OnClickListener {
    private ImageView image;
    private ImageView imageC;
    private ImageView imageF;
    private ImageView imageK;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        initView();
        initDate();
    }

    private void initDate() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myButton:
                //显示ValueAnimator生成出来的数值
                d();
                //自定义数值生成器
//                e();
                //ValueAnimator总结
                f();
                break;
            case R.id.imageM:
//                //作用单个属性的动画实现
//                a();
//                //xml文件配合代码实现属性动画
//                b();
//                //多个属性动画，动画同时播放
//                c1();
//                //多个属性动画，按顺序播放
//                c2();
//                //多个属性动画,有的按顺序
                c3();
                break;
            case R.id.imageK:
                g(view);
                break;
            case R.id.imageC:
                h();
                break;
            case R.id.imageF:
                i();
                break;
        }
    }

    /**
     * 轨迹动画关键的配置就是 objectAnimator 中 androd:propertyName=”pathData”
     * 和 androd:valueType=”pathType”属性。这里我们实现五角星向五边形的变换动画。
     */
    private void i() {
        Drawable drawable = imageF.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            } else {
                ((Animatable) drawable).start();
            }
        }
    }

    /**
     * 轨迹动画:关键的配置就是 objectAnimator 中
     * androd:propertyName=”trimPathStart” 属性。
     */
    private void h() {
        Drawable drawable = imageC.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            } else {
                ((Animatable) drawable).start();
            }
        }
    }

    /**
     * VectorDrawable创建属性动画
     * Vector（矢量图） 对比 Bitmap（位图）
     * 绘制效率	Vector依赖于CUP计算，适合图像简单的情况。Bitmap可借助于GPU加速，适合图像复杂的情况。
     * 适用情况	Vector适用于ICON、Button、ImageView的小图片，或者需要动画效果时。Bitmap由于在GPU中有缓存功能，所以Bitmap不能做频繁的重绘。
     * 加载速度	SVG快于PNG，但PNG有硬件加速，平均下来加载速度的提升弥补了绘制的速度缺陷。
     * <p>
     * VectorDrawable，矢量图动画。使用需要添加兼容库，在app的build.gradle文件相关节点下添加：
     * android {
     * defaultConfig {
     * vectorDrawables.useSupportLibrary = true
     * }
     * }
     */
    private void g(View view) {
//        ImageView imageView = (ImageView) view;
//        Drawable drawable = imageView.getDrawable();
        Drawable drawable = imageK.getDrawable();
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            } else {
                ((Animatable) drawable).start();
            }
        }
    }

    /**
     * ValueAnimator总结
     */
    private void f() {
        //官方文档：https://developer.android.google.cn/reference/android/animation/package-summary.html
//        ValueAnimator\ObjectAnimator
//        AnimatorUpdateListener\AnimatorListenerAdapter   做监听器的
//        PropertyValuesHolder\AnimatorSet    控制动态集合的显示效果、顺序、流程的
//        TypeEvaluators          值计算器
//        Interpolators           插值器
    }

    /**
     * 自定义数值生成器
     */
    private void e() {
        ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
            /**
             * 通过evaluate返回各种各样的值
             * @param fraction 时间因子，0-1之间变化的数值
             * @param startValue
             * @param endValue
             * @return
             */
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                return null;// TODO: 2017/4/7 0007
            }
        });
    }

    /**
     * ValueAnimator不会作用于任何一个属性，简单来说，它就是“数值发生器”，
     * 实际上在属性动画中，产生每一步的具体动画实现效果都是通过ValueAnimator计算出来的。
     * ObjectAnimator是继承自ValueAnimator的，ValueAnimator并没有ObjectAnimator使用的广泛。
     * ValueAnimator通过动画已经继续的时间和总时间的比值产生一个0～1点时间因子，
     * 有了这样的时间因子，经过相应的变换，就可以根据startValue和endValue来生成中间相应的值。
     */
    private void d() {
        //（这里没有指定插值器，默认线性增长）：
        ValueAnimator animator = ValueAnimator.ofInt(0, 60);
        animator.setDuration(60000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                myButton.setText("" + value);
            }
        });
        animator.start();
    }

    /**
     * 多个属性动画,有的按顺序
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void c3() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(image, "translationX", 0f, 300f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(image, "translationY", 0f, 500f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(image, "translationY", 500f, 0f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(image, "rotation", 0f, 720f);
        AnimatorSet set = new AnimatorSet();
        set.play(animator1).with(animator2);//同时播放
        set.play(animator4).after(animator1);
        set.play(animator3).before(animator4);//之后播放，before
        set.setDuration(2000).start();
        animatorSetListener(set);
    }

    /**
     * 多个属性动画，按顺序播放
     */
    private void c2() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(image, "translationX", 0f, 200f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(image, "translationY", 0f, 200f);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator1, animator2);
        set.setDuration(1000).start();
    }

    /**
     * 多个属性动画，监听事件
     *
     * @param set
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void animatorSetListener(AnimatorSet set) {
        set.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animator) {
                Log.i("addPauseListener", "onAnimationPause");
            }

            @Override
            public void onAnimationResume(Animator animator) {
                Log.i("addPauseListener", "onAnimationResume");
            }
        });
    }

    /**
     * 多个属性动画，动画同时播放
     */
    private void c1() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(image, "translationX", 0f, 200f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(image, "translationY", 0f, 200f);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator1, animator2);
        set.setDuration(1000).start();
    }


    /**
     * a.同样属性动画也可以在res/animator文件夹下进行创建anim.xml
     * 并配置（多个objectAnimator可以用set进行包裹）：
     * b.调用AnimatorInflater的loadAnimator来将XML动画文件加载进来，
     * 然后再调用setTarget()方法将这个动画设置到某一个对象上面，
     * 最后再调用start()方法启动动画
     */
    private void b() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim);
        animator.setTarget(image);
        animator.start();
        animatorListener(animator);
    }

    /**
     * 单个属性动画，监听事件
     *
     * @param animator
     */
    private void animatorListener(Animator animator) {
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.i("animatorListener", "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i("animatorListener", "onAnimationEnd");
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                Log.i("animatorListener", "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.i("animatorListener", "onAnimationRepeat");
            }
        });
    }

    /**
     * 参数1：操纵的控件
     * 参数2：操纵的属性，常见的有偏移translationX，translationY，绝对值X、Y，3d旋转rotation、
     * 水平竖直方向旋转rotationX、rotationY，水平竖直缩放scaleX、scaleY，透明度alpha
     * 参数3、4：变化范围
     * duration：时长
     */
    private void a() {
        ObjectAnimator.ofFloat(image, "translationX", 0f, 200f)
                .setDuration(1000).start();
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.imageM);
        image.setOnClickListener(this);
        imageC = (ImageView) findViewById(R.id.imageC);
        imageC.setOnClickListener(this);
        imageF = (ImageView) findViewById(R.id.imageF);
        imageF.setOnClickListener(this);
        imageK = (ImageView) findViewById(R.id.imageK);
        imageK.setOnClickListener(this);
        myButton = (Button) findViewById(R.id.myButton);
        myButton.setOnClickListener(this);
    }
}
