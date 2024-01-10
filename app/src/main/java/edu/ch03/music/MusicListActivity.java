package edu.ch03.music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MusicListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<MusicItem> adapter;
    private List<MusicItem> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_list);

        listView = findViewById(R.id.lv);

        // 初始化音乐列表
        musicList = new ArrayList<>();
        // 假设你有一个方法来获取raw目录下的所有音乐文件的名称
        musicList = getAllRawMusicNames();

        // 假设你有一个适配器
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, musicList);
        listView.setAdapter(adapter);

//        // 为 ListView 的每一项设置点击事件,切换歌曲
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 获取点击的 MusicItem 对象
//                MusicItem musicItem = musicList.get(position);
//                // 获取音乐资源ID
//                int musicResId = musicItem.getResId();
//                // 创建一个 Intent
//                Intent intent = new Intent(MusicListActivity.this, SecondActivity.class);
//                // 将音乐资源ID作为额外数据放入 Intent
//                intent.putExtra("musicResId", musicResId);
//                // 使用 Intent 启动 SecondActivity
//                startActivity(intent);
//            }
//        });
    }

    private List<MusicItem> getAllRawMusicNames() {
        List<MusicItem> musicNames = new ArrayList<>();
        musicNames.add(new MusicItem("music0", R.raw.music0));
        musicNames.add(new MusicItem("music1", R.raw.music1));
        musicNames.add(new MusicItem("music2", R.raw.music2));
        return musicNames;
    }
}