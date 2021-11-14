package com.exformat.tz;

public class GameConstants
{

	public static final float DIVIDER = 100;
	public static final float SCL = 0.01f;
	public static final float WORLD_WIDTH = 10.8f;
	public static final float WORLD_HEIGHT = 19.2f;


	public static final short CATEGORY_PLAYER = 0x0001; 
	public static final short CATEGORY_BONUS = 0x0002; 
	public static final short CATEGORY_ENEMY = 0x0004; 
	public static final short CATEGORY_PLATFORM = 0x0008; 
	
	


	public static final short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_PLATFORM | CATEGORY_BONUS; 
	public static final short MASK_BONUS = CATEGORY_PLAYER; 
	public static final short MASK_ENEMY = CATEGORY_PLAYER; 
	public static final short MASK_PLATFORM = CATEGORY_PLAYER;
	
}
