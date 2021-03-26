package com.company.modules;

import com.company.Main;
import com.company.core.components.PageEntity.PageEntity;

import javax.swing.*;
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
        JFrame frame = new JFrame(MainFrame.Text.get("frame-title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setSize(width, height);
        frame.setVisible(true);

        JPanel container = new JPanel();
        container.setBounds(0, 0, width, height);
        frame.add(container);
    }

    private void cleanContainer() {
        container.removeAll();
        container.revalidate();
        container.repaint();
    }

    public void renderPage(PageEntity newPage) {
        cleanContainer();
        container.add(newPage.render());
    }
}
