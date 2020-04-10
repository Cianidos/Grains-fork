package com.grains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.HeadlessException;



public class MainGame extends ApplicationAdapter {
	private  SpriteBatch batch;
	public   StateManager sMngr;
	public   SGame sGame;
	public   SSettings sSettings;
	public   STest sTest;


	private static float WIDTH_K;
	private static float HEIGHT_K;


	@Override
	public void create () {
		WIDTH_K = Gdx.graphics.getWidth() / 1240f;
		HEIGHT_K = Gdx.graphics.getHeight() / 720f;

		batch = new SpriteBatch();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sGame = new SGame(HEIGHT_K);
		sSettings = new SSettings(WIDTH_K, HEIGHT_K);
		sTest = new STest();
		sMngr = new StateManager(sGame, this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		touch();
		sMngr.dBugRender();

		sMngr.update(Gdx.graphics.getDeltaTime());

		batch.begin();
		sMngr.render(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void touch() {
		sMngr.touch();
	}
}