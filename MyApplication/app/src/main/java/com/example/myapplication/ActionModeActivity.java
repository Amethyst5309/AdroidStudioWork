package com.example.myapplication;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionModeActivity extends AppCompatActivity {

    private ListView listView;
    private String[] listData = {"One", "Two", "Three", "Four", "Five"};
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_model);

        listView = findViewById(R.id.list_action_mode);

        // 准备数据列表（将文字和图标封装成Map）
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (String listDatum : listData) {
            Map<String, Object> item = new HashMap<>();
            item.put("icon", R.drawable.ic_launcher_foreground);  // 图标资源ID
            item.put("text", listDatum);  // 文字内容
            dataList.add(item);
        }

        // 创建SimpleAdapter，绑定数据到自定义布局
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                dataList,
                R.layout.item_list_with_icon,  // 自定义列表项布局
                new String[]{"icon", "text"},  // 数据的key（与Map中的key对应）
                new int[]{R.id.item_icon, R.id.item_text}  // 绑定到布局中的控件ID
        );

        listView.setAdapter(adapter);

        // 设置ListView长按触发ActionMode
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            // 选中项变化时调用
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // 获取选中项数量
                int selectedCount = listView.getCheckedItemCount();
                // 更新ActionMode标题（如“1 selected”）
                mode.setTitle(selectedCount + " selected");
            }

            // 创建ActionMode菜单
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mActionMode = mode;
                // 加载菜单（可自定义menu.xml，此处简化用代码添加）
                menu.add("删除").setIcon(android.R.drawable.ic_menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                return true;
            }

            // ActionMode菜单准备时调用
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            // ActionMode菜单项点击
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getTitle().equals("删除")) {
                    // 获取选中项并处理（此处仅Toast提示）
                    int selectedCount = listView.getCheckedItemCount();
                    Toast.makeText(ActionModeActivity.this, "删除" + selectedCount + "项", Toast.LENGTH_SHORT).show();
                    mode.finish(); // 关闭ActionMode
                    return true;
                }
                return false;
            }

            // ActionMode销毁时调用
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
            }
        });
    }
}
