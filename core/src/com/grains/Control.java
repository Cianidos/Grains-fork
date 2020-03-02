package com.grains;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

class Control {

    static boolean isPressed(){
        return Gdx.input.isTouched();
    }
    static boolean isClicked(){
        return Gdx.input.justTouched();
    }

    static Vector2 TouchPoint(){
        return new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight()- Gdx.input.getY());
    }
}
