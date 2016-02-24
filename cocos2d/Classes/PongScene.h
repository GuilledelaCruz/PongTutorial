#ifndef __PONG_SCENE_H__
#define __PONG_SCENE_H__

#include "cocos2d.h"

class Pong : public cocos2d::Layer
{
public:
    static cocos2d::Scene* createScene();

    virtual bool init();
    
    // a selector callback
    void menuCloseCallback(cocos2d::Ref* pSender);
    void menuStartCallback(cocos2d::Ref* pSender);
    
    // implement the "static create()" method manually
    CREATE_FUNC(Pong);
};

#endif // __PONG_SCENE_H__
