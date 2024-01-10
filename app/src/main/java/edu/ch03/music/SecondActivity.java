package edu.ch03.music;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SeekBar musicSeekBar;
    private ImageView bottomIcon, bottomNext, bottomPlay, bottomLast;
    private int[] musicArray = {R.raw.music0, R.raw.music1, R.raw.music2}; // 歌曲列表
    private int currentSongIndex = 0; // 当前播放的歌曲索引
    private Handler handler = new Handler(); // 用于更新进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, musicArray[currentSongIndex]);

        musicSeekBar = findViewById(R.id.music_seekBar);

        // 通过线程更新进度条
        // 启动一个新的线程来定期更新SeekBar的进度
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        // 每100毫秒更新一次进度
                        Thread.sleep(100);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mediaPlayer != null) {
                                    musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        bottomIcon = findViewById(R.id.bottom_iv_icon);
        bottomNext = findViewById(R.id.bottom_iv_next);
        bottomPlay = findViewById(R.id.bottom_iv_play);
        bottomLast = findViewById(R.id.bottom_iv_last);

        musicSeekBar.setMax(mediaPlayer.getDuration());

        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    progressChanged = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 用户开始滑动SeekBar时调用
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 用户停止滑动SeekBar时调用
                mediaPlayer.seekTo(progressChanged);
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.bottom_iv_icon) {
                    // 处理bottomIcon的点击事件
                } else if (id == R.id.bottom_iv_next) {
                    // 处理bottomNext的点击事件
                    nextSong();
                } else if (id == R.id.bottom_iv_play) {
                    // 处理bottomPlay的点击事件
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        bottomPlay.setImageResource(R.drawable.ripple_play); // 设置为播放图标
                    } else {
                        mediaPlayer.start();
                        bottomPlay.setImageResource(R.drawable.ripple_pause); // 设置为暂停图标
                    }
                } else if (id == R.id.bottom_iv_last) {
                    // 处理bottomLast的点击事件
                    previousSong();
                }
            }
        };

        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当用户点击menu_icon时，启动MusicListActivity
                Intent intent = new Intent(SecondActivity.this, MusicListActivity.class);
                startActivity(intent);
            }
        });
        bottomIcon.setOnClickListener(onClickListener);
        bottomNext.setOnClickListener(onClickListener);
        bottomPlay.setOnClickListener(onClickListener);
        bottomLast.setOnClickListener(onClickListener);
    }


    private void nextSong() {
        currentSongIndex = (currentSongIndex + 1) % musicArray.length;
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, musicArray[currentSongIndex]);
        mediaPlayer.start();
        musicSeekBar.setProgress(0); // 将进度条设置为0
        musicSeekBar.setMax(mediaPlayer.getDuration()); // 重置SeekBar的最大值
    }

    private void previousSong() {
        currentSongIndex = (currentSongIndex - 1 + musicArray.length) % musicArray.length;
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, musicArray[currentSongIndex]);
        mediaPlayer.start();
        musicSeekBar.setProgress(0); // 将进度条设置为0
        musicSeekBar.setMax(mediaPlayer.getDuration()); // 重置SeekBar的最大值
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}