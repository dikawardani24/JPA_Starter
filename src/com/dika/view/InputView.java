package com.dika.view;

import com.dika.ClearAble;
import org.apache.xpath.operations.Bool;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public interface InputView<R extends Window> extends View<R>, ClearAble {
    List<JTextComponent> getTextComponents();

    @Override
    default boolean isEmpty() {
        List<JTextComponent> emptyComponent = new ArrayList<>();
        for (JTextComponent textComponent : getTextComponents()) {
            if (textComponent instanceof ClearAble) {
                if (((ClearAble) textComponent).isEmpty()) {
                    emptyComponent.add(textComponent);
                }
            } else {
                if (textComponent.getText().isEmpty()) {
                    emptyComponent.add(textComponent);
                }
            }
        }

        return emptyComponent.size() == getTextComponents().size();
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
