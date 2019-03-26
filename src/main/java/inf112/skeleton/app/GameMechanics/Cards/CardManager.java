package inf112.skeleton.app.GameMechanics.Cards;

import inf112.skeleton.app.GameMechanics.Board.Board;
import inf112.skeleton.app.GameMechanics.Cards.Card;
import inf112.skeleton.app.GameMechanics.Cards.ProgramCardDeck;
import inf112.skeleton.app.GameMechanics.Player;
import inf112.skeleton.app.Interfaces.ICardDeck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CardManager {

    private ICardDeck cardDeck;
    private Player[] players;
    private int playerPtr;
    private HashSet<Card> lockedCards;

    public CardManager(Board board) {
        players = board.getAllPlayers();
        cardDeck = new ProgramCardDeck();
    }

    /**
     * Initialize new round. Fills card deck with last round cards.
     * Draw new cards for each player.
     */
    public void newRound() {
        playerPtr = 0;
        lockCards();
        sendCardsBackToDeck();
        cardDeck.shuffleDeck();
        dealCards();
        /*
        for (int i = 0; i < players.length; i++) {
            players[i].setCardHand(cardDeck.drawCards(9));
        }
        */
    }

    /**
     * @return current player for assigning play sequence
     */
    public Player getPlayer() {
        return players[playerPtr];
    }

    /**
     * Checks if all players has a valid play card sequence
     *
     * @return true if there is one or more player without a valid card sequence ready to execute
     */
    public boolean hasNotReadyPlayers() {
        for (Player player : players) {
            if (player.isReady()) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets a five card play sequence for a given player
     *
     * @param player
     * @param cards
     * @return true if its a valid 5 card sequence
     */
    public boolean setCardSeq(Player player, Card[] cards) {
        if (cards.length == 5) {
            for (Card card : cards) {
                if (card == null) {
                    return false;
                }
            }
            player.setCardSequence(cards);
            player.setReady();
            playerPtr++;
            return true;
        } else {
            return false;
        }
    }

    public boolean isLocked(Card c) {
        if (lockedCards != null) {
            return lockedCards.contains(c);
        } else {
            return false;
        }
    }

    private void sendCardsBackToDeck() {
        for (int i = 0; i < players.length; i++) {
            List<Card> cards = players[i].getCardHand();
            if (cards != null) {
                for (int j = 0; j < cards.size(); j++) {
                    if (!isLocked(cards.get(j))) {
                        cardDeck.addCard(cards.get(j));
                    }
                }
            }
        }
    }

    private void lockCards() {
        lockedCards = new HashSet<>();
        for (int i = 0; i < players.length; i++) {
            if (players[i].getCardSequence() != null) {
                Card[] tempCards = players[i].getCardSequence();
                int playerHP = players[i].getHealth();
                if (playerHP < 6) {
                    lockedCards.add(tempCards[4]);
                    if (playerHP < 5) {
                        lockedCards.add(tempCards[3]);
                    }
                    if (playerHP < 4) {
                        lockedCards.add(tempCards[2]);
                    }
                    if (playerHP < 3) {
                        lockedCards.add(tempCards[1]);
                    }
                    if (playerHP < 2) {
                        lockedCards.add(tempCards[0]);
                    }
                }
            }
        }
    }

    private void dealCards() {
        for (int i = 0; i < players.length; i++) {
            Card[] oldCardSeq = players[i].getCardSequence();
            List<Card> newCardHand = new ArrayList<>();

            for (int j = 0; j < 9; j++) {
                if (oldCardSeq != null && oldCardSeq.length > j) {
                    if (oldCardSeq[j] != null) {
                        if (isLocked(oldCardSeq[j])) {
                            newCardHand.add(oldCardSeq[j]);
                            continue;
                        }
                    }
                }
                newCardHand.add(cardDeck.drawCard());
            }

            int playerHealth = players[i].getHealth();
            int cardsToRemove = 10 - playerHealth;
            if (cardsToRemove > 0) {
                for (int j = 0; j < cardsToRemove; j++) {
                    if (j > 3) {
                        break;
                    }
                    cardDeck.addCard(newCardHand.remove(newCardHand.size() - 1));
                }
            }
            players[i].setCardHand(newCardHand);
        }
    }

}

