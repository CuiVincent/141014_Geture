package com.cui.view.geture;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.cui.view.util.ScreenUtil;

public class GestureObserve extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    private static final String TAG = "GestureObserve";
    /** 手势的类别 **/
    public enum GESTURE_TYPE {NONE, HORIZONTAL_SCORLL,VERTICAL_SCORLL};
    /** 手势识别区域偏移量 **/
    private int mIdentifiableAreasOffset;
    /** 手势识别区域起点 **/
    private int mIdentifiableAreasO;
    /** 手势识别区域高度 **/
    private int mIdentifiableAreasHeightY;
    /** 手势识别区域宽度 **/
    private int mIdentifiableAreasWidhtX;
    /** 手势识别区域左右分界线 **/
    private int mIdentifiableAreasMiddleBoundary;
    /** 符合条件的上次的MotionEvent记录 **/
    private static MotionEvent mLastMotionEvent;

    private GESTURE_TYPE lastGestureType = GESTURE_TYPE.NONE;

    private final int IDENTIFIABLE_AREAS_PADDING_PROPORTION = 20;
    /**
     * 30度直角三角形，短直角边与长直角边比率
     */
    private final float RATIO_30_DEGREE_SQUARE = 0.577f;

    private Context mContext;
    private View mView;
    /**
     * 坐标位移最小阀值
     */
    private final int FLING_MIN_DISTANCE = 30;

    private onGestureListener mListener;

    private GestureDetector gestureDetector;

    public  void init(){
        gestureDetector = new GestureDetector(this);
    }
    public GestureObserve(Context context,View view, onGestureListener listener )   {
        mContext = context;
        mListener = listener;
        mView = view;
        mView.setOnTouchListener(this);
        mView.setLongClickable(true);
        initIdentifiablAres();
    }

    /**
     * 初始化手势识别区域关键坐标
     */
    private void initIdentifiablAres() {
        /** 手势识别区域起点 **/
        mIdentifiableAreasO = 0;
        /** 手势识别区域 偏移量 **/
        mIdentifiableAreasOffset = ScreenUtil.getScreenWidth(null) / IDENTIFIABLE_AREAS_PADDING_PROPORTION;
        /** 手势识别区域高度 **/
        mIdentifiableAreasHeightY = ScreenUtil.getScreenWidth(null);
        /** 手势识别区域宽度 **/
        mIdentifiableAreasWidhtX = ScreenUtil.getScreenHeight(null);
        /** 手势识别区域左右分界线 **/
        mIdentifiableAreasMiddleBoundary = ScreenUtil.getScreenHeight(null) / 2;

        System.out.println(TAG+ ":Offset:" + mIdentifiableAreasOffset + ":HeightY:" + mIdentifiableAreasHeightY + ":WidhtX:"
                + mIdentifiableAreasWidhtX + ":MiddleBoundary:" + mIdentifiableAreasMiddleBoundary);
    }

    /**
     * Touch up时触发，e为up时的MotionEvent
     * 
     * @param e
     * @return
     * @see android.view.GestureDetector.SimpleOnGestureListener#onSingleTapUp(android.view.MotionEvent)
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        System.out.println("onSingleTapUp e:"+e);
        return super.onSingleTapUp(e);
    }

    /**
     * 长触
     * @param e
     * @see android.view.GestureDetector.SimpleOnGestureListener#onLongPress(android.view.MotionEvent)
     */
    @Override
    public void onLongPress(MotionEvent e) {
        System.out.println("onLongPress e:"+e);
        super.onLongPress(e);
    }

    /**
     * 滑动时触发，e1为down时的MotionEvent，e2为move时的MotionEvent
     * 
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     * @see android.view.GestureDetector.SimpleOnGestureListener#onScroll(android.view.MotionEvent,
     *      android.view.MotionEvent, float, float)
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        onScrollEvent(e1, e2, distanceX, distanceY);
        System.out.println("onScroll e1:"+e1+" e2:"+e2 +"dx:"+distanceX+" dy"+distanceY);
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    /**
     * 滑动一段距离，up时触发，e1为down时的MotionEvent，e2为up时的MotionEvent
     * 
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     * @see android.view.GestureDetector.SimpleOnGestureListener#onFling(android.view.MotionEvent,
     *      android.view.MotionEvent, float, float)
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        onFlingEvent(e1, e2, velocityX, velocityY);
        System.out.println("onFling e1:"+e1+" e2:"+e2 +"vx:"+velocityX+" vy"+velocityY);
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    /**
     * Touch了还没有滑动时触发
     * 
     * @param e
     * @see android.view.GestureDetector.SimpleOnGestureListener#onShowPress(android.view.MotionEvent)
     */
    @Override
    public void onShowPress(MotionEvent e) {
        System.out.println("onShowPress e:"+e);
        super.onShowPress(e);
    }

    /**
     * @param e
     * @return
     * @see android.view.GestureDetector.SimpleOnGestureListener#onDown(android.view.MotionEvent)
     */
    @Override
    public boolean onDown(MotionEvent e) {
        recordLastMotionEvent(e);
        System.out.println("onDown e:"+e);
        return super.onDown(e);
    }

    /**
     * 轻触
     * @param e
     * @return
     * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTap(android.view.MotionEvent)
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        System.out.println("onDoubleTap e:"+e);
        return super.onDoubleTap(e);
    }

    /**
     *
     * @param e
     * @return
     * @see android.view.GestureDetector.SimpleOnGestureListener#onDoubleTapEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        System.out.println("onDoubleTapEvent e:"+e);
        return super.onDoubleTapEvent(e);
    }

    /**
     * @param e
     * @return
     * @see android.view.GestureDetector.SimpleOnGestureListener#onSingleTapConfirmed(android.view.MotionEvent)
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        System.out.println("onSingleTapConfirmed e:"+e);
        mListener.onSingleTapConfirmed();
        return super.onSingleTapConfirmed(e);
    }

    /**
     * 处理快速滑动事件
     * 
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     */
    private void onFlingEvent(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // 参数解释：
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒

        // velocityX + ":velocityY:" + velocityY);
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && effectiveX(e1, e2)) {
            // Fling left
            if (mListener != null) {
                mListener.onFlingToLeft();
            }
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && effectiveX(e1, e2)) {
            // Fling right
            if (mListener != null) {
                mListener.onFlingToRight();
            }
        }
        if (mListener != null) {
            mListener.onGestureComplete();
        }
    }

    /**
     * 处理拖动事件
     * 
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     */
    private void onScrollEvent(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // 参数解释：
        // e1：ACTION_DOWN MotionEvent
        // e2：ACTION_MOVE MotionEvent
        // distanceX：X轴上的距离
        // distanceY：Y轴上的距离
        boolean moveToLeft = mLastMotionEvent.getX() - e2.getX() > FLING_MIN_DISTANCE;
        boolean moveToRight = e2.getX() - mLastMotionEvent.getX() > FLING_MIN_DISTANCE;
        boolean moveToUp = mLastMotionEvent.getY() - e2.getY() > FLING_MIN_DISTANCE;
        boolean moveToDown = e2.getY() - mLastMotionEvent.getY() > FLING_MIN_DISTANCE;
        // ":e2:" + e2 + ":distanceX:" + distanceX
        // + ":distanceY:" + distanceY);
        boolean record = recordLastMotionEvent(e2);
        if (moveToLeft && record && effectiveX(e1, e2)) {
            // onScroll left
            if (mListener != null) {
                mListener.onScrollMoveToLeft();
                lastGestureType = GESTURE_TYPE.HORIZONTAL_SCORLL;
            }
        } else if (moveToRight && record && effectiveX(e1, e2)) {
            // onScroll right
            if (mListener != null) {
                mListener.onScrollMoveToRight();
                lastGestureType = GESTURE_TYPE.HORIZONTAL_SCORLL;
            }
        } else if (moveToDown && record && effectiveY(e1, e2)) {
            // onScroll down
            if (mListener != null) {
                if (inLeftScreen(e2)) {
                    mListener.onScrollMoveToDownLeftScreen();
                } else if (inRightScreen(e2)) {
                    mListener.onScrollMoveToDownRightScreen();
                }
            }
        } else if (moveToUp && record && effectiveY(e1, e2)) {
            // onScroll up
            if (mListener != null) {
                if (inLeftScreen(e2)) {
                    mListener.onScrolMoveToUpLeftScreen();
                } else if (inRightScreen(e2)) {
                    mListener.onScrolMoveToUpRightScreen();
                }
            }
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean gesture =  gestureDetector.onTouchEvent(motionEvent);
        if (!gesture && motionEvent.getAction() == MotionEvent.ACTION_UP) {
             mListener.onGestureComplete();
            if( lastGestureType == GESTURE_TYPE.HORIZONTAL_SCORLL){
                mListener.onHorizontalScrollComplete();
            }
        }
        if (!gesture && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            mListener.onGestureBegin();
            lastGestureType = GESTURE_TYPE.NONE;
        }
        return mView.onTouchEvent(motionEvent);
    }

    /**
     * 手势监听回调 com.example.gesturetest.GestureReFreshListener
     * 
     * @author yangqinghai <br/>
     *         create at 2013-8-9 下午1:52:28
     */

    public interface onGestureListener {
        /**
         * 左半屏幕向上滑动
         */
        public void onScrolMoveToUpLeftScreen();

        /**
         * 左半屏幕向下滑动
         */
        public void onScrollMoveToDownLeftScreen();

        /**
         * 右半屏幕向上滑动
         */
        public void onScrolMoveToUpRightScreen();

        /**
         * 右半屏幕向下滑动
         */
        public void onScrollMoveToDownRightScreen();

        /**
         * 向左滑动
         */
        public void onScrollMoveToLeft();

        /**
         * 向右滑动
         */
        public void onScrollMoveToRight();

        /**
         * 向左滑动
         */
        public void onFlingToLeft();

        /**
         * 向右滑动
         */
        public void onFlingToRight();

        /***
         * 手势操作完成
         */
        public void onGestureComplete();

        /***
         * 单击抬起
         */
        public void onSingleTapConfirmed();

        /***
         * 单机按下
         */
        public void onGestureBegin();

        /***
         * 水平拖动完成
         */
        public void onHorizontalScrollComplete();
    }

    /***
     * 记录符合条件的位移事件
     * 
     * @param nowMotionEvent
     */
    private boolean recordLastMotionEvent(MotionEvent nowMotionEvent) {
        boolean record = false;
        if (mLastMotionEvent == null) {
            mLastMotionEvent = MotionEvent.obtain(nowMotionEvent);
        }
        // 两次X轴方向位移差
        float motionEventX = mLastMotionEvent.getX() - nowMotionEvent.getX();
        // 两次Y轴方向位移差
        float motionEventY = mLastMotionEvent.getY() - nowMotionEvent.getY();
        if (Math.abs(motionEventX) > FLING_MIN_DISTANCE || Math.abs(motionEventY) > FLING_MIN_DISTANCE) {
            mLastMotionEvent = MotionEvent.obtain(nowMotionEvent);
            record = true;
        }
        return record;
    }

    /**
     * 位移事件是否在左半边屏幕生效
     * 
     * @param nowMotionEvent
     * @return
     */
    private boolean inLeftScreen(MotionEvent nowMotionEvent) {
        boolean inLeftScreen = false;
        // X方向起点生效
        boolean originEeffectiveX = nowMotionEvent.getX() > mIdentifiableAreasO + mIdentifiableAreasOffset;
        // Y方向起点生效
        boolean originEffectiveY = nowMotionEvent.getY() > mIdentifiableAreasO + mIdentifiableAreasOffset;
        // X方向终点生效
        boolean finishEeffectiveX = nowMotionEvent.getX() < mIdentifiableAreasMiddleBoundary - mIdentifiableAreasOffset;
        // Y方向终点生效
        boolean finishEffectiveY = nowMotionEvent.getY() < mIdentifiableAreasHeightY - mIdentifiableAreasOffset;
        if (originEeffectiveX && originEffectiveY && finishEeffectiveX && finishEffectiveY) {
            inLeftScreen = true;
        }
        return inLeftScreen;
    }

    /**
     * 位移事件是否在右半边屏幕生效
     * 
     * @param nowMotionEvent
     * @return
     */
    private boolean inRightScreen(MotionEvent nowMotionEvent) {
        boolean inRightScreen = false;
        // X方向起点生效
        boolean originEeffectiveX = nowMotionEvent.getX() > mIdentifiableAreasMiddleBoundary + mIdentifiableAreasOffset;
        // Y方向起点生效
        boolean originEffectiveY = nowMotionEvent.getY() > mIdentifiableAreasO + mIdentifiableAreasOffset;
        // X方向终点生效
        boolean finishEeffectiveX = nowMotionEvent.getX() < mIdentifiableAreasWidhtX - mIdentifiableAreasOffset;
        // Y方向终点生效
        boolean finishEffectiveY = nowMotionEvent.getY() < mIdentifiableAreasHeightY - mIdentifiableAreasOffset;
        if (originEeffectiveX && originEffectiveY && finishEeffectiveX && finishEffectiveY) {
            inRightScreen = true;
        }
        return inRightScreen;
    }

    /**
     * 在X轴方向生效
     * 
     * @param startMotionEvent
     * @param endMotionEvent
     * @return
     */
    private boolean effectiveX(MotionEvent startMotionEvent, MotionEvent endMotionEvent) {
        boolean effective = false;
        // X方向生效
        boolean effectiveX =
                Math.abs((startMotionEvent.getY() - endMotionEvent.getY())
                        / (startMotionEvent.getX() - endMotionEvent.getX())) <= RATIO_30_DEGREE_SQUARE;
        if (effectiveX) {
            effective = true;
        }
        return effective;
    }

    /**
     * 在Y轴方向生效
     * 
     * @param startMotionEvent
     * @param endMotionEvent
     * @return
     */
    private boolean effectiveY(MotionEvent startMotionEvent, MotionEvent endMotionEvent) {
        boolean effective = false;
        // Y方向生效
        boolean effectiveY =
                Math.abs((startMotionEvent.getX() - endMotionEvent.getX())
                        / (startMotionEvent.getY() - endMotionEvent.getY())) <= RATIO_30_DEGREE_SQUARE;
        if (effectiveY) {
            effective = true;
        }
        return effective;
    }
    // setFullScreenMode(!mPlayPanelFragment.isFullScreenMode());
}
