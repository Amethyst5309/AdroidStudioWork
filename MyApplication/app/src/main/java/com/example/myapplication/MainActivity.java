package com.example.myapplication;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.os.Build;
import android.os.Bundle;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // 动物名称数组
    private String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    // 动物图片资源ID（需将图片放入drawable目录，命名对应）
    private int[] animalImages = {
            R.drawable.lion, R.drawable.tiger,
            R.drawable.monkey, R.drawable.dog,
            R.drawable.cat, R.drawable.elephant
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.animal_list);
        //SimpleAdapter所需的List<Map>格式
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < animalImages.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", animalImages[i]);
            item.put("text", animalNames[i]);
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                R.layout.item_list,
                new String[]{"image", "text"},
                new int[]{R.id.item_image, R.id.item_text}
        );

        listView.setAdapter(adapter);

        // 列表项点击事件（Toast提示+发送通知）
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedAnimal = animalNames[position];
            // 1. Toast显示选中信息
            Toast.makeText(MainActivity.this, selectedAnimal, Toast.LENGTH_SHORT).show();

            // 2. 发送通知
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        "list_channel", "ListView通知", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
                // 构建通知时添加渠道ID
                Notification notification = new Notification.Builder(this, "list_channel")
                        .setSmallIcon(R.drawable.square_button) // 关键：添加小图标（确保该资源存在）
                        .setContentTitle("ListView选中通知")
                        .setContentText("你选中了动物：" + selectedAnimal)
                        .build();
                notificationManager.notify(1, notification);
            }

        });

        // 在MainActivity的onCreate中添加触发按钮逻辑（或在其他事件中调用）
        Button showDialogBtn = new Button(this);
        showDialogBtn.setText("显示登录对话框");
        showDialogBtn.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // 获取主布局（ConstraintLayout），无需转换类型
        ConstraintLayout mainLayout = findViewById(R.id.main_layout);
        // 设置按钮的布局参数（ConstraintLayout 必须用 ConstraintLayout.LayoutParams）
        ConstraintLayout.LayoutParams btnParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // 宽度占满
                ConstraintLayout.LayoutParams.WRAP_CONTENT  // 高度自适应
        );
        // 关键约束：让按钮贴紧主布局底部
        btnParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        // 添加约束：左右贴父布局
        btnParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        btnParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        // 添加上边距（可选）
        btnParams.topMargin = 20;

        // 将按钮添加到主布局
        mainLayout.addView(showDialogBtn, btnParams);

        // 按钮点击显示对话框
        showDialogBtn.setOnClickListener(v -> {
            // 1. 加载自定义布局
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
            // 2. 创建AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(dialogView); // 设置自定义布局
            AlertDialog dialog = builder.create();

            // 3. 绑定按钮事件
            dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> dialog.dismiss()); // 取消
            dialogView.findViewById(R.id.btn_signin).setOnClickListener(v1 -> {
                // 获取输入内容
                String username = ((EditText) dialogView.findViewById(R.id.et_username)).getText().toString();
                String password = ((EditText) dialogView.findViewById(R.id.et_password)).getText().toString();
                // 简单验证（可扩展）
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录成功：" + username, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            // 4. 显示对话框
            dialog.show();
        });
    }
}