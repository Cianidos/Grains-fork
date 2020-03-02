package com.grains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;

public class STest extends State implements debugble {
    HashMap<Integer, Balls> balls;
    HashMap<Integer, Walls> walls;
    ShapeRenderer shapeRenderer;

    @Override
    public void init() {
        Balls.setToucheble();
        //Balls.config(1,false,false);
        balls = new HashMap<Integer, Balls>();
        for (int i = 0; i < 100; i++) {
            balls.put(i, Balls.makeBall());
        }
        walls = new HashMap<>();
        shapeRenderer = new ShapeRenderer();
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
    }
    @Override
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
            ball.update(walls, deltaTime);
        }
    }

    @Override
    public void touch() {

    }
}
