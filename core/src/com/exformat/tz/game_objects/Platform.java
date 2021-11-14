package com.exformat.tz.game_objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.MathUtils;
import com.exformat.tz.GameConstants;
import com.exformat.tz.model.GameObject;

public class Platform extends GameObject
{
	
	private float velocity = 2;
	
	public Platform(World world, TextureAtlas atlas){
		super(world, atlas.findRegion("platform"));
		builderBox(BodyDef.BodyType.KinematicBody, new Vector2(0, 0), getWidth(), getHeight());
		
		setFilter(GameConstants.CATEGORY_PLATFORM, GameConstants.MASK_PLATFORM);
		getBody().setLinearVelocity(MathUtils.random(-velocity, velocity), 0);
	}

	@Override
	public void update(float dT) {
		super.update(dT);
		
		if(getX() > GameConstants.WORLD_WIDTH){
			getBody().setLinearVelocity(MathUtils.random(-velocity, 0f), 0);
		}
		
		if(getX() + getWidth() < 0){
			getBody().setLinearVelocity(MathUtils.random(0f, velocity), 0);
		}
	}
}
