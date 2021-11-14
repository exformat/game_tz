package com.exformat.tz.game_objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.exformat.tz.GameConstants;
import com.exformat.tz.managers.AudioManager;
import com.exformat.tz.model.GameObject;

public class Player extends GameObject
{
	private float velocity = 3.3f;
	private float jumpVelocity = 14;
	private Fixture sensor;
	
	public static boolean contactPlatform = false;
	
	public Player(World world, TextureAtlas atlas){
		super(world, atlas.findRegion("player"));
		builderCircle(BodyDef.BodyType.DynamicBody, new Vector2(0, 0), halfWidth - 0.08f);
		sensor = buildCircleSensor(halfWidth + 0.5f);
		
		setFilter(GameConstants.CATEGORY_PLAYER, GameConstants.MASK_PLAYER, sensor);
		setFilter(GameConstants.CATEGORY_PLAYER, GameConstants.MASK_PLAYER);
		
		body.setFixedRotation(true);
		body.setLinearDamping(1);

		TAG = "PLAYER";
	}

	
	
	public void jump(){
		if(contactPlatform && body.getLinearVelocity().y < 0.1f){
			float xVelocity = body.getLinearVelocity().x;
			body.setLinearVelocity(xVelocity, jumpVelocity);
			AudioManager.jump.play();
			log("jump");
		}
	}
	
	public void moveLeft(){
		float yVelocity = body.getLinearVelocity().y;
		body.setLinearVelocity(-velocity, yVelocity);
	}
	
	public void moveRight(){
		float yVelocity = body.getLinearVelocity().y;
		body.setLinearVelocity(velocity, yVelocity);
	}
}
