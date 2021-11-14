package com.exformat.tz.game_objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.exformat.tz.GameConstants;
import com.exformat.tz.model.GameObject;

public class NumberTable extends GameObject
{
	
	private Array<Sprite> spriteNumbers = new Array<>(10);
	private Array<Integer> floorNumbers = new Array<>();
	
	private int floor = 0;
	
	
	
	public NumberTable(TextureAtlas atlas, Vector2 pos){
		super(null, atlas.findRegion("table"));
		setPosition(pos.x, pos.y);
		for(int i = 0; i < 10; i++){
			Sprite s = atlas.createSprite(i + "");
			s.setBounds(0, 0, s.getWidth() / GameConstants.DIVIDER, s.getHeight() / GameConstants.DIVIDER);
			s.setOriginCenter();
			spriteNumbers.add(s);
		}
	}

	
	
	

	@Override
	public void draw(Batch batch, float alphaModulation) {
		super.draw(batch, alphaModulation);
		
		// либо так, либо творить еще большие костыли для отрисовки 11, 22, 33...
		calcNumbers(batch);
	}
	
	private void calcNumbers(Batch batch){
		int f = floor + 1;
		float x = 0;
		floorNumbers.clear();
		while(f > 0){
			floorNumbers.add(f % 10);
			f /= 10;
		}
		floorNumbers.reverse();
		for(int i = 0; i < floorNumbers.size; i++){
			int number = floorNumbers.get(i);
			
			Sprite s = spriteNumbers.get(number);
			
			s.setPosition(getX() + x + (halfWidth / 2), getY() + (s.getHeight() / 2));
			x += s.getWidth();
			
			s.draw(batch);
		}
	}
	
	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getFloor() {
		return floor;
	}
	
}
