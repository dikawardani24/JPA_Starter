package com.dika.activity.service;

import com.dika.Logger;
import com.dika.activity.Activity;

public interface OnResumedAction extends ActivityAction {
    @Override
    default void stopAction(Activity<?> activity) {
        Logger.INSTANCE.printInfo("Resume action from "+activity.getClass()+" request to stop");
    }
}
