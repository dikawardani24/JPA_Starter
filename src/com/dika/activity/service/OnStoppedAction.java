package com.dika.activity.service;

import com.dika.Logger;
import com.dika.activity.Activity;

public interface OnStoppedAction extends ActivityAction {
    @Override
    default void stopAction(Activity<?> activity) {
        Logger.INSTANCE.printInfo("Stopped action from "+activity.getClass()+" request to stop");
    }
}
