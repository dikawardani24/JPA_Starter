package com.dika.view;

import com.dika.ClearAble;

import javax.swing.text.JTextComponent;
import java.awt.Container;
import java.util.List;

public interface InputContainer<C extends Container> extends com.dika.view.Container<C>, ClearAble {
    List<JTextComponent> getTextComponents();

    @Override
    default boolean isEmpty() {
        for (JTextComponent textComponent : getTextComponents()) {
            if (textComponent instanceof ClearAble) {
                if (((ClearAble) textComponent).isEmpty()) {
                    return true;
                }
            } else {
                if (textComponent.getText().isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    default void clear() {
        for (JTextComponent textComponent :
                getTextComponents()) {
            if (textComponent instanceof ClearAble) {
                ((ClearAble) textComponent).clear();
            } else {
                textComponent.setText("");
            }
        }
    }
}
