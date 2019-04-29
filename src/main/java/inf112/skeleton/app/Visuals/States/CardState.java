package inf112.skeleton.app.Visuals.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.skeleton.app.GameMechanics.Board.Board;
import inf112.skeleton.app.GameMechanics.Cards.CardManager;
import inf112.skeleton.app.Visuals.*;
import inf112.skeleton.app.GameMechanics.Player;

public class CardState extends State {

	private Board board;
	private BoardGUI boardGUI;
	private SpriteBatch batch;
	private Player[] players;

	private CardGUI cardHandGUI;

	private PlayerInfoGUI playerInfoGUI;

	private CardManager cardManager;

	public CardState(GameStateManager gsm, Board board, CardManager cardManager) {
		super(gsm);
		this.board = board;
		this.batch = new SpriteBatch();
		this.boardGUI = new BoardGUI(board, this.camera, this.stage, this.gsm, super.assetHandler);
		this.boardGUI.create();

		this.players = board.getAllPlayers();

		this.playerInfoGUI = new PlayerInfoGUI(board, batch, stage, super.assetHandler);
		this.cardManager = cardManager;

		//this.cardHandGUI = new CardHandGUI(cardManager, batch, stage, super.assetHandler);
		this.cardHandGUI = new CardGUI(cardManager, stage, super.assetHandler);
	}

	@Override
	public void update(float dt) {
		super.update(dt);
		playerInfoGUI.update();
		Gdx.input.setInputProcessor(stage);
		for (Player player : players) {
			if (!player.isReady()) {
				return;
			}
		}
		board.initRound();
		gsm.set(new ActionState(gsm, board, cardManager));
	}

	@Override
	public void render() {
		if (Gdx.input.isKeyPressed(Input.Keys.P)) {
			System.out.println("PAUSE!");
			this.gsm.push(new PauseState(this.gsm));
		}
		super.render();
		//cardHandGUI.render();
	}

	@Override
	public void dispose() {
		//cardHandGUI.dispose();
		playerInfoGUI.dispose();
		batch.dispose();
	}

	@Override
	public void resize() {
		super.resize();
		//cardHandGUI.resize();
	}
}
