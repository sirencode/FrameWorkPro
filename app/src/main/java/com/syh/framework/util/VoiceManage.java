package com.syh.framework.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.syh.framework.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyonghe on 2020-03-23.
 */
public class VoiceManage {
    public static void mediaPlayer(Context context, int rawRes) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, rawRes);
        mediaPlayer.start();
    }

    public static void StopMediaPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public static void play(Context context) {
        SoundPool soundpool;
        if (Build.VERSION.SDK_INT > 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频数量
            builder.setMaxStreams(5);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_SYSTEM);//STREAM_MUSIC
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundpool = builder.build();
        } else {
            soundpool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 0);
        }
        Map<Integer, Integer> soundmap = new HashMap<Integer, Integer>();
        soundmap.put(1, soundpool.load(context, R.raw.one, 1));
        soundmap.put(2, soundpool.load(context, R.raw.two, 1));
        soundmap.put(3, soundpool.load(context, R.raw.three, 1));
        soundmap.put(4, soundpool.load(context, R.raw.four, 1));
        soundmap.put(5, soundpool.load(context, R.raw.five, 1));
        soundpool.play(soundmap.get(1), 1, 1, 0, 0, 1);
    }
}
