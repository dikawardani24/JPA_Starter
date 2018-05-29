package com.dika.view;

import java.awt.*;

public interface View<R extends Window> {
    R getRoot();

    default void display() {
        getRoot().setVisible(true);
    }

    default void close() {
        getRoot().dispose();
    }
}
