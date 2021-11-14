package com.exformat.tz.game_objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.exformat.tz.model.GameObject;

public class Grass extends GameObject
{
	
	public Grass(TextureAtlas atlas){
		super(null, atlas.findRegion("grass"));
		setPosition(0, halfHeight);
	}
}
