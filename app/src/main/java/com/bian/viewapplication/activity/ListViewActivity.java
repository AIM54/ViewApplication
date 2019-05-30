package com.bian.viewapplication.activity;

import android.os.Bundle;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bian.viewapplication.R;

public class ListViewActivity extends AppCompatActivity implements ScaleGestureDetector.OnScaleGestureListener {
    private ListView mListView;
    private SpringAnimation springAnimation;
    private SpringForce springForce;
    private ScaleGestureDetector mScaleGestureDetector;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mListView = findViewById(R.id.lv_main);
        String[] items = new String[1000];
        initStringArray(items);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(arrayAdapter);
        mScaleGestureDetector=new ScaleGestureDetector(this,this);
    }

    /**
     *  {@link #onCreate(Bundle)}
     * @param items
     */
    private void initStringArray(String[] items) {
        for (int index = 0; index < 1000; index++) {
            items[index] = "第" + index + "条数据";
        }
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        detector.getCurrentSpan();
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
