package com.company.core.components.ComponentEntity;

import com.company.core.components.PageEntity.PageEntity;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.HashMap;

public abstract class ComponentEntity extends PageEntity {
    static protected HashMap<String, String> Text = new HashMap<String, String>();

    static public void removeButtonAllActionListeners(JButton button) {
        for(ActionListener actionListener : button.getActionListeners() ) {
            button.removeActionListener(actionListener);
        }
    }

    public abstract JPanel render();
}
