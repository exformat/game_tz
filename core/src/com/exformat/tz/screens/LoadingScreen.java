package com.exformat.tz.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exformat.tz.GameConstants;
import com.exformat.tz.managers.AudioManager;

public class LoadingScreen implements Screen
{

	private Game game;
	private AssetManager assetManager;
	private TextureAtlas atlas;
	
	private Viewport viewport;
	private SpriteBatch batch;
	
	private Texture splashTexture;
	private Sprite splashSprite;
	
	private AssetDescriptor<TextureAtlas> atlasDescriptor;
	
	
	private float timer = 0;

	public LoadingScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		viewport = new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
		viewport.apply(true);
		
		batch = new SpriteBatch();
		
		splashTexture = new Texture("LibGDX.png");
		
		splashSprite = new Sprite(splashTexture);
		splashSprite.setBounds(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT / 5);
		splashSprite.setOriginCenter();
		
		assetManager = new AssetManager();
		AudioManager.load(assetManager);
		atlasDescriptor = new AssetDescriptor<TextureAtlas>("atlas/tz.atlas", TextureAtlas.class);
		assetManager.load(atlasDescriptor);
	}

	@Override
	public void render(float dt) {
		ScreenUtils.clear(0.8f, 0.8f, 0.8f ,1, true);
		timer += dt;
		
		assetManager.update();
		if(assetManager.isFinished() && timer > 1){
			atlas = assetManager.get(atlasDescriptor);
			AudioManager.init(assetManager);
			GameScreen gs = new GameScreen(game, assetManager, atlas, batch);
			gs.init();
			
			
			game.setScreen(gs);
		}
		
		batch.begin();
			splashSprite.draw(batch);
		batch.end();
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
		splashTexture.dispose();
	}
}
