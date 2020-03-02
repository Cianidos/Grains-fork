package com.grains;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

public class Walls {

    static Map<Integer, Walls> wallsMap = new HashMap<Integer, Walls>();

    static void updateAll(){
        for (int i = 0; i < wallsMap.size() ; i++) {
            wallsMap.get(i).update();
        }
    }

    static void drawAll(SpriteBatch batch){
        for (int i = 0; i < wallsMap.size() ; i++) {
            wallsMap.get(i).draw(batch);
        }
    }

    public static void makeWall(Vector2 pos, float width, float height){
        new Walls(pos, width, height);
    }

    Texture texture = new Texture("empty-texture_1.png");
    Vector2 position;
    float width;
    float height;
    int id;
    boolean touchble;

    Walls(Vector2 position, float width, float height) {
        //this.texture = new Texture("empty-texture_1.png");
        this.position = position;
        this.width = width;
        this.height = height;
        wallsMap.put(wallsMap.size(),this);
    }
    Walls(Vector2 position, float scaleX, float scaleY , Texture texture) {
        this.id = 1;
        this.texture = texture;
        this.position = position;
        this.width = texture.getWidth() * scaleX;
        this.height = texture.getHeight() * scaleY;
        this.touchble = false;
        wallsMap.put(wallsMap.size(),this);
    }

    boolean overlaps(Vector2 point) {
        return point.x < position.x + width &&
                point.x > position.x &&
                point.y < position.y + height &&
                point.y > position.y;
    }
    void update(){

    }

    void draw(SpriteBatch batch){
        batch.draw(this.texture, this.position.x, this.position.y, this.width, this.height);
    }

}
