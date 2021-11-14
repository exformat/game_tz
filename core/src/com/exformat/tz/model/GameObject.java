package com.exformat.tz.model;

import static com.exformat.tz.GameConstants.DIVIDER;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.Gdx;
import com.exformat.tz.physics.BodyEditorLoader;

public class GameObject extends Sprite {
	
	protected String TAG = "";
	
	protected World world; 
	protected Fixture fixture; 
	protected Body body = null; 
	protected Filter filter; 
	protected float halfWidth = 0; 
	protected float halfHeight = 0; 
	
	protected boolean isVisible = true; 
	
	private boolean isTransform = false;
	private float xTransform = 0;
	private float yTransform = 0;
	private float aTransform = 0;
	
	private boolean isAnimation = false; 
	private float currentTime = 0; 
	protected Animation<TextureRegion> animation; 

	public GameObject(World world, TextureRegion region) { 
		super(region); 
		setBounds(0, 0, getWidth() / DIVIDER, getHeight() / DIVIDER);
		setOriginCenter(); 
		halfWidth = getWidth() / 2; 
		halfHeight = getHeight() / 2; 
		this.world = world; 
		filter = new Filter(); 
	} 

	public GameObject(World world, TextureRegion region, int columns, int rows, float frameTime) { 
		this.world = world; 
		TextureRegion[] reg = new TextureRegion[columns * rows]; 
		TextureRegion[][] regs = region.split(region.getRegionWidth() / columns, region.getRegionHeight() / rows); 
		int index = 0; 
		for (int i = 0; i < rows; i++) { 
			for (int a = 0; a < columns; a++) { 
				reg[index++] = regs[i][a];
			}
		}

		animation = new Animation<TextureRegion>(frameTime, reg);
		isAnimation = true;
		setRegion(animation.getKeyFrame(0));
		setBounds(0, 0, getWidth() / DIVIDER, getHeight() / DIVIDER);
		setOriginCenter();
		halfWidth = getWidth() / 2;
		halfHeight = getHeight() / 2;
		this.world = world;
		filter = new Filter();
	}

	public void update(float dT) {
		if(body != null){
			if(isTransform){
				isTransform = false;
				body.setTransform(xTransform, yTransform, aTransform); 
			}

			setRotation(body.getAngle() * MathUtils.radiansToDegrees);
			setPosition(body.getPosition().x - halfWidth, body.getPosition().y - halfHeight); 
		}
		
		if (isAnimation) {
			currentTime += dT;
			setRegion(animation.getKeyFrame(currentTime, true));
		}
	} 

	@Override 
	public void draw(Batch batch, float alphaModulation) { 
		if (isVisible) {
			super.draw(batch, alphaModulation);
		}
	} 

	protected Body buildModel(BodyEditorLoader loader, String name, BodyDef.BodyType type, Vector2 position, float scl){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(position);
		bodyDef.angle = 0;
		body = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0.55f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.3f;
		
		loader.attachFixture(body, name, fixtureDef, scl);
		return body;
	}
	
	protected Body buildModel(BodyEditorLoader loader, String name, BodyDef.BodyType type, FixtureDef fixtureDef, Vector2 position, float scl){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(position);
		bodyDef.angle = 0;
		body = world.createBody(bodyDef);

		loader.attachFixture(body, name, fixtureDef, scl);
		return body;
	}
	
	protected Body builderBox(BodyDef.BodyType type, Vector2 position, float width, float height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(position);
		bodyDef.angle = 0;
		body = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(halfWidth, halfHeight);
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;
		fixture = body.createFixture(fixtureDef);
		shape.dispose();
		return body;
	} 

	protected Body builderBoxSensor(BodyDef.BodyType type, Vector2 position, float width, float height, float deg) { 
		BodyDef bodyDef = new BodyDef(); 
		bodyDef.type = type; 
		bodyDef.position.set(position); 
		bodyDef.angle = deg; 
		body = world.createBody(bodyDef); 
		FixtureDef fixtureDef = new FixtureDef(); 
		PolygonShape shape = new PolygonShape(); 
		shape.setAsBox(halfWidth, halfHeight); 
		fixtureDef.shape = shape; 
		fixtureDef.isSensor = true; 
		fixture = body.createFixture(fixtureDef); 
		shape.dispose(); 
		
		return body; 
	}
	
	protected Fixture buildSensor(Vector2 position, float width, float height, float deg) { 
		
		FixtureDef fixtureDef = new FixtureDef(); 
		PolygonShape shape = new PolygonShape(); 
		shape.setAsBox(halfWidth, halfHeight); 
		fixtureDef.shape = shape; 
		fixtureDef.isSensor = true; 
		Fixture fixtureSensor = body.createFixture(fixtureDef); 
		shape.dispose(); 

		return fixtureSensor; 
	}
	
	protected Fixture buildCircleSensor(float radius) { 
		
		FixtureDef fixtureDef = new FixtureDef(); 
		CircleShape shape = new CircleShape(); 
		shape.setRadius(radius); 
		fixtureDef.shape = shape; 
		Fixture fixtureSensor = body.createFixture(fixtureDef); 
		fixtureSensor.setSensor(true);
		shape.dispose();

		return fixtureSensor; 
	}
	
	protected Body builderCircle(BodyDef.BodyType type, Vector2 position, float radius) { 
		BodyDef bodyDef = new BodyDef(); 
		bodyDef.type = type; 
		body = world.createBody(bodyDef); 
		FixtureDef fixtureDef = new FixtureDef(); 
		CircleShape shape = new CircleShape(); 
		shape.setRadius(radius); 
		fixtureDef.shape = shape; 
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f; 
		fixtureDef.restitution = 0.6f; 
		fixture = body.createFixture(fixtureDef); 
		shape.dispose(); 
		body.setTransform(position, 0); 
		
		return body; 
	}
	
	public void destroy() { 
		isVisible = false; 
		body.setActive(false); 
		world.destroyBody(body); 
	} 
	
	protected void setFilter(short cat, short mask) { 
		filter.categoryBits = cat; 
		filter.maskBits = mask; 
		fixture.setFilterData(filter); 
	} 
	
	protected void setFilter(short cat, short mask, Fixture fixture) { 
		filter.categoryBits = cat; 
		filter.maskBits = mask; 
		fixture.setFilterData(filter); 
	} 
	
	public void setTransform(Vector2 position, float angle) { 
		setTransform(position.x, position.y, angle); 
	} 
	
	public void setTransform(float x, float y, float angle) { 
		isTransform = true;
		xTransform = x;
		yTransform = y;
		aTransform = angle;
	} 
	
	public void setVisible(boolean visible) { 
		this.isVisible = visible; 
		if (!visible) { 
			body.setActive(visible); 
		} 
	} 
	
	protected void log(String s){
		Gdx.app.log(TAG, s);
	}
	
	public boolean isVisible() { 
		return isVisible; 
	} 
	
	public boolean isAnimation() { 
		return isAnimation; 
	} 
	
	public void setHalfWidth(float halfWidth) {
		this.halfWidth = halfWidth;
	}

	public float getHalfWidth() {
		return halfWidth;
	}

	public void setHalfHeight(float halfHeight) {
		this.halfHeight = halfHeight;
	}

	public float getHalfHeight() {
		return halfHeight;
	} 
	
	public void setBody(Body body) {
		this.body = body;
	}

	public Body getBody() {
		return body;
	}
	
	public Vector2 getPosition(){
		return body.getPosition();
	}
	
	public void dispose(){
		
	}
}
