#include "GameScene.h"
#include "PongScene.h"
#include <iostream>

USING_NS_CC;

Scene* Game::createScene()
{
    auto scene = Scene::createWithPhysics();
    scene->getPhysicsWorld()->setGravity(Vec2(0,0));

    auto layer = Game::create();
    scene->addChild(layer);

    return scene;
}

bool Game::init()
{
    if ( !Layer::init() )
    {
        return false;
    }

    visibleSize = Director::getInstance()->getVisibleSize();
    origin = Director::getInstance()->getVisibleOrigin();

    createMenu();

    createScoreboad();

    createEntities();

    events();

    ball->getPhysicsBody()->applyImpulse(Vect(100.0, 100.0));
    this->scheduleUpdate();

    return true;
}

void Game::createMenu()
{
    Label* label = Label::createWithTTF("Back to menu", "fonts/arial.ttf", 14);
    label->setColor(Color3B(255, 255, 255));

    auto closeItem = MenuItemLabel::create(label, CC_CALLBACK_1(Game::menuCloseCallback, this));
    closeItem->setPosition(Vec2(origin.x + visibleSize.width / 2,
                                origin.y + visibleSize.height / 6));

    auto menu = Menu::create(closeItem, NULL);
    menu->setPosition(Vec2::ZERO);
    this->addChild(menu, 1);
}

void Game::createScoreboad()
{
    goalA = 0;
    goalB = 0;
    scoreboard[10];
    sprintf(scoreboard,"%d  %d", goalA, goalB);

    labelScoreboard = Label::createWithTTF(scoreboard, "fonts/arial.ttf", 18);
    // position the label on the center of the screen
    labelScoreboard->setPosition(Vec2(origin.x + visibleSize.width/2,
                                      origin.y + visibleSize.height - labelScoreboard->getContentSize().height));
    // add the label as a child to this layer
    this->addChild(labelScoreboard, 1);
}

void Game::createEntities()
{
    auto drawBall = DrawNode::create();
    this->addChild(drawBall);
    auto drawBoundings = DrawNode::create();
    this->addChild(drawBoundings);
    auto drawPanels = DrawNode::create();
    this->addChild(drawPanels);

    ball = Sprite::create("images/ball.png");
    ball->setPosition(Vec2(origin.x + visibleSize.width / 2,
                           origin.y + visibleSize.height / 2));
    createBody(ball, PhysicsCategory::Ball);
    drawBall->addChild(ball);

    leftPanel = Sprite::create("images/panel.png");
    leftPanel->setPosition(Vec2(origin.x + leftPanel->getContentSize().width / 2,
                                origin.y + visibleSize.height / 2));
    rightPanel = Sprite::create("images/panel.png");
    rightPanel->setPosition(Vec2(origin.x + visibleSize.width - rightPanel->getContentSize().width / 2,
                                 origin.y + visibleSize.height / 2));
    createBody(rightPanel, PhysicsCategory::Panel);
    createBody(leftPanel, PhysicsCategory::Panel);
    drawPanels->addChild(leftPanel);
    drawPanels->addChild(rightPanel);

    upperWall = Sprite::create("images/wall.png");
    upperWall->setPosition(Vec2(origin.x + visibleSize.width / 2,
                                origin.y + visibleSize.height - upperWall->getContentSize().height / 2));
    lowerWall = Sprite::create("images/wall.png");
    lowerWall->setPosition(Vec2(origin.x + visibleSize.width / 2,
                                origin.y + lowerWall->getContentSize().height / 2));
    createBody(upperWall, PhysicsCategory::Wall);
    createBody(lowerWall, PhysicsCategory::Wall);
    drawBoundings->addChild(upperWall);
    drawBoundings->addChild(lowerWall);
}

