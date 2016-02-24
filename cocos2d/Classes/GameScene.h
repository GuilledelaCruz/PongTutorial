#ifndef __GAME_SCENE_H__
#define __GAME_SCENE_H__

#include "cocos2d.h"

class Game : public cocos2d::Layer
{
public:
    static cocos2d::Scene* createScene();
    enum class PhysicsCategory {
        None = 0,
        Ball = (1 << 0),
        Panel = (1 << 1),
        Wall = (1 << 2),
        Bounce = PhysicsCategory::Panel | PhysicsCategory::Wall
    };

    virtual bool init();
    void update(float) override;

    // callbacks
    void menuCloseCallback(cocos2d::Ref* pSender);
    virtual void onMouseDown(cocos2d::Event*);
    virtual void onMouseUp(cocos2d::Event*);
    virtual void onMouseMove(cocos2d::Event*);
    virtual void onMouseScroll(cocos2d::Event*);
    bool onContactBegin(cocos2d::PhysicsContact &contact);

    // implement the "static create()" method manually
    CREATE_FUNC(Game);
private:
    void createMenu();
    void createScoreboad();
    void createEntities();
    void events();
    void createBody(cocos2d::Sprite*, PhysicsCategory);

    cocos2d::Sprite* ball;
    cocos2d::Sprite* leftPanel;
    cocos2d::Sprite* rightPanel;
    cocos2d::Sprite* upperWall;
    cocos2d::Sprite* lowerWall;
    cocos2d::Sprite* rightGoal;
    cocos2d::Sprite* leftGoal;
    cocos2d::Label* labelScoreboard;
    int goalA;
    int goalB;
    char scoreboard[10];
    cocos2d::Size visibleSize;
    cocos2d::Vec2 origin;
};

#endif // __GAME_SCENE_H__
