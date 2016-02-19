package guilledelacruz.pongtutorial.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import guilledelacruz.pongtutorial.GameMain;

import java.util.Random;

/**
 * Created by guilledelacruz on 18/02/16.
 */
public class Ball {

    private Texture texture;
    private Sprite sprite;
    private Body body;
    private Float width;
    private Float height;

    public Ball(GameMain game, World world){
        // Load texture, create sprite and set its position
        texture = new Texture(Gdx.files.internal("images/ball.png"));
        sprite = new Sprite(texture);
        width = sprite.getWidth();
        height = sprite.getHeight();
        sprite.setPosition(game.WIDTH / 2 - width / 2, game.HEIGHT / 2 - height / 2);
        // Creating the definition of the body that the world needs
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + width / 2) / game.PIXELS_TO_METERS,
                (sprite.getY() + height / 2) / game.PIXELS_TO_METERS);
        // Geometry of the body
        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2 / game.PIXELS_TO_METERS); // comprobar diametro PIXELS_TO_METERS
        // More properties of the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        /*
            This filter is used for collisions, categorybits means "what i am" and maskbit "what i can hit"
         */
        fixtureDef.filter.categoryBits = game.BALL_ENTITY;
        fixtureDef.filter.maskBits = game.EDGE_ENTITY;
        fixtureDef.filter.maskBits |= game.PANEL_ENTITY; // EDGE_ENTITY OR PANEL_ENTITY
        // Finally, the body is created into the world
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        // Gotta go fast
        body.setLinearVelocity(new Vector2(1.25f, 1.25f));
        shape.dispose();
    }

    public Body getBody(){
        return body;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void disponse(){
        texture.dispose();
    }
}
