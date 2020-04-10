package com.grains;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SGame extends State {

    private Button setts;

    public SGame(float HEIGHT_K) {
        Balls.MakeAllBalls(100);
        Balls.config(HEIGHT_K, true, false);
        setts = new Button(new Vector2(0, 0), 0.5f * HEIGHT_K, 0.5f * HEIGHT_K, new Texture("settgear.png"));
    }

    @Override
    public void init() {
        Balls.setToucheble();
    }

    @Override
    public void end() {

    }

    @Override
    public void pause() {
        Balls.setToucheble();
    }

    @Override
    public void depause() {
        Balls.setToucheble();
    }

    @Override
    public void render(SpriteBatch batch) {
        Balls.drawAll(batch);
        setts.draw(batch);
    }

    @Override
    public void update(float deltaTime) {
        Balls.updateAll(deltaTime);
    }

    @Override
    public void touch() {
        if (Control.isClicked() && setts.overlaps(Control.TouchPoint())) {
            global.sMngr.push(global.sSettings);
        }
    }
}