void Game::createBody(Sprite * sprite, PhysicsCategory cat)
{
    auto size = sprite->getContentSize();
    PhysicsBody* physicsBody;

    if(cat == PhysicsCategory::Ball)
    {
        physicsBody = PhysicsBody::createCircle(size.width / 2, PhysicsMaterial(0.0f, 1.0f, 0.0f));
        physicsBody->setDynamic(true);
        physicsBody->setCategoryBitmask((int)PhysicsCategory::Ball);
        physicsBody->setCollisionBitmask((int)PhysicsCategory::None);
        physicsBody->setContactTestBitmask((int)PhysicsCategory::Bounce);
    }
    else if(cat == PhysicsCategory::Panel)
    {
        physicsBody = PhysicsBody::createBox(size, PhysicsMaterial(0.0f, 1.0f, 0.0f));
        physicsBody->setDynamic(false);
        physicsBody->setCategoryBitmask((int)PhysicsCategory::Panel);
        physicsBody->setCollisionBitmask((int)PhysicsCategory::None);
        physicsBody->setContactTestBitmask((int)PhysicsCategory::Ball);
    }
    else
    {
        physicsBody = PhysicsBody::createBox(size, PhysicsMaterial(0.0f, 1.0f, 0.0f));
        physicsBody->setDynamic(false);
        physicsBody->setCategoryBitmask((int)PhysicsCategory::Wall);
        physicsBody->setCollisionBitmask((int)PhysicsCategory::None);
        physicsBody->setContactTestBitmask((int)PhysicsCategory::Ball);
    }
    sprite->setPhysicsBody(physicsBody);
}

void Game::events()
{
    auto eventListener = EventListenerMouse::create();
    eventListener->onMouseMove = CC_CALLBACK_1(Game::onMouseMove, this);
    eventListener->onMouseUp = CC_CALLBACK_1(Game::onMouseUp, this);
    eventListener->onMouseDown = CC_CALLBACK_1(Game::onMouseDown, this);
    eventListener->onMouseScroll = CC_CALLBACK_1(Game::onMouseScroll, this);
    this->getEventDispatcher()->addEventListenerWithSceneGraphPriority(eventListener, this);

    auto contactListener = EventListenerPhysicsContact::create();
    contactListener->onContactBegin = CC_CALLBACK_1(Game::onContactBegin, this);
    this->getEventDispatcher()->addEventListenerWithSceneGraphPriority(contactListener, this);
}

bool Game::onContactBegin(PhysicsContact &contact)
{
    auto bodyA = contact.getShapeA()->getBody();
    auto bodyB = contact.getShapeB()->getBody();

    if(bodyB->getCategoryBitmask() == (int)PhysicsCategory::Wall)
    {
        if(bodyA->getPosition().y > origin.y + visibleSize.height / 2)
        {
            bodyA->applyImpulse(Vect(0.0, -200.0));
        }
        else
        {
            bodyA->applyImpulse(Vect(0.0, 200.0));
        }
    }

    if(bodyB->getCategoryBitmask() == (int)PhysicsCategory::Panel)
    {
        if(bodyA->getPosition().x > origin.x + visibleSize.width / 2)
        {
            bodyA->applyImpulse(Vect(-200.0, 0.0));
        }
        else
        {
            bodyA->applyImpulse(Vect(200.0, 0.0));
        }
    }

    return true;
}

void Game::update(float delta)
{
    if (ball->getPosition().x > origin.x + visibleSize.width)
    {
        goalA++;
        sprintf(scoreboard,"%d  %d", goalA, goalB);
        labelScoreboard->setString(scoreboard);
        labelScoreboard->setPosition(Vec2(origin.x + visibleSize.width/2,
                                          origin.y + visibleSize.height - labelScoreboard->getContentSize().height));

        ball->setPosition(Vec2(origin.x + visibleSize.width / 2,
                               origin.y + visibleSize.height / 2));
    }
    if (ball->getPosition().x < origin.x)
    {
        goalB++;
        sprintf(scoreboard,"%d  %d", goalA, goalB);
        labelScoreboard->setString(scoreboard);
        labelScoreboard->setPosition(Vec2(origin.x + visibleSize.width/2,
                                          origin.y + visibleSize.height - labelScoreboard->getContentSize().height));

        ball->setPosition(Vec2(origin.x + visibleSize.width / 2,
                               origin.y + visibleSize.height / 2));
    }
}

void Game::onMouseMove(Event *event)
{
    EventMouse* e = (EventMouse*)event;
    float y = e->getCursorY();
    if(y > origin.y + lowerWall->getContentSize().height + rightPanel->getContentSize().height / 2 &&
            y < origin.y + visibleSize.height - upperWall->getContentSize().height - rightPanel->getContentSize().height / 2){
        rightPanel->setPosition(rightPanel->getPosition().x, y);
    }
}

void Game::onMouseDown(Event* event)
{
}
void Game::onMouseUp(Event* event)
{
}
void Game::onMouseScroll(Event* event)
{
}

void Game::menuCloseCallback(Ref* pSender)
{
    auto scene = Pong::createScene();
    Director::getInstance()->replaceScene(scene);
}
