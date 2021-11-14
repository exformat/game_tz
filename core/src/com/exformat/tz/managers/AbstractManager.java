package com.exformat.tz.managers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.exformat.tz.model.GameObject;

public abstract class AbstractManager
{
	protected String TAG = "";
	protected Array<GameObject> gameObjectArray = new Array<>();
	
	abstract public void update(float dt, float camPosY);
	abstract public Array<GameObject> create();
	abstract public void reInit();
	
	protected void log(String s){
		Gdx.app.log(TAG, s);
	}
	
	public void dispose(){}
}
