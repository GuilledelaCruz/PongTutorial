package guilledelacruz.pongtutorial.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import guilledelacruz.pongtutorial.GameMain;
import guilledelacruz.pongtutorial.entities.Ball;
import guilledelacruz.pongtutorial.entities.ContactEntities;
import guilledelacruz.pongtutorial.entities.Edge;
import guilledelacruz.pongtutorial.entities.Panel;

/**
 * Created by guilledelacruz on 18/02/16.
 */
public class GameScreen implements Screen, InputProcessor {

    private GameMain game;

    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout;

    private World world;
    private Ball ball;
    private Edge upperEdge;
    private Edge lowerEdge;
    private Panel leftPanel;
    private Panel rightPanel;

    private int puntuationJA;
    private int puntuationJB;

    //private Box2DDebugRenderer debugRenderer;
    //private Matrix4 debugMatrix;

    public GameScreen(GameMain game){
        this.game = game;
    }

    public void show() {
        // Initializing all that we need
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout = new GlyphLayout();
        layout.setText(font, "MENU");

        world = new World(new Vector2(0f, 0f), true);
        world.setContactListener(new ContactEntities(game));
        ball = new Ball(game, world);
        upperEdge = new Edge(game, world, Edge.EdgeSide.Upper);
        lowerEdge = new Edge(game, world, Edge.EdgeSide.Lower);
        leftPanel = new Panel(game, world, Panel.PanelSide.Left);
        rightPanel = new Panel(game, world, Panel.PanelSide.Right);

        puntuationJA = 0;
        puntuationJB = 0;

        // Events of keyboard, mouse and screen
        Gdx.input.setInputProcessor(this);

        //debugRenderer = new Box2DDebugRenderer();
    }

    public void render(float delta) {
        // Step the physics simulation forward at a rate of 60hz
        world.step(delta, 6, 2);
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Set the position of the sprite of ball and panels in respect of their bodies in the world
        ball.getSprite().setPosition(
                (ball.getBody().getPosition().x * game.PIXELS_TO_METERS) - ball.getSprite().getWidth() / 2,
                (ball.getBody().getPosition().y * game.PIXELS_TO_METERS) - ball.getSprite().getWidth() / 2);

        leftPanel.getSprite().setPosition(
                (leftPanel.getBody().getPosition().x * game.PIXELS_TO_METERS) - leftPanel.getSprite().getWidth() / 2,
                (leftPanel.getBody().getPosition().y * game.PIXELS_TO_METERS) - leftPanel.getSprite().getHeight() / 2);
        rightPanel.getSprite().setPosition(
                (rightPanel.getBody().getPosition().x * game.PIXELS_TO_METERS) - rightPanel.getSprite().getWidth() / 2,
                (rightPanel.getBody().getPosition().y * game.PIXELS_TO_METERS) - rightPanel.getSprite().getHeight() / 2);

        // Check if a player has scored
        if (ball.getSprite().getX() >= game.WIDTH){
            puntuationJA++;
            newBall();
        }
        if (ball.getSprite().getX() <= -ball.getSprite().getWidth()){
            puntuationJB++;
            newBall();
        }

        //debugMatrix = batch.getProjectionMatrix().cpy().scale(game.PIXELS_TO_METERS, game.PIXELS_TO_METERS, 0);

        batch.begin();// Open spritebatch to draw
        // Draw walls
        upperEdge.getSprite().draw(batch);
        lowerEdge.getSprite().draw(batch);
        // Draw score
        font.draw(batch, Integer.toString(puntuationJA), game.WIDTH / 2 - game.WIDTH / 80 * 2, game.HEIGHT - font.getCapHeight() * 2);
        font.draw(batch, Integer.toString(puntuationJB), game.WIDTH / 2 + game.WIDTH / 80, game.HEIGHT - font.getCapHeight() * 2);
        font.draw(batch, layout, game.WIDTH / 2 - layout.width / 2, game.HEIGHT / 10);
        // Draw panels
        leftPanel.getSprite().draw(batch);
        rightPanel.getSprite().draw(batch);
        // Draw ball
        ball.getSprite().draw(batch);
        batch.end();// End spritebatch of drawing

        //debugRenderer.render(world, debugMatrix);
    }

    public void newBall(){
        // The ball is deleted and is created another one with default properties
        world.destroyBody(ball.getBody());
        ball = new Ball(game, world);
    }

    public void resize(int width, int height) {}

    public void resume() {}

    public void pause() {}

    public void hide() {
        // The current screen ends
        dispose();
    }

    public void dispose() {
        // Releasing resources
        batch.dispose();
        font.dispose();
        ball.disponse();
        leftPanel.dispose();
        rightPanel.dispose();
        upperEdge.dispose();
        lowerEdge.dispose();
        world.dispose();
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // By default, the [0, 0] point of this event is at the upper left corner and it's positive going down
        float pos = (screenY - game.HEIGHT) / -1; // So we invert the value like this

        if(pos > (lowerEdge.getSprite().getHeight() + rightPanel.getSprite().getHeight() / 2) &&
                pos < upperEdge.getSprite().getY() - rightPanel.getSprite().getHeight() / 2) {

            float posy = pos / game.PIXELS_TO_METERS;

            leftPanel.getBody().setTransform(leftPanel.getBody().getPosition().x, posy, 0);
            rightPanel.getBody().setTransform(rightPanel.getBody().getPosition().x, posy, 0);

            return true;
        }

        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Menu button
        float posx = (screenX - game.WIDTH) / -1;
        float posy = (screenY - game.HEIGHT) / -1;
        if ((posx >= game.WIDTH / 2 - layout.width / 2 && posx <= game.WIDTH / 2 + layout.width / 2) &&
                (posy >= game.HEIGHT / 10 - layout.height && posy <= game.HEIGHT / 10)){
            game.menuScreen();
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
