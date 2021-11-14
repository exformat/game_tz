package com.exformat.tz.managers;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.assets.AssetManager;

public class AudioManager
{
	public static Music music;
	public static Sound jump;
	public static Sound collision;
	
	public static void load(AssetManager assetManager){
		assetManager.load("audio/music/music.mp3", Music.class);
		assetManager.load("audio/sounds/collision.ogg", Sound.class);
		assetManager.load("audio/sounds/jump.ogg", Sound.class);
	}
	
	public static void init(AssetManager assetManager){
		music = assetManager.get("audio/music/music.mp3", Music.class);
		collision = assetManager.get("audio/sounds/collision.ogg", Sound.class);
		jump = assetManager.get("audio/sounds/jump.ogg", Sound.class);
	}
}
