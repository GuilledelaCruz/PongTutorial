package guilledelacruz.pongtutorial;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import guilledelacruz.pongtutorial.screens.GameScreen;
import guilledelacruz.pongtutorial.screens.MenuScreen;

public class GameMain extends Game {

	// Screens
	private MenuScreen menu;
	private GameScreen game;
	// Size of screen
	public int HEIGHT;
	public int WIDTH;
	// All that we need for Box2D
	public Float PIXELS_TO_METERS;
	public final short BALL_ENTITY = 0x1;
	public final short PANEL_ENTITY = 0x2;
	public final short EDGE_ENTITY = 0x4;

	public void create () {
		/*  PIXELS_TO_METERS:
			This first value is needed because Box2D does not work with pixels, it works with its own measurement
			so it is necesary to translate pixels to meters	and viceversa
		*/
		PIXELS_TO_METERS = 100f; // It can be changed to a value scaled by the size of the screen
		// Creating screens of the game
		menu = new MenuScreen(this);
		game = new GameScreen(this);

		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
		// Starting the menu screen
		menuScreen();
	}

	// Change the screen
	public void menuScreen(){
		setScreen(menu);
	}
	public void gameScreen(){
		setScreen(game);
	}
}
