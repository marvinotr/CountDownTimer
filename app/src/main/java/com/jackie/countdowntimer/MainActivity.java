package com.jackie.countdowntimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mCountDownTextView;
    private ImageView mCodeImageView;
    private CountDownTimer mCountDownTimer;
    private SwitchButton mSwitchButton;

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
        mCodeImageView = (ImageView) findViewById(R.id.verify_code);
        mCodeImageView.setImageBitmap(CodeUtils.getInstance().createBitmap());

        mCountDownTimer = new CountDownTimerUtils(mCountDownTextView, 60000, 1000);
        mCountDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimer.start();
            }
        });

        mCodeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeImageView.setImageBitmap(CodeUtils.getInstance().createBitmap());
            }
        });

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
 }
