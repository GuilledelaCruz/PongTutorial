package guilledelacruz.pongtutorial.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import guilledelacruz.pongtutorial.GameMain;

/**
 * Created by guilledelacruz on 18/02/16.
 */
public class Edge {

    public enum EdgeSide{
        Upper, Lower
    }

    private Texture texture;
    private Sprite sprite;
    private Body body;
    private Float width;
    private Float height;

    public Edge(GameMain game, World world, EdgeSide side){
        // Load texture, create sprite and set its position
        texture = new Texture("images/wall.png");
        sprite = new Sprite(texture);
        width = sprite.getWidth();
        height = sprite.getHeight();
        // Creating the definition of the body that the world needs
        BodyDef edgeBody = new BodyDef();
        edgeBody.type = BodyDef.BodyType.StaticBody;

        if (side.equals(EdgeSide.Upper)){
            sprite.setPosition(0, (game.HEIGHT - height));
        }else{
            sprite.setPosition(0, 0);
        }

        edgeBody.position.set((sprite.getX() + width / 2) / game.PIXELS_TO_METERS,
                              (sprite.getY() + height / 2) / game.PIXELS_TO_METERS);
        // More properties of the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0f;
        /*
            This filter is used for collisions, categorybits means "what i am" and maskbit "what i can hit"
         */
        fixtureDef.filter.categoryBits = game.EDGE_ENTITY;
        fixtureDef.filter.maskBits = game.BALL_ENTITY;
        // Geometry of the body
        PolygonShape edge = new PolygonShape();
        edge.setAsBox(width / 2 / game.PIXELS_TO_METERS, height / 2 / game.PIXELS_TO_METERS);
        fixtureDef.shape = edge;
        // Finally, the body is created into the world
        body = world.createBody(edgeBody);
        body.createFixture(fixtureDef);
        edge.dispose();
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
