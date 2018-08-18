package com.bai.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bai.myapplication.Util.OnDragViewClickListener;
import com.bai.myapplication.View.DragView;

public class MainActivity extends AppCompatActivity {

    private DragView dragView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragView = findViewById(R.id.DragView);
        dragView.SetClickListener(new OnDragViewClickListener() {
            @Override
            public void onDragViewListener(String name, String context) {
                if (name.equals("pic")){
                    Toast.makeText(MainActivity.this,"点击事件",Toast.LENGTH_SHORT).show();
                }else if (name.equals("delete")){
                    Toast.makeText(MainActivity.this,"关闭view",Toast.LENGTH_SHORT).show();
                    dragView.setVisibility(View.GONE);
                }
            }
        });
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragView.setVisibility(View.VISIBLE);
            }
        });
    }
}
