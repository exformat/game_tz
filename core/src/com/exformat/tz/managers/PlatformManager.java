package com.exformat.tz.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.exformat.tz.GameConstants;
import com.exformat.tz.game_objects.Platform;
import com.exformat.tz.model.GameObject;

public class PlatformManager extends AbstractManager
{
	
	private Array<Platform> platforms;
	private World world;
	private TextureAtlas atlas;
	
	private int platformCount = 50;
	
	private float yInterval = 3;
	private float xPlatformPos = 2;
	private float yPlatformPos = 0;
	
	private Body leftWall;
	private Body rightWall;
	
	public PlatformManager(World world, TextureAtlas atlas) {
		this.world = world;
		this.atlas = atlas;
	}
	
	@Override
	public void update(float dt, float camPosY){
		
		for (Platform p: platforms) {
			if (p.getY() < camPosY - (yInterval * (platformCount - 10))) {
				yPlatformPos += yInterval;
				xPlatformPos = MathUtils.random(0, GameConstants.WORLD_WIDTH);
				p.setTransform(xPlatformPos, yPlatformPos, 0);
			}
		}
		
		rightWall.setTransform(0, camPosY, 0);
		leftWall.setTransform(GameConstants.WORLD_WIDTH, camPosY, 0);
	}
	
	
	@Override
	public Array<GameObject> create(){
		platforms = new Array<>();
		Vector2 coord = new Vector2();
		for (int i = 0; i < platformCount; i++) {
			yPlatformPos += yInterval;
			xPlatformPos = MathUtils.random(0, GameConstants.WORLD_WIDTH);
			coord.set(xPlatformPos, yPlatformPos);
			
			Platform platform = new Platform(world, atlas);
			platform.setTransform(coord, 0);
			
			platforms.add(platform);
			gameObjectArray.add(platform);
		}
		createWalls();
		return gameObjectArray;
	}
	
	
	
	private void createWalls(){
		Filter filter = new Filter();
		filter.categoryBits = GameConstants.CATEGORY_PLATFORM;
		filter.maskBits = GameConstants.MASK_PLATFORM;
		
		BodyDef lBodyDef = new BodyDef();
		BodyDef rBodyDef = new BodyDef();

		lBodyDef.position.set(new Vector2(0, 0));
		rBodyDef.position.set(new Vector2(GameConstants.WORLD_WIDTH, 0));

		leftWall = world.createBody(lBodyDef);
		rightWall = world.createBody(rBodyDef);

		PolygonShape lBox = new PolygonShape();  
		lBox.setAsBox(0.01f, GameConstants.WORLD_HEIGHT * 3);
		leftWall.createFixture(lBox, 0.0f);
		lBox.dispose();

		PolygonShape rBox = new PolygonShape();  
		rBox.setAsBox(0.01f, GameConstants.WORLD_HEIGHT * 3);
		rightWall.createFixture(rBox, 0.0f);
		rBox.dispose();

		leftWall.getFixtureList().get(0).setFilterData(filter);
		rightWall.getFixtureList().get(0).setFilterData(filter);
	}

	@Override
	public void reInit() {
		
		yPlatformPos = 0;
		
		for (int i = 0; i < platforms.size; i++) {
			Platform p = platforms.get(i);
			yPlatformPos += yInterval;
			xPlatformPos = MathUtils.random(0, GameConstants.WORLD_WIDTH);
			p.setTransform(xPlatformPos, yPlatformPos, 0);
		}

		rightWall.setTransform(0, 0, 0);
		leftWall.setTransform(GameConstants.WORLD_WIDTH, 0, 0);
	}
}
