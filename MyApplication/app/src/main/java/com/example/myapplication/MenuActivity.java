package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // 绑定并设置Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTest = findViewById(R.id.tv_test);
    }

    // 加载菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_text, menu);
        return true;
    }

    // 菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        // 字体大小设置
        if (itemId == R.id.size_small) {
            tvTest.setTextSize(10);
            return true;
        } else if (itemId == R.id.size_mid) {
            tvTest.setTextSize(16);
            return true;
        } else if (itemId == R.id.size_large) {
            tvTest.setTextSize(20);
            return true;
        }
        // 普通菜单项
        else if (itemId == R.id.menu_normal) {
            Toast.makeText(this, "点击了普通菜单项", Toast.LENGTH_SHORT).show();
            return true;
        }
        // 字体颜色设置
        else if (itemId == R.id.color_red) {
            tvTest.setTextColor(getResources().getColor(com.google.android.material.R.color.design_default_color_error)); // 需在colors.xml定义red
            return true;
        } else if (itemId == R.id.color_black) {
            tvTest.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
