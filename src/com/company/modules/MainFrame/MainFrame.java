package com.company.modules;

import com.company.Main;
import com.company.core.components.PageEntity.PageEntity;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame {
    static public HashMap<String, String> Text = new HashMap<String, String>();
    static public int DefaultWidth = 500;
    static public int DefaultHeight = 500;

    private JFrame frame;
    private JPanel container;
    private int width;
    private int height;

    public MainFrame(int width, int height) {
        MainFrame.Text.put("frame-title", "My Expenses");
        this.width = width;
        this.height = height;
    }

    public void init() {
        frame = new JFrame(MainFrame.Text.get("frame-title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(width, height);
        frame.setVisible(true);

        container = new JPanel();
        container.setBackground(Color.GREEN);
        container.setLayout(new BorderLayout());
        frame.add(container);
    }

    private void cleanContainer() {
        container.removeAll();
        rerenderElement(container);
    }

    private void rerenderElement(JComponent element) {
        element.repaint();
        element.revalidate();
    }

    public void renderPage(PageEntity newPage) {
        cleanContainer();
        container.add(newPage.render());
        rerenderElement(container);
    }
}
