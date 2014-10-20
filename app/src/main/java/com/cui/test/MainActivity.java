package com.cui.test;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.cui.view.geture.GestureObserve;
import com.cui.view.util.ScreenUtil;
import com.cui.view.geture.R;


public class MainActivity extends ActionBarActivity  implements GestureObserve.onGestureListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtil.initScreenData(this);
        RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
        new GestureObserve(this,main,this).init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrolMoveToUpLeftScreen() {

    }

    @Override
    public void onScrollMoveToDownLeftScreen() {

    }

    @Override
    public void onScrolMoveToUpRightScreen() {

    }

    @Override
    public void onScrollMoveToDownRightScreen() {

    }

    @Override
    public void onScrollMoveToLeft() {

    }

    @Override
    public void onScrollMoveToRight() {

    }

    @Override
    public void onFlingToLeft() {

    }

    @Override
    public void onFlingToRight() {

    }

    @Override
    public void onGestureComplete() {
        System.out.println("onGestureComplete");
    }

    @Override
    public void onSingleTapConfirmed() {

    }

    @Override
    public void onGestureBegin() {
        System.out.println("!!!!!!!!!!!!onGestureBegin");
    }

    @Override
    public void onHorizontalScrollComplete() {
        System.out.println("!!!!!!!!!!!onHorizontalScrollComplete");
    }

}
