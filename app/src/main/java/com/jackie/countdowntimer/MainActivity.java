package com.jackie.countdowntimer;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mCountDownTextView;
    private CountDownTimer mCountDownTimer;
    private ImageView mCodeImageView;
    private SwitchButton mSwitchButton;
    private EditText mNumberEditText;
    private ImageButton mSelectImageButton;
    private PopupWindow mPopupWindow;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private List<String> mNumberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCountDownTextView = (TextView) findViewById(R.id.count_down);
        mCountDownTimer = new CountDownTimerUtils(mCountDownTextView, 60000, 1000);
        mCountDownTextView.setOnClickListener(this);

        mCodeImageView = (ImageView) findViewById(R.id.verify_code);
        mCodeImageView.setImageBitmap(CodeUtils.getInstance().createBitmap());
        mCodeImageView.setOnClickListener(this);

        mSwitchButton = (SwitchButton) findViewById(R.id.switcher);
        mSwitchButton.setSwitchState(true);
        mSwitchButton.setOnSwitchStateChangeListener(new OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean state) {
                if (state) {
                    Toast.makeText(MainActivity.this, "开关打开了", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "开关关闭了", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mNumberEditText = (EditText) findViewById(R.id.number);
        mSelectImageButton = (ImageButton) findViewById(R.id.select_number);
        mSelectImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count_down:
                mCountDownTimer.start();
                break;
            case R.id.verify_code:
                mCodeImageView.setImageBitmap(CodeUtils.getInstance().createBitmap());
                break;
            case R.id.select_number:
                showSelectNumberPopupWindow();
                break;
        }
    }

    /**
     * 弹出选择号码的对话框
     */
    private void showSelectNumberPopupWindow() {
        initRecyclerView();

        mPopupWindow = new PopupWindow(mRecyclerView, mNumberEditText.getWidth() - 4, 200);
        mPopupWindow.setOutsideTouchable(true);		// 设置外部可以被点击
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.setFocusable(true);		// 使PopupWindow可以获得焦点
        // 显示在输入框的左下角
        mPopupWindow.showAsDropDown(mNumberEditText, 2, -5);
    }

    /**
     * 初始化RecyclerView，模仿ListView下拉列表的效果
     */
    private void initRecyclerView(){
        mNumberList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mNumberList.add("1000000" + i);
        }

        mRecyclerView = new RecyclerView(this);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setBackgroundResource(R.drawable.background);

        //设置Adapter
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, mNumberList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        //设置点击事件
        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mNumberEditText.setText(mNumberList.get(position));
                mNumberEditText.setSelection(mNumberEditText.getText().toString().length());
                mPopupWindow.dismiss();
            }
        });

        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }
 }
