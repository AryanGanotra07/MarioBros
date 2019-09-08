package com.mariobrosaryan.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mariobrosaryan.game.MarioBros;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private static Integer score;
    private float timeCount;

    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLable;
    private Label worldLabel;
    private Label marioLabel;

    boolean upPressed,downPressed,leftPressed, rightPressed;

    public Hud(SpriteBatch sb){
        worldTimer=300;
        timeCount=0;
        score=0;

        viewport=new FitViewport(MarioBros.V_WIDTH,MarioBros.V_HEIGHT,new OrthographicCamera());
        stage=new Stage(viewport,sb);

        Table table=new Table();
        //Table table1=new Table();


        table.top();
        table.setFillParent(true);


        countdownLabel=new Label(String.format("%03d",worldTimer),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        scoreLabel=new Label(String.format("%06d",score),new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        timeLabel=new Label("TIME",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        levelLable=new Label("1-1",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        worldLabel=new Label("WORLD",new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        marioLabel=new Label("MARIO",new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLable).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

      /*  table1.bottom().left();



        Gdx.input.setInputProcessor(stage);

        Image upImg=new Image(new Texture("flatDark25.png"));
        upImg.setSize(35,35);
        upImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=false;

            }
        });

        Image downImg=new Image(new Texture("flatDark26.png"));
        downImg.setSize(35,35);
        downImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=false;

            }
        });

        Image rightImg=new Image(new Texture("flatDark24.png"));
        rightImg.setSize(35,35);
        rightImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=false;

            }
        });

        Image leftImg=new Image(new Texture("flatDark23.png"));
        leftImg.setSize(35,35);
        leftImg.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=true;
               return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=false;

            }
        });




        table1.add();
        table1.add(upImg).size(upImg.getWidth(),upImg.getHeight());
        table1.add();
        table1.add();
        table1.add();
        table1.add();
        table1.add();
        table1.add();

        table1.row().pad(5,5,5,5);
        table1.add();
        table1.add();
        table1.add();
        table1.add();
        table1.add();



        table1.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight());
        table1.add();
        table1.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        table1.row().pad(5,5,5,5);
        table1.add();
        table1.add(downImg).size(downImg.getWidth(),downImg.getHeight());
        table1.add();
        table1.add();
        table1.add();
        table1.add();
        table1.add();
        table1.add();


        stage.addActor(table1); */



        }


        public void resize(int width,int height){
        viewport.update(width,height);

        }



    public void update(float dt){
        timeCount+=dt;
        if (timeCount>=1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount=0;
        }
    }

    public static void addScore(int value){
        score +=value;
        scoreLabel.setText(String.format("%06d",score));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
}
