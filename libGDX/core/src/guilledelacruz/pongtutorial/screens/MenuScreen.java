package guilledelacruz.pongtutorial.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import guilledelacruz.pongtutorial.GameMain;

/**
 * Created by guilledelacruz on 18/02/16.
 */
public class MenuScreen implements Screen, InputProcessor {

    private GameMain game;

    private SpriteBatch batch;
    private Texture textureButtonPlay;
    private Texture textureButtonExit;

    public MenuScreen(GameMain game){
        this.game = game;
    }

    public void show() {
        // Creating batch to draw
        batch = new SpriteBatch();
        // Loading texture of botons
        textureButtonPlay = new Texture("images/buttonplay.png");
        textureButtonExit = new Texture("images/buttonexit.png");
        // Set input processor to this implementation
        Gdx.input.setInputProcessor(this);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin(); // Begin SpriteBatch to draw
        // By default, [0, 0] point of textures are at the lower left corner and it's positive going up
        // Draw Play and Exit buttons
        batch.draw(textureButtonPlay, game.WIDTH / 3, game.HEIGHT / 5 * 3);
        batch.draw(textureButtonExit, game.WIDTH / 3, game.HEIGHT / 5 * 1);
        batch.end(); // Closing the buffer
    }

    public void resize(int width, int height) {}

    public void resume() {}

    public void pause() {}

    public void hide() {
        // Dispose if it is left the current screen
        dispose();
    }

    public void dispose() {
        // Disposing resources for avoiding memory leaks
        batch.dispose();
        textureButtonPlay.dispose();
        textureButtonExit.dispose();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // By default, the [0, 0] point of this event is at the upper left corner and it's positive going down
        // Button Play
        if ((screenX >= game.WIDTH / 3 && screenX <= (game.WIDTH / 3 + textureButtonPlay.getWidth())) &&
                (screenY >= game.HEIGHT / 5 * 1 && screenY <= (game.HEIGHT / 5 * 1 + textureButtonPlay.getHeight()))){
            game.gameScreen();
            return true;
        }
        // Button Exit
        if ((screenX >= game.WIDTH / 3 && screenX <= game.WIDTH / 3 + textureButtonExit.getWidth()) &&
                (screenY >= game.HEIGHT / 5 * 3 && screenY <= game.HEIGHT / 5 * 3 + textureButtonExit.getHeight())){
            Gdx.app.exit();
            return true;
        }

        return false;
    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }
}
