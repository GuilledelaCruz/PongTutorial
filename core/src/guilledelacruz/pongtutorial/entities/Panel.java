package guilledelacruz.pongtutorial.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import guilledelacruz.pongtutorial.GameMain;

/**
 * Created by guilledelacruz on 18/02/16.
 */
public class Panel {

    public enum PanelSide{
        Left, Right
    }

    private Texture texture;
    private Sprite sprite;
    private Body body;
    private Float width;
    private Float height;

    public Panel(GameMain game, World world, PanelSide side){
        // Load texture, create sprite and set its position
        texture = new Texture("images/panel.png");
        sprite = new Sprite(texture);
        width = sprite.getWidth();
        height = sprite.getHeight();
        // Creating the definition of the body that the world needs
        BodyDef edgeBody = new BodyDef();
        edgeBody.type = BodyDef.BodyType.StaticBody;

        if (side.equals(PanelSide.Left)){
            sprite.setPosition(width, game.HEIGHT / 2 - height / 2);
        }else{
            sprite.setPosition(game.WIDTH - width * 2, game.HEIGHT / 2 - height / 2);
        }

        edgeBody.position.set((sprite.getX() + width / 2) / game.PIXELS_TO_METERS,
                             (sprite.getY() + height / 2) / game.PIXELS_TO_METERS);
        // More properties of the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0f;
        /*
            This filter is used for collisions, categorybits means "what i am" and maskbit "what i can hit"
         */
        fixtureDef.filter.categoryBits = game.PANEL_ENTITY;
        fixtureDef.filter.maskBits = game.BALL_ENTITY;
        // Geometry of the body
        PolygonShape panel = new PolygonShape();
        panel.setAsBox(width / 2 / game.PIXELS_TO_METERS, height / 2 / game.PIXELS_TO_METERS);
        fixtureDef.shape = panel;
        // Finally, the body is created into the world
        body = world.createBody(edgeBody);
        body.createFixture(fixtureDef);
        panel.dispose();
    }

    public Body getBody(){
        return body;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void dispose(){
        texture.dispose();
    }
}
