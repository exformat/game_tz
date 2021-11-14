package com.exformat.tz.managers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.exformat.tz.game_objects.Background;
import com.exformat.tz.model.GameObject;

public class BackgroundManager extends AbstractManager
{

	private Array<Background> backgrounds;
	
	private TextureAtlas atlas;

	public BackgroundManager(TextureAtlas atlas) {
		this.atlas = atlas;
	}
	
	
	@Override
	public void update(float dt, float camPosY) {
		for(int i = 0; i < backgrounds.size; i++){
			Background b = backgrounds.get(i);
			b.setPosition(0, camPosY - b.getHalfHeight());
		}
	}

	@Override
	public Array<GameObject> create() {
		backgrounds = new Array<>();
		backgrounds.add(new Background(atlas));
		
		gameObjectArray.addAll(backgrounds);
		return gameObjectArray;
	}

	@Override
	public void reInit() {
		for(int i = 0; i < backgrounds.size; i++){
			Background b = backgrounds.get(i);
			b.setPosition(0, i * b.getHeight());
		}
	}

}
