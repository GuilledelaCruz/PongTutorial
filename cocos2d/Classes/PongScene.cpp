#include "PongScene.h"
#include "GameScene.h"

USING_NS_CC;

Scene* Pong::createScene()
{
    // 'scene' is an autorelease object
    auto scene = Scene::create();
    
    // 'layer' is an autorelease object
    auto layer = Pong::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool Pong::init()
{
    //////////////////////////////
    // 1. super init first
    if ( !Layer::init() )
    {
        return false;
    }
    
    Size visibleSize = Director::getInstance()->getVisibleSize();
    Vec2 origin = Director::getInstance()->getVisibleOrigin();

    auto startItem = MenuItemImage::create("images/buttonplay.png",
                                           "images/buttonplay.png",
                                           CC_CALLBACK_1(Pong::menuStartCallback, this));

    startItem->setPosition(Vec2(origin.x + visibleSize.width / 2,
                                origin.y + visibleSize.height / 10 * 7));

    auto closeItem = MenuItemImage::create("images/buttonexit.png",
                                           "images/buttonexit.png",
                                           CC_CALLBACK_1(Pong::menuCloseCallback, this));
    
    closeItem->setPosition(Vec2(origin.x + visibleSize.width / 2,
                                origin.y + visibleSize.height / 10 * 3));

    // create an array for all items to insert in menu
    Vector<MenuItem*> items;
    items.pushBack(startItem);
    items.pushBack(closeItem);

    // create menu, it's an autorelease object
    auto menu = Menu::createWithArray(items);
    menu->setPosition(Vec2::ZERO);
    this->addChild(menu, 1);

    auto label = Label::createWithTTF("PongTutorial", "fonts/arial.ttf", 18);
    // position the label on the center of the screen
    label->setPosition(Vec2(origin.x + visibleSize.width/2,
                            origin.y + visibleSize.height - label->getContentSize().height));
    // add the label as a child to this layer
    this->addChild(label, 1);

    /* add "HelloWorld" splash screen"
    auto sprite = Sprite::create("HelloWorld.png");
    // position the sprite on the center of the screen
    sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height/2 + origin.y));
    // add the sprite as a child to this layer
    this->addChild(sprite, 0);
    */

    return true;
}

void Pong::menuStartCallback(Ref *pSender)
{
    auto scene = Game::createScene();
    Director::getInstance()->replaceScene(scene);
}

void Pong::menuCloseCallback(Ref* pSender)
{
    Director::getInstance()->end();

#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
}
