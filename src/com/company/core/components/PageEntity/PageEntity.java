package com.company.core.components.PageEntity;

import javax.swing.*;
import java.util.HashMap;

public abstract class PageEntity {
    protected JPanel pageWrapper;
    static public HashMap<String, String> Text = new HashMap<String, String>();

    public PageEntity() {
        this.pageWrapper = new JPanel();
    }

    public abstract JPanel render();
}
