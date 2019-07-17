package com.syh.framework.design_patters.behavior_type.mediator;

/**
 * 中介者具体实现
 */
public class Wuba implements HouseMediator {
    Purchaser mPurchaser;
    Landlord mLandlord;

    public void setPurchaser(Purchaser purchaser) {//设置买房者
        mPurchaser = purchaser;
    }

    public void setLandlord(Landlord landlord) {//设置房东
        mLandlord = landlord;
    }


    @Override
    public void notice(Person person, String message) {//发送通知
        System.out.println("中介收到信息，并转发给相应的目标人群");
        if (person == mPurchaser) {
            mLandlord.getNotice(message);
        } else if (person == mLandlord) {
            mPurchaser.getNotice(message);
        }
    }
}
