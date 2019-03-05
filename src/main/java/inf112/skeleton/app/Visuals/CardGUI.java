package inf112.skeleton.app.Visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import inf112.skeleton.app.GameMechanics.Board.Board;
import inf112.skeleton.app.GameMechanics.Cards.Card;

import java.util.HashMap;
import java.util.List;

public class CardGUI {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private List<Card> cards;
    private Board board;
    private ImageButton[] buttonArr;
    Stage stage;
    SpriteSheet spriteSheet;
    private Card[] cardSeq;
    private int cardPtr;
    private int selectedCardDrawPos;
    private HashMap<Integer, ImageButton> buttonByXPos = new HashMap<>();
    private Image infoBar;
    private int labelXPos;

    public CardGUI(OrthographicCamera camera, SpriteBatch batch, Board board, List<Card> cards) {
        this.camera = camera;
        this.batch = batch;
        this.cards = cards;
        this.board = board;
        this.buttonArr = new ImageButton[cards.size()];
        stage = new Stage(new ScreenViewport());
        spriteSheet  = new SpriteSheet();
        this.camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cardSeq = new Card[5];
        cardPtr = 0;
        selectedCardDrawPos = 0;

        create();
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void create() {
        int xpos = 0;
        for (int i = 0; i < buttonArr.length; i++) {
            buttonArr[i] = new ImageButton(new TextureRegionDrawable(spriteSheet.getTexture(cards.get(i))));
            buttonArr[i].setSize(97, 135);
            buttonArr[i].setPosition(xpos, 0);

            buttonByXPos.put(xpos, buttonArr[i]);

            xpos += 97;
            stage.addActor(buttonArr[i]);
            labelXPos = 0;
        }

        for (int i = 0; i < buttonArr.length; i++) {
            final int finalI = i;
            buttonArr[i].addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (cardPtr < 5) {
                        cardSeq[cardPtr] = cards.get(finalI);

                        addLabel(cardPtr);
                        swapCardPlacement(buttonArr[finalI], getCardByX(drawPos(cardPtr)));
                        cardPtr++;

                    }
                    System.out.println("card priority: " + cards.get(finalI).getPriority());
                    return true;
                }
            });
        }

        xpos += 10;

        ImageButton clear = new ImageButton(new TextureRegionDrawable(spriteSheet.getTexture(SpriteType.CARD_CLEAR)));
        ImageButton submit = new ImageButton(new TextureRegionDrawable(spriteSheet.getTexture(SpriteType.CARD_SUBMIT)));
        infoBar = new Image(new TextureRegionDrawable(spriteSheet.getTexture(SpriteType.CARD_BAR)));

        infoBar.setSize(485, 30);
        infoBar.setPosition(0,135);
        infoBar.addAction(Actions.sequence(Actions.fadeOut(0.15f), Actions.fadeIn(0.15f)));
        stage.addActor(infoBar);

        clear.setSize(80,30);
        clear.setPosition(xpos, 20);
        stage.addActor(clear);
        submit.setSize(80,30);
        submit.setPosition(xpos, 70);
        stage.addActor(submit);

        clear.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                cardPtr = 0;
                stage.clear();
                create();
                System.out.println("clear");
                return true;
            }
        });

        submit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.print("submit: ");
                for (int i = 0; i < cardPtr; i++) {
                    System.out.print(cardSeq[i].toString() + ", ");
                }
                return true;
            }
        });


        Gdx.input.setInputProcessor(stage);
    }

    private void addLabel(int cardPtr) {
        if (cardPtr == 0) {
            infoBar.remove();
        }

        String filename = "button" + (cardPtr+1);
        Image label = new Image(new TextureRegionDrawable(new Texture("CardImages/" + filename + ".png")));
        label.setSize(97, 30);
        label.setPosition(labelXPos,135);
        stage.addActor(label);
        labelXPos += 97;
    }

    private ImageButton getCardByX(int xPos) {
        ImageButton b = buttonByXPos.get(xPos);
        return b;
    }

    private int drawPos(int cardPtr) {
        return cardPtr*97;
    }

    private void swapCardPlacement(ImageButton a, ImageButton b) {
        int aXPos = (int)a.getX();
        int bXPos = (int)b.getX();

        a.setPosition(bXPos, 0);
        b.setPosition(aXPos, 0);

        buttonByXPos.put(bXPos, a);
        buttonByXPos.put(aXPos, b);
    }

}
