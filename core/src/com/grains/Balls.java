package com.grains;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Balls {
    //private static Sound tuck = Gdx.audio.newSound(Gdx.files.internal("boom.wav"));
    private final static Random             rand = new Random();
    static float                            Scale;
    static Texture                          texture = new Texture("bullet_t_3s.png");
    static float                            velK = 10f;
    static float                            Friction = 1;
    static boolean                          isBoarders, isGravity;
    private static HashMap<Integer, Balls>  ballsMap = new HashMap<Integer, Balls>();
    private static Vector2                  Gravity = new Vector2(0, -10);
    private static boolean                  toucheble = false;
    static float                            width = texture.getWidth();
    static float                            height = texture.getHeight();

    public Vector2                          position;
    private Vector2                         velocity;

    private Balls(Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public static void  setToucheble () {
        toucheble = !toucheble;
    }
    static void         config (float scale, boolean boarders, boolean gravity) {
        Scale = scale;
        isBoarders = boarders;
        isGravity = gravity;
    }
    static void         updateAll (Map<Integer, Walls> walls, float deltaTime) {
        //Balls.up_rule_balls_to_balls_x2();
        for (int i = 0; i < ballsMap.size(); i++) {
            ballsMap.get(i).update(walls, deltaTime);
        }
    }
    static void         drawAll (SpriteBatch batch) {
        for (int i = 0; i < ballsMap.size(); i++) {
            ballsMap.get(i).draw(batch);
        }
    }
    static void         MakeAllBalls (int j) {
        int count = ballsMap.size() + j;
        for (int i = ballsMap.size(); i < count; i++) {
            ballsMap.put(i, makeBall());
        }
    }
    static void         clearAllBalls () {
        ballsMap.clear();
    }
    public static Balls makeBall () {
        return (new Balls(new Vector2(rand.nextInt(Gdx.graphics.getWidth()), rand.nextInt(Gdx.graphics.getHeight() - 20) + 20),
                new Vector2((rand.nextFloat() - 0.5f) * velK, (rand.nextFloat() - 0.5f) * velK)));
    }
    public static void  delBall (int k) {
        for (int i = 0; i < k; i++) {
            ballsMap.remove(ballsMap.size() - 1);
        }
    }
    public void         update (Map<Integer, Walls> walls, float deltaTime) {
        this.up_rule_motion(deltaTime);
        //this.up_rule_touch();
        //this.up_rule_brakets();
        if (isBoarders) {
            //if (!isGravity) {
            this.up_rule_brakets_soundless();
            //}
            this.up_rule_out_wall(walls);
        } else {
            this.up_rule_unbrakets_soundless();
        }

        this.up_rule_to_touch();
        //this.up_rule_speed_limit();
        if (isGravity) {
            this.up_rule_gravity(deltaTime);
            //this.up_rule_grave_brakets_soundless();
        }
        //this.up_rule_min_velocity();
        //this.up_rule_in_wall(wall);
        //Balls.up_rule_balls_to_balls_x2();
    }
    public void         draw (SpriteBatch batch) {
        batch.draw(texture, this.position.x - width/2, this.position.y - height/2, width, height);
    }
    private void        up_rule_motion (float deltaTime) {
        this.position.add(velocity.cpy().scl(deltaTime).scl(velK));
        this.velocity.scl(Friction);
    }
    private void        up_rule_brakets_soundless () {


        if (this.position.x <= width/2) {
            this.velocity.x *= -1f;
            this.position.x = width/2;
        }                          //left GrandWall

        if (this.position.y <= height/2) {
            this.velocity.y *= -1f;
            this.position.y = height/2;
        }                          //Bottom

        if (this.position.x >= Gdx.graphics.getWidth() - width/2) {
            this.velocity.x *= -1f;
            this.position.x = Gdx.graphics.getWidth() - width/2;
        }    //right GrandWall

        if (this.position.y >= Gdx.graphics.getHeight() - height/2) {
            this.velocity.y *= -1f;
            this.position.y = Gdx.graphics.getHeight() - height/2;
        }   //Roof

    }
    private void        up_rule_unbrakets_soundless () {

        if (this.position.x <= -width) {
            this.position.x = Gdx.graphics.getWidth();
        }    //right GrandWall
        if (this.position.y <= -height) {
            this.position.y = Gdx.graphics.getHeight();
        }   //Roof
        if (this.position.x >= Gdx.graphics.getWidth() + width) {
            this.position.x = 0;
        }                          //left GrandWall
        if (this.position.y >= Gdx.graphics.getHeight() + height) {
            this.position.y = 0;
        }                          //Bottom
    }
    private void        up_rule_to_touch () {
        if (Control.isPressed() && toucheble) {
            this.velocity = this.position.cpy().sub(Control.TouchPoint()).nor().scl(-velK);
        }
    }
    private void        up_rule_gravity (float deltaTime) {
        if (velocity.len() < 55) {
            this.velocity.add(Gravity.cpy().scl(deltaTime).scl(velK));
        }
    }
    private void        up_rule_out_wall (Map<Integer, Walls> walls) {

        for (int i = 0; i < walls.size(); i++) {

            Walls wall = walls.get(i);
            if (wall.touchble) {
                float tx1 = this.position.x;
                float tx2 = this.position.x + width / 2;
                float wx1 = wall.position.x + width / 2;
                float wx2 = wall.position.x + wall.width - width / 2;

                float ty1 = this.position.y;
                float ty2 = this.position.y + height / 2;
                float wy1 = wall.position.y + height / 2;
                float wy2 = wall.position.y + wall.height - height / 2;

                if ((tx1 <= wx1) &&                       // left wall
                        (tx2 >= wx1) &&
                        (ty1 <= wy2) &&
                        (ty2 >= wy1)) {

                    this.position.x = wall.position.x - width;
                    this.velocity.x *= -1;
                }

                if ((tx1 <= wx2) &&                       // right wall
                        (tx2 >= wx2) &&
                        (ty1 <= wy2) &&
                        (ty2 >= wy1)) {

                    this.position.x = wall.position.x + wall.width;
                    this.velocity.x *= -1;
                }

                if ((tx2 >= wx1) &&                      // bottom
                        (tx1 <= wx2) &&
                        (ty1 <= wy1) &&
                        (ty2 >= wy1)) {

                    this.position.y = wall.position.y - height;
                    this.velocity.y *= -1;
                }

                if ((tx2 >= wx1) &&                      // roof
                        (tx1 <= wx2) &&
                        (ty1 <= wy2) &&
                        (ty2 >= wy2)) {

                    this.position.y = wall.position.y + wall.height;
                    this.velocity.y *= -1;
                }
            }
        }
    }
    public boolean overlaps_to_ball(Balls other) {
        float distance = this.position.dst2(other.position);
        return distance < 6;
    }

    private void        up_role_self_touch() {
        for(Balls ball : ballsMap.values()) {
            for(Balls ball1 : ballsMap.values()) {
                if (ball.overlaps_to_ball(ball1)) {

                }
            }
        }
    }
    /*
        private static void up_rule_balls_to_balls_x1() {
    for (int i = 0; i < ballsMap.size(); i++) {
        for (int j = 0; j < ballsMap.size(); j++) {
            if (i != j){
                Balls iball = ballsMap.get(i);
                Balls jball = ballsMap.get(j);

                float tx1 = iball.position.x;
                float tx2 = iball.position.x + width;
                float wx1 = jball.position.x;
                float wx2 = jball.position.x + width;

                float ty1 = iball.position.y;
                float ty2 = iball.position.y + height;
                float wy1 = jball.position.y;
                float wy2 = jball.position.y + height;

                if ((tx1 <= wx1) &&                       // left wall
                        (tx2 >= wx1) &&
                        (ty2 <= wy2) &&
                        (ty1 >= wy1)) {

                    iball.position.x = jball.position.x - width;
                    iball.velocity.x *= -1;
                }

                if ((tx1 <= wx2) &&                       // right wall
                        (tx2 >= wx2) &&
                        (ty2 <= wy2) &&
                        (ty1 >= wy1)) {

                    iball.position.x = jball.position.x + width;
                    iball.velocity.x *= -1;
                }

                if ((tx1 >= wx1) &&                      // bottom
                        (tx2 <= wx2) &&
                        (ty1 <= wy1) &&
                        (ty2 >= wy1)) {

                    iball.position.y = jball.position.y - height;
                    iball.velocity.y *= -1;
                }

                if ((tx1 >= wx1) &&                      // roof
                        (tx2 <= wx2) &&
                        (ty1 <= wy2) &&
                        (ty2 >= wy2)) {

                    iball.position.y = jball.position.y + height;
                    iball.velocity.y *= -1;
                }
            }
        }
    }
}
    private static void up_rule_balls_to_balls_x2() {
        int len = ballsMap.size();
        for (int i = 0; i < len/2; i++) {
            for (int j = 0; j < len; j++) {
                if (i != j){
                    Vector2 iball = ballsMap.get(i).position;
                    Vector2 jball = ballsMap.get(j).position;

                    if (iball.sub(jball).len2() < height * height){
                        ballsMap.get(i).velocity.scl(-1);
                        ballsMap.get(j).velocity.scl(-1);
                    }
                }
            }
        }
    }

        private void up_rule_brakets() {
        if (this.position.x < 0){this.velocity.x *= -1;
            //tuck.play(0.05f);
        }
        if (this.position.y < 0){this.velocity.y *= -1;
            //tuck.play(0.05f);
        }
        if (this.position.x > Gdx.graphics.getWidth()){this.velocity.x *= -1;
            //tuck.play(0.05f);
        }
        if (this.position.y > Gdx.graphics.getHeight()){this.velocity.y *= -1;
            //tuck.play(0.05f);
        }

    }


    private void up_rule_touch() {
        if (Control.isClicked()) {
            this.position = Control.TouchPoint();
        }
    }
    private void up_rule_grave_brakets_soundless() {


        if (this.position.x <= 0){
            this.velocity.x *= -1f;
            this.position.x = 0;}                          //left GrandWall

        if (this.position.y <= 0){
            this.velocity.y *= -1f;
            this.position.y = 0;}                          //Bottom

        if (this.position.x >= Gdx.graphics.getWidth() - width ){
            this.velocity.x *= -1f;
            this.position.x = Gdx.graphics.getWidth() - width ;}    //right GrandWall

        if (this.position.y >= Gdx.graphics.getHeight() - height ){
            this.velocity.y = 0.1f;
            this.position.y = Gdx.graphics.getHeight() - height ;}   //Roof

    }
    private void up_rule_speed_limit() {
        ch++;
        if (ch == 1000){
            for (int i = 0; i < ballsMap.size() ; i++) {
                if (ballsMap.get(i).velocity.len() > 20) {
                    ballsMap.get(i).velocity = new Vector2((rand.nextFloat() - 0.5f) * vel_K2, (rand.nextFloat() - 0.5f) * vel_K2);

                }
            }
            ch = 0;

        }
    }
    private void up_rule_min_velocity() {

        float stop = 0.3f;
        //float x = Math.abs(this.velocity.x);
        //float y = Math.abs(this.velocity.y);
        if(Math.abs(this.velocity.y) < stop){
            this.velocity.y = 0;
        }

        if (Math.abs(this.velocity.x) < stop ){
            this.velocity.x = 0;
        }
    }
    private void up_rule_in_wall(Walls wall) {


        if (this.position.x <= wall.position.x){
            this.velocity.x *= -1f;
            this.position.x = wall.position.x;}                          //left GrandWall

        if (this.position.y <= wall.position.y){
            this.velocity.y *= -1f;
            this.position.y = wall.position.y;}                          //Bottom

        if (this.position.x >= wall.position.x + wall.width){
            this.velocity.x *= -1f;
            this.position.x = wall.position.x + wall.width;}    //right GrandWall

        if (this.position.y >= wall.position.y + wall.height){
            this.velocity.y *= -1f;
            this.position.y = wall.position.y + wall.height;}   //Roof

    }
    */

}