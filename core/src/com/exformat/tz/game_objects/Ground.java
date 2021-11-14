package com.exformat.tz.game_objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.exformat.tz.GameConstants;
import com.exformat.tz.model.GameObject;

public class Ground extends GameObject
{
	public Ground(World world, TextureAtlas atlas){
		super(world, atlas.findRegion("ground"));
		builderBox(BodyDef.BodyType.StaticBody, new Vector2(0, 0), getWidth(), getHeight());
		setTransform(GameConstants.WORLD_WIDTH / 2, 0, 0);
		setFilter(GameConstants.CATEGORY_PLATFORM, GameConstants.MASK_PLATFORM);
	}
}
