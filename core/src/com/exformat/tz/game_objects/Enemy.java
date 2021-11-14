package com.exformat.tz.game_objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.exformat.tz.GameConstants;
import com.exformat.tz.model.GameObject;

public class Enemy extends GameObject
{
	public Enemy(World world, TextureAtlas atlas){
		super(world, atlas.findRegion("enemy"));
		builderCircle(BodyDef.BodyType.KinematicBody, new Vector2(0, 0), getWidth());
		
		setFilter(GameConstants.CATEGORY_ENEMY, GameConstants.MASK_ENEMY);
	}
}
