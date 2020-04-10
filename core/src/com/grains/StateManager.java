package com.grains;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class StateManager {
    private MainGame game;
    private Stack<State> stateStack;


    StateManager(State start, MainGame _game) {
        game = _game;
        stateStack = new Stack<>();
        start.global = _game;
        stateStack.push(start);
        stateStack.peek().init();
    }

    public void push(State state) {
        stateStack.peek().pause();
        state.global = game;
        stateStack.push(state);
        stateStack.peek().init();
    }

    public void pop() {
        stateStack.peek().end();
        stateStack.pop();
        stateStack.peek().depause();
    }

    public void render(SpriteBatch batch) {
        stateStack.peek().render(batch);
    }
    public void dBugRender(){if (stateStack.peek() instanceof Debugble) {((Debugble) stateStack.peek()).dRender();}}
    public void update(float deltaTime) {
        stateStack.peek().update(deltaTime);
    }
    public void touch() {
        if (Control.isClicked() || Control.isPressed()) {
            stateStack.peek().touch();
        }
    }
}
