package com.exformat.tz.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.exformat.tz.model.GameObject;


public class GameObjectsRenderer
{
	
	private final String TAG = "G.O.Render";
	
	private Array<GameObject> objects;
	private OrthographicCamera camera;
	private SpriteBatch gameObjectsSpriteBatch;
	
	private float frameTime = 0;
	private boolean debug = false;

	private Box2DDebugRenderer debugRender;
	private World world;
	
	public GameObjectsRenderer(Array<GameObject> objects, OrthographicCamera camera, SpriteBatch batch, World world) {
		this.objects = objects;
		this.gameObjectsSpriteBatch = batch;
		this.camera = camera;
		
		
		debugRender = new Box2DDebugRenderer();
		this.world = world;
	}
	
	public void draw(){
		float startTime = TimeUtils.nanoTime();
		
		camera.update();
		gameObjectsSpriteBatch.setProjectionMatrix(camera.combined);
		gameObjectsSpriteBatch.begin();

		for(int i = objects.size; --i >= 0;){
			GameObject obj = objects.get(i);
			obj.draw(gameObjectsSpriteBatch, 1);
		}

		gameObjectsSpriteBatch.end();
		
		if(debug){
			debugRender.render(world, camera.combined);
		}
		
		
		frameTime = (TimeUtils.nanoTime() - startTime) / 1000_000;
	}

	public float getFrameTime() {
		return frameTime;
	}
	
	public void dispose(){
		for(GameObject go: objects){
			go.dispose();
		}
	}
	
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}
	
	private void log(String message){
		Gdx.app.log(TAG, message);
	}
}
