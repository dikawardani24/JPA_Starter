package com.dika.activity.service;

import com.dika.activity.Activity;

public interface ActivityAction {
    void invoke(Activity<?> activity);
    void stopAction(Activity<?> activity);

}
