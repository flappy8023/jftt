package com.jmtad.jftt.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.jmtad.jftt.R;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-14 14:00
 **/
public class SoundPoolUtil {
    private static SoundPoolUtil soundPoolUtil;
    private SoundPool soundPool;
    private boolean loaded = false;

    //单例模式
    public static SoundPoolUtil getInstance(Context context) {
        if (soundPoolUtil == null)
            soundPoolUtil = new SoundPoolUtil(context);
        return soundPoolUtil;
    }

    private SoundPoolUtil(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 1);
        //加载音频文件
        soundPool.load(context, R.raw.music, 1);
        soundPool.setOnLoadCompleteListener((soundPool, i, i1) -> loaded = true);


    }

    public void play(int number) {
        Log.d("tag", "number " + number);
        //播放音频
        if (loaded) {
            soundPool.play(number, 1, 1, 0, 0, 1);
        }
    }
}
