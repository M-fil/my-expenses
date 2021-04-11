package com.company.core.components.PageEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.HashMap;

public abstract class PageEntity {
    protected JPanel pageWrapper;
    static protected HashMap<String, String> Text = new HashMap<String, String>();

    public PageEntity() {
        this.pageWrapper = new JPanel();
    }

    static public void setTheSamePaddingForAllSides(int paddingNumber, JComponent component) {
        component.setBorder(new EmptyBorder(paddingNumber, paddingNumber, paddingNumber, paddingNumber));
    }

    public abstract JPanel render();
}
