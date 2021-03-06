package com.summerrc.dumplingplan.utils;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import com.summerrc.dumplingplan.R;
import com.summerrc.dumplingplan.config.GameDataManager;

public class SoundUtil  {
	/** 音效编号 */
	public static final int BACK = 6;				//返回
	public static final int REPLAY = 6;			    //重玩
	public static final int STOP = 6;			    //暂停
	public static final int SETTING = 6;			//设置
	public static final int ONE_ONE = 6;			//欢迎界面
	public static final int ONE_TWO = 7;
	public static final int ONE_THREE = 8;
	public static final int TWO_ONE = 9;			//选择食材
	public static final int TWO_TWO = 10;
	public static final int TWO_THREE = 11;
	public static final int FOUR_ONE = 12;			//切馅
	public static final int NINE_ONE = 13;
	public static final int WATER = 15;
	public static final int WIN = 16;
	public static final int LOSE = 17;
	public static final int MINUS = 19;
	public static final int CUT = 18;
	public static final int BOMB = 20;



	public static SoundPool soundPool;				//声音缓冲池
	public static HashMap<Integer, Integer> soundPoolMap; 				//存放声音ID的Map

	/**
	 * *声音缓冲池的初始化
	 * @param context 上下文参数
	 */
	public static void initSounds(Context context) {
		if(soundPool != null) {
			return;
		}
		/** 创建声音缓冲池 */
		soundPool = new SoundPool(
						20, 						//同时能最多播放的个数
						AudioManager.STREAM_MUSIC,  //音频的类型
						100							//声音的播放质量，目前无效
		);

		soundPoolMap = new HashMap<>();				//创建声音资源Map
		/** 将加载的声音资源id放进此Map */
		soundPoolMap.put(ONE_ONE, soundPool.load(context, R.raw.dumpling_plan_sd_one_one, 1));
		soundPoolMap.put(ONE_TWO, soundPool.load(context, R.raw.dumpling_plan_sd_one_two, 1));
		soundPoolMap.put(ONE_THREE, soundPool.load(context, R.raw.dumpling_plan_sd_one_three, 1));
		soundPoolMap.put(TWO_ONE, soundPool.load(context, R.raw.dumpling_plan_sd_two_one, 1));
		soundPoolMap.put(TWO_TWO, soundPool.load(context, R.raw.dumpling_plan_sd_two_two, 1));
		soundPoolMap.put(TWO_THREE, soundPool.load(context, R.raw.dumpling_plan_sd_two_three, 1));
		soundPoolMap.put(FOUR_ONE, soundPool.load(context, R.raw.dumpling_plan_sd_four_one, 1));
		soundPoolMap.put(NINE_ONE, soundPool.load(context, R.raw.dumpling_plan_sd_nine_one, 1));
		soundPoolMap.put(WATER, soundPool.load(context, R.raw.dumpling_plan_sd_water, 1));
		soundPoolMap.put(WIN, soundPool.load(context, R.raw.lianliankan_sd_win, 1));
		soundPoolMap.put(LOSE, soundPool.load(context, R.raw.lianliankan_sd_lose, 1));
		soundPoolMap.put(MINUS, soundPool.load(context, R.raw.minus, 1));
		soundPoolMap.put(CUT, soundPool.load(context, R.raw.cut, 1));
		soundPoolMap.put(BOMB, soundPool.load(context, R.raw.bomb_explode, 1));
	}

	/**
	 * 播放声音的方法
	 * @param key  存放音频资源id的HashMap的key
	 * @param loop 是否循环
	 * @param context 上下文参数
	 */
	public static void playSounds(int key, int loop, Context context) {
		if(!GameDataManager.init(context).isSoundST()) {
			return;
		}
		AudioManager mgr = (AudioManager)  context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		soundPool.play(
				soundPoolMap.get(key), 						//声音id
				1, 									        //左声道
				1, 											//右声道
				1,											//优先级
				loop,										//是否循环
				0.5f										//rate
		);
	}

	public static void release() {
		soundPool.release();
	}
}
