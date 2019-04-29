package inf112.skeleton.app.Visuals;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.GameMechanics.Cards.Card;
import inf112.skeleton.app.GameMechanics.Cards.CardManager;
import inf112.skeleton.app.GameMechanics.Player;
import org.lwjgl.Sys;

import java.util.HashSet;
import java.util.List;

public class CardGUI {
    private Stage stage;
    private CardManager cardManager;
    private AssetHandler assetHandler;

    private Table table;
    private Table buttonTable;

    private Player currentPlayer;
    private List<Card> currentCards;
    private String playerTurn;

    private Card[] tempCardSeq;
    private HashSet<Card> selectedCards;
    private int cardsToSelect;

    private ImageButton clear;
    private ImageButton submit;
    private ImageButton powerDown;

    public CardGUI(CardManager cardManager, Stage stage, AssetHandler assetHandler) {
        this.stage = stage;
        this.cardManager = cardManager;
        this.assetHandler = assetHandler;

        table = new Table();
        table.bottom().left();
        table.setFillParent(true);
        buttonTable = new Table();

        clear = new ImageButton(new TextureRegionDrawable(assetHandler.getTexture(SpriteType.CARD_CLEAR)));
        submit = new ImageButton(new TextureRegionDrawable(assetHandler.getTexture(SpriteType.CARD_SUBMIT)));
        powerDown = new ImageButton(new TextureRegionDrawable(assetHandler.getTexture("powerDown.png")));

        cardManager.newRound();
        createOptionButtons();
        selectCards();
    }

    public void dispose() {
        table.clearChildren();
        buttonTable.clearChildren();
    }

    private void selectCards() {
        if (cardManager.hasNotReadyPlayers()) {
            currentPlayer = cardManager.getPlayer();
            playerTurn = currentPlayer.getPlayerID() + "'s turn";
            currentCards = currentPlayer.getCardHand();


            if (currentPlayer.getPowerDown() == 3) {
                //drawPowerDownOptions(currentCards);
            } else {

                tempCardSeq = new Card[5];
                selectedCards = new HashSet<>();
                cardsToSelect = 5;


                for (int i = 0; i < currentCards.size(); i++) {
                    if (cardManager.isLocked(currentCards.get(i))) {
                        tempCardSeq[i] = currentCards.get(i);
                        cardsToSelect--;
                    }
                }
                draw(currentCards);
            }
        }
    }

    private void draw(List<Card> cards) {
        table.clearChildren();

        Label infoField = new Label(playerTurn, assetHandler.getSkin());
        table.add(infoField).expand().top().left().row();

        //handle indicators over cards
        for (int i = 0; i < cards.size(); i++) {
            if (cardManager.isLocked(cards.get(i))) {
                addLockLabel();
            } else if (selectedCards.contains(cards.get(i))) {
                addSelectLabel(i);
            } else {
                table.add(); //empty cell
            }
        }

        table.row();

        for (int i = 0; i < cards.size(); i++) {
            putCardInTable(cards.get(i));
        }
        table.add(buttonTable).expandX().right();
        stage.addActor(table);
    }

    private void addSelectLabel(int index) {
        String filename = "button" + (index + 1);
        Image image = new Image(new TextureRegion(assetHandler.getTexture("CardImages/" + filename + ".png")));
        table.add(image).height(30).width(97);
    }

    private void addLockLabel() {
        Image lock = new Image(new TextureRegion(assetHandler.getTexture("lock.png")));
        table.add(lock).height(30).width(97);
    }

    private void putCardInTable(Card c) {
        final Card card = c;
        final ImageButton cardButton = new ImageButton(new TextureRegionDrawable(assetHandler.getTexture(c)));

        cardButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!selectedCards.contains(cardButton) && cardsToSelect > 0 && !cardManager.isLocked(card)) {
                    putInTempSeq(card);
                    swapCards(card);
                    cardsToSelect--;
                }
                if (cardManager.isLocked(card)) {
                    System.out.println("CARD IS LOCKED IN HAND");
                }
                System.out.println("Card priority: " + card.getPriority() + ", card type: " + card.getCardType());
                draw(currentCards);
                return true;
            }
        });
        table.add(cardButton).width(97).height(135);
    }

    private void swapCards(Card card) {
        for (int i = 0; i < currentCards.size(); i++) {
            if (currentCards.get(i) == card) {
                break;
            }
            if (!selectedCards.contains(currentCards.get(i)) && !cardManager.isLocked(currentCards.get(i))) {
                System.out.println("wut");
                Card temp = currentCards.get(i);
                int tempPos = currentCards.indexOf(temp);
                int swapPos = currentCards.indexOf(card);

                currentCards.set(tempPos, card);
                currentCards.set(swapPos, temp);
                break;
            }
        }
    }

    private void putInTempSeq(Card card) {
        for (int i = 0; i < tempCardSeq.length; i++) {
            if (tempCardSeq[i] == null) {
                tempCardSeq[i] = card;
                selectedCards.add(card);
                break;
            }
        }
    }

    private void createOptionButtons() {

        clear.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Clear");
                selectCards();
                return true;
            }
        });

        submit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (cardManager.setCardSeq(currentPlayer, tempCardSeq)) {
                    System.out.print("Cards submitted: ");
                    for (int i = 0; i < tempCardSeq.length; i++) {
                        System.out.print(tempCardSeq[i].toString() + ", ");
                    }
                    System.out.println();
                    selectCards();
                } else {
                    System.out.println("Select 5 cards!");
                }
                return true;
            }
        });

        buttonTable.add(submit).height(32).width(100);
        buttonTable.row();
        buttonTable.add(clear).height(32).width(100);

    }

}
