package com.gaoxianglong.animationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class ImgActivity extends AppCompatActivity {

    public static final String POINTX = "POINTX";
    public static final String POINTY = "POINTY";
    private static final String TAG = "ImgActivity";
    private ImageView mImageView;

    private float pointx; // 保存传递过来的x坐标
    private float pointy; // 保存传递过来的y坐标
    private float pointXValue; // 保存计算过后的x坐标
    private float pointYValue; // 保存计算过后的y坐标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);

        init();
    }

    private void init() {
        mImageView = findViewById(R.id.imageView);

        pointx = getIntent().getIntExtra(POINTX,0); // 获取传递来的x坐标
        pointy = getIntent().getIntExtra(POINTY, 0); // 获取传递来的y坐标

        DisplayMetrics metrics = getResources().getDisplayMetrics(); // 获取当前屏幕尺寸
        // 计算传递来的坐标在屏幕的百分比
        if (pointx == 0) {
            pointXValue = 0.5f; // 当传递过来x的值为0时将要使用的X设置为0.5f = 50%
        } else {
            pointXValue = pointx / metrics.widthPixels; // 用传递的x / 当前屏幕的宽度 = 就是当前x在屏幕上的百分比
        }
        if (pointy == 0) {
            pointYValue = 0.5f; // 当传递过来y的值为0时将要使用的Y设置为0.5f
        } else {
            pointYValue = pointy / metrics.heightPixels; // 用传递的y / 当前屏幕的高度 = 就是当前y在屏幕上的百分比
        }
        Log.d(TAG, String.format("pointXValue = %s ,pointYValue = %s",pointXValue,pointYValue));

        // 使用 AnimationSet 集合动画
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1); // 实例化一个透明度动画 从完全透明到完全半透明(0,1)
        // 实例化一个缩放动画
        // x轴上：从没有到原样(0,1f) 0表示只有一个点等于是没有 1f表示原样没有任何缩放
        // y轴上：从没有到原样(0,1f)
        // x轴上相对于父布局：
        // ScaleAnimation.RELATIVE_TO_PARENT 这就是相对于父布局 就是从父布局的左上角开始(0,0)
        // pointXValue 这是计算过后x轴的百分比 从这个x坐标开始
        // y轴上相对于父布局：
        // ScaleAnimation.RELATIVE_TO_PARENT 同样的相对于父布局
        // pointYValue 这是计算过后y轴的百分比 从这个y坐标开始
        // 合在一起解释就是从 父布局的(pointXValue,pointYValue) 开始缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                ScaleAnimation.RELATIVE_TO_PARENT, pointXValue,
                ScaleAnimation.RELATIVE_TO_PARENT, pointYValue);
        animationSet.addAnimation(alphaAnimation); // 向集合动画里添加透明度动画
        animationSet.addAnimation(scaleAnimation); // 向集合动画里添加缩放动画

        animationSet.setDuration(500); // 设置这个动画开始到结束的时间(持续时间) 单位为毫秒
        animationSet.setFillAfter(true); // 设置动画结束后保持结束的状态
        mImageView.startAnimation(animationSet); // 启动动画
    }

    /**
     * 重写退出当前activity回调的方法
     * 在这里面将之前的透明度(0,1)和缩放(0,1f)颠倒
     * 实现进入当前activity和退出时的动画
     */
    @Override
    public void onBackPressed() {
        // 将这里的集合动画和上面的集合动画对比理解
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0,
                ScaleAnimation.RELATIVE_TO_PARENT, pointXValue,
                ScaleAnimation.RELATIVE_TO_PARENT, pointYValue);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        animationSet.setDuration(500);
        animationSet.setFillAfter(true);
        // 设置动画监听
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 动画开始时
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 动画结束时
                // 调用父类的返回方法
                ImgActivity.super.onBackPressed();
                // 使用这个可以消除activity切换之间的缝隙 具体效果 注释掉代码对比前后
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 动画重复时
            }
        });
        mImageView.startAnimation(animationSet); // 启动动画
    }
}
