package com.mariobrosaryan.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mariobrosaryan.game.Items.Item;
import com.mariobrosaryan.game.Items.ItemDef;
import com.mariobrosaryan.game.Items.Mushroom;
import com.mariobrosaryan.game.MarioBros;
import com.mariobrosaryan.game.Scenes.Hud;
import com.mariobrosaryan.game.Sprites.Enemies.Enemy;
import com.mariobrosaryan.game.Sprites.Mario;
import com.mariobrosaryan.game.Tools.B2Worldcreater;
import com.mariobrosaryan.game.Tools.worldcontactlistener;
import com.mariobrosaryan.game.controller;

import java.util.concurrent.LinkedBlockingDeque;


public class PlayScreen implements Screen {
    private MarioBros game;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Mario player;
    private Music music;
    private B2Worldcreater creater;

    private Array<Item> items;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;
    private controller controller;



    public PlayScreen(MarioBros game) {


        atlas=new TextureAtlas("Mario_and_enemies.atlas");

        this.game = game;
        controller=new controller(game.batch);

        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creater=new B2Worldcreater(this);

        player=new Mario(this);

        world.setContactListener(new worldcontactlistener());

        music= MarioBros.manager.get("audio/music/mario_music.ogg",Music.class);
        music.setLooping(true);
        music.play();

        items= new Array<Item>();
        itemsToSpawn=new LinkedBlockingDeque<ItemDef>();



    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if (!itemsToSpawn.isEmpty()){
            ItemDef idef=itemsToSpawn.poll();
            if (idef.type==Mushroom.class){
                items.add(new Mushroom(this,idef.position.x,idef.position.y));
            }
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }


    public void handleInput(float dt){
        if (player.currentState!=Mario.State.DEAD) {
            if (controller.isUpPressed() && player.b2body.getLinearVelocity().y==0) {
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }
            if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

            }
            if (controller.isLeftPressed() && player.b2body.getLinearVelocity().x >= -2) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            }
        }
    }

    public void update(float dt){

        handleInput(dt);
        handleSpawningItems();


        world.step(1/60f,6,2);

        gamecam.position.x=player.b2body.getPosition().x;



       player.update(dt);
       for (Enemy enemy:creater.getEnemies()){
           if (enemy.getX()<player.getX()+224/MarioBros.PPM){
               enemy.b2body.setActive(true);
           }
           enemy.update(dt);

       }

       for (Item item: items)
           item.update(dt);
       hud.update(dt);

if (player.currentState!=Mario.State.DEAD) {
    gamecam.update();

}

        renderer.setView(gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world,gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (Enemy enemy:creater.getEnemies()){
            enemy.draw(game.batch);

        }

        for(Item item: items)
            item.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if (Gdx.app.getType()==Application.ApplicationType.Android) {
            controller.draw();
        }
        controller.draw();

        if (gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

    }

    public boolean gameOver(){
        if (player.currentState== Mario.State.DEAD && player.getStateTimer() > 3) {
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {

        gameport.update(width,height);
       controller.resize(width,height);

    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
        hud.dispose();


    }
}
