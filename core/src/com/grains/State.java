package com.grains;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {
    public MainGame global;
    public boolean debug = false;
    public abstract void init();
    public abstract void end();
    public abstract void pause();
    public abstract void depause();
    public abstract void render(SpriteBatch batch);
    public abstract void update(float deltaTime);
    public abstract void touch();
}
