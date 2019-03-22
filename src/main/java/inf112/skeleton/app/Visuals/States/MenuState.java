package inf112.skeleton.app.Visuals.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.GameMechanics.Board.Board;
import inf112.skeleton.app.Visuals.RoboRally;
import inf112.skeleton.app.Visuals.SpriteSheet;
import inf112.skeleton.app.Visuals.SpriteType;

public class MenuState extends State {

    private SpriteSheet spriteSheet;
    private TextureRegion background;
    //private TextureRegion playButton;
    //private ImageButton startButton;
    private Image startButton;

    private Stage stage;

    private boolean start;

    public MenuState(GameStateManager gsm, Board board) {
        super(gsm, board);

        this.spriteSheet = new SpriteSheet();
        this.stage = new Stage(new ScreenViewport());

        this.stage.getBatch().setProjectionMatrix(camera.combined);
        this.background = this.spriteSheet.getTexture(SpriteType.MENU_BACKGROUND);
        //this.playButton = this.spriteSheet.getTexture(SpriteType.MENU_PLAY_BUTTON);

        //set start button

        /*this.startButton = new ImageButton(new TextureRegionDrawable(this.playButton));
        this.startButton.setSize(192, 49);
        this.startButton.setPosition((RoboRally.WIDTH / 2) - (this.playButton.getRegionWidth()/2), RoboRally.HEIGHT-(this.playButton.getRegionHeight()*3));
        this.stage.addActor(this.startButton);
        */
        this.startButton = new Image(new TextureRegionDrawable(new Texture("StateImages/start.png")));
        this.startButton.setSize(192, 49);
        this.startButton.setPosition((RoboRally.WIDTH / 2) - (192/2), RoboRally.HEIGHT-(49*3));
        this.stage.addActor(this.startButton);

        this.start = false;
        clickable();
    }

    private void clickable() {
        this.startButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Start game!");
                start = true;
                return true;
            }
        });
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void handleInput() {
        if (this.start) {
            gsm.set(new ChooseBoardState(gsm, board));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render() {
        this.stage.act();
        this.stage.getBatch().begin();
        this.stage.getBatch().draw(this.background, 0, 0, RoboRally.WIDTH, RoboRally.HEIGHT);
        this.stage.getBatch().end();
        this.stage.draw();
    }

    @Override
    public void dispose() {
        this.spriteSheet.dispose();
    }

    @Override
    public void resize() {
        super.resize();
        //this.stage.getBatch().setProjectionMatrix(camera.combined);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }
}

