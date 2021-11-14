package com.exformat.tz.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;
import com.exformat.tz.GameConstants;
import com.exformat.tz.control.Control;
import com.exformat.tz.game_objects.Grass;
import com.exformat.tz.game_objects.Ground;
import com.exformat.tz.game_objects.NumberTable;
import com.exformat.tz.game_objects.Player;
import com.exformat.tz.managers.AbstractManager;
import com.exformat.tz.managers.AudioManager;
import com.exformat.tz.managers.BackgroundManager;
import com.exformat.tz.managers.PlatformManager;
import com.exformat.tz.model.GameObject;
import com.exformat.tz.physics.B2DContactListener;
import com.exformat.tz.render.GameObjectsRenderer;

public class GameScreen implements Screen
{
	private Game game;
	
	private AssetManager assetManager;
	private TextureAtlas atlas;
	
	private Viewport viewport;
	private SpriteBatch batch;
	
	private World world;
	private GameObjectsRenderer render;
	private Array<GameObject> gameObjects;

	private boolean init = false;
	
	private Array<AbstractManager> managers;
	
	private Player player;
	private NumberTable table;
	private int maxFloor = 0;
	
	Preferences preferences;
	
	public GameScreen(Game game, AssetManager assetManager, TextureAtlas atlas, SpriteBatch batch) {
		this.game = game;
		this.assetManager = assetManager;
		this.atlas = atlas;
		this.batch = batch;
	}

	@Override
	public void show() {
		init();
		AudioManager.music.play();
		AudioManager.music.setLooping(true);
		
		preferences = Gdx.app.getPreferences("prefs");
		maxFloor = preferences.getInteger("max floor");
	}

	@Override
	public void render(float dt) {
		ScreenUtils.clear(0,0,0,1);
		
		world.step(0.016f, 6, 2);
		render.draw();
		
		viewport.getCamera().position.y = player.getY();
		
		for(int i = gameObjects.size; --i >= 0;){
			gameObjects.get(i).update(dt);
		}
		
		for(AbstractManager manager: managers){
			manager.update(dt, viewport.getCamera().position.y);
		}
		
		int floor = (int)player.getY() / 3;
		if(floor == 0){
			floor = maxFloor;
		}
		
		table.setFloor(floor);
		table.setPosition(table.getX(), 8 + player.getY());
		
		if(floor > maxFloor){
			maxFloor = floor;
			preferences.putInteger("max floor", maxFloor);
			preferences.flush();
		}
	}

	@Override
	public void resize(int w, int h) {
		viewport.update(w, h, true);
		batch.setProjectionMatrix(viewport.getCamera().combined);
	}

	@Override
	public void pause() {
		// TODO: Implement this method
	}

	@Override
	public void resume() {
		// TODO: Implement this method
	}

	@Override
	public void hide() {
		// TODO: Implement this method
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		world.dispose();
		atlas.dispose();
		batch.dispose();
	}
	
	public void init(){
		if(!init){
			init = true;
			
			world = new World(new Vector2(0, -9.8f), true);
			world.setContactListener(new B2DContactListener());
			
			gameObjects = new Array<>();
			managers = new Array<>();
			viewport = new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
			viewport.apply(true);
			render = new GameObjectsRenderer(gameObjects, (OrthographicCamera)viewport.getCamera(), batch, world);
			
			
			
			player = new Player(world, atlas);
			player.setTransform(GameConstants.WORLD_WIDTH / 2, 2, 0);
			
			table = new NumberTable(atlas, new Vector2(9.5f, 19));
			
			PlatformManager platformManager = new PlatformManager(world, atlas);
			BackgroundManager backgroundManager = new BackgroundManager(atlas);
			
			managers.add(platformManager,
						 backgroundManager);
			
			
			//TODO n спрайт отрисовывается первым. 0 последним.
			gameObjects.add(new Grass(atlas));
			gameObjects.add(table);
			gameObjects.addAll(platformManager.create());
			gameObjects.add(new Ground(world, atlas));
			gameObjects.add(player);
			gameObjects.addAll(backgroundManager.create());
			
			Control control = new Control(player);
			
			
			//TODO костыль для вытаскивания всех спрайтов из 0х0у
			for(int i = gameObjects.size; --i >= 0;){
				gameObjects.get(i).update(0.016f);
			}
		}
	}
}
