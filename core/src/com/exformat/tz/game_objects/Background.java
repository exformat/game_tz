package com.exformat.tz.game_objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.exformat.tz.model.GameObject;

public class Background extends GameObject
{

	public Background(TextureAtlas atlas){
		super(null, atlas.findRegion("background"));
	}
}
