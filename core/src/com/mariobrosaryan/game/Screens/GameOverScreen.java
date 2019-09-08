package com.mariobrosaryan.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mariobrosaryan.game.MarioBros;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameOverScreen(Game game){
        this.game=game;
        viewport=new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,new OrthographicCamera());
        stage=new Stage(viewport, ((MarioBros) game).batch);

        Label.LabelStyle font=new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Label.LabelStyle font1= new Label.LabelStyle(new BitmapFont(), Color.YELLOW);


        Table table=new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER",font);

        Label playAgainLabel = new Label("Click to Play Again",font);
        Label developedLabel= new Label("Developed By - Codingee Games" ,font1 );
        developedLabel.setFontScale(0.8f);
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);
        table.row();
        table.add(developedLabel).expandX().padTop(30f);


        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            game.setScreen(new PlayScreen((MarioBros) game));
            dispose();
        }
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();

    }
}
