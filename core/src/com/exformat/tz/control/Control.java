package com.exformat.tz.control;


import com.badlogic.gdx.*;
import com.badlogic.gdx.input.*;
import com.badlogic.gdx.math.*;
import com.exformat.tz.game_objects.Player;

public class Control extends InputAdapter implements GestureDetector.GestureListener {

	private final String TAG = "CONTROL";

	
	private GestureDetector gestureDetector = new GestureDetector(this);
	private InputMultiplexer inputMultiplexer = new InputMultiplexer();

	private Player player;
	
	
	public Control(Player player){
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(gestureDetector);
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		this.player = player;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float delteY){
		if(deltaX > 0){
			player.moveRight();
		}
		if(deltaX < 0){
			player.moveLeft();
		}

		if(delteY < 0){
			player.jump();
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y){
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4){
		return false;
	}

	@Override
	public void pinchStop(){
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button){
		return false;
	}
	
	private void log(String message){
		Gdx.app.log(TAG, message);
	}
}

