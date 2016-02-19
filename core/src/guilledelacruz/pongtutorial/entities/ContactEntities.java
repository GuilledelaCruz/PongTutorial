package guilledelacruz.pongtutorial.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import guilledelacruz.pongtutorial.GameMain;

/**
 * Created by guilledelacruz on 19/02/16.
 */
public class ContactEntities implements ContactListener {

    private GameMain game;

    public ContactEntities(GameMain game){
        this.game = game;
    }

    public void beginContact(Contact contact) {
        // Getting the static body which the ball has hitted
        Short a = contact.getFixtureA().getFilterData().categoryBits;

        // If it is an edge, apply a vertical speed to the ball
        if (a.equals(game.EDGE_ENTITY)){
            Body b = contact.getFixtureA().getBody();
            Float ypos = b.getPosition().y * game.PIXELS_TO_METERS;

            Body ball = contact.getFixtureB().getBody();
            Vector2 speed = ball.getLinearVelocity();

            ball.setLinearVelocity(speed.x, speed.y * -1);
        }
        // If it is a panel, apply an horizontal speed to the ball
        if (a.equals(game.PANEL_ENTITY)) {
            Body b = contact.getFixtureA().getBody();
            Float xpos = b.getPosition().x * game.PIXELS_TO_METERS;

            Body ball = contact.getFixtureB().getBody();
            Vector2 speed = ball.getLinearVelocity();

            ball.setLinearVelocity(speed.x * -1, speed.y);
        }
    }

    public void endContact(Contact contact) {

    }

    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}