package com.company.core.components.PageEntity;

import javax.swing.*;

public abstract class PageEntity {
    protected JPanel pageWrapper;

    PageEntity() {
        this.pageWrapper = new JPanel();
    }

    public JPanel render() {
        return pageWrapper;
    };
}
