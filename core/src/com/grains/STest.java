package com.grains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;

public class STest extends State /*implements debugble*/ {
    HashMap<Integer, Balls> balls;
    ShapeRenderer shapeRenderer;
    BitmapFont bitmapFont;

    @Override
    public void init() {
        Balls.setToucheble();
        //Balls.config(1,false,false);
        balls = new HashMap<Integer, Balls>();
       // bitmapFont = new BitmapFont(Gdx.files.internal("./assets/hello.fnt"));

        for (int i = 0; i < 1000; i++) {
            balls.put(i, Balls.makeBall());
        }
        shapeRenderer = new ShapeRenderer();
        Balls.width /=2;
        Balls.height /=2;
    }

    @Override
    public void end() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void depause() {

    }

    @Override
    public void render(SpriteBatch batch) {
        for(Balls ball : balls.values()) {
            ball.draw(batch);
        }
        bitmapFont.draw(batch, "hello", 500, 500);
    }
    //@Override
    public void dRender() {
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GOLDENROD);


        for (Balls ball :
                balls.values()) {
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(ball.position.x, ball.position.y, Balls.width);
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.circle(ball.position.x, ball.position.y, 4);

        }
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.circle(Control.TouchPoint().x, Control.TouchPoint().y, 5);
        shapeRenderer.end();
    }


    @Override
    public void update(float deltaTime) {
        for(Balls ball : balls.values()) {
            ball.update(deltaTime);
        }
    }

    @Override
    public void touch() {

    }
}
