package com.bai.myapplication.View;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bai.myapplication.R;
import com.bai.myapplication.Util.OnDragViewClickListener;


/**
 * Created by bailing on 2018/8/18.
 * Description：拖动view
 */


public class DragView extends FrameLayout {

    private float mStartX;
    private float mStartY;
    private int rawX;
    private int rawY;
    private int lastX;
    private int lastY;
    private int pHeight;
    private int pWidth;
    private long mLastTime ;
    private ViewGroup parent;

    private ImageView iv_pic,iv_close;
    private OnDragViewClickListener dragViewClickListener;
    private boolean flag=false;

    public void SetClickListener(OnDragViewClickListener dragViewClickListener){
        this.dragViewClickListener = dragViewClickListener;
    }

    public DragView(Context context) {
        this(context,null);

    }


    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }


    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dragview, this);

        iv_close = view.findViewById(R.id.iv_close);
        iv_pic = view.findViewById(R.id.iv_pic);

    }

    //判断是否触摸view
    private boolean isTouchInView(ImageView view, float xAxis, float yAxis) {
        if (view== null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (yAxis >= top && yAxis <= bottom && xAxis >= left
                && xAxis <= right) {
            return true;
        }
        return false;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        rawX = (int) event.getRawX();
        rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
                //获取相对View的坐标，即以此View左上角为原点
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                if (isTouchInView(iv_close,rawX,rawY)){
                    flag = true;
                }
                mLastTime = System.currentTimeMillis();
                lastX=rawX;
                lastY=rawY;
                if(getParent()!=null){
                    parent= (ViewGroup) getParent();
                    pHeight=parent.getHeight();
                    pWidth=parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:   //捕获手指触摸移动动作
                int dx=rawX-lastX;
                int dy=rawY-lastY;
                float x=getX()+dx;
                float y=getY()+dy;
                //判断是否到边界
                x = x < 0 ? 0 : x > pWidth - getWidth() ? pWidth - getWidth() : x;
                y = getY() < 0 ? 0 : getY() + getHeight() > pHeight ? pHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX=rawX;
                lastY=rawY;
                break;
            case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
                if(System.currentTimeMillis() - mLastTime < 800){
                    if(Math.abs(mStartX- event.getRawX() )< 10.0 && Math.abs(mStartY - event.getRawY()) < 10.0){
                        //处理点击的事件
                        if (flag){
                            dragViewClickListener.onDragViewListener("delete","");
                        }else
                            dragViewClickListener.onDragViewListener("pic","");
                    }
                }
                flag = false;
                break;
        }
        return true;
    }

}