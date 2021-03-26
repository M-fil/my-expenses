package com.company;

import com.company.modules.MainFrame;
import com.company.modules.Registration.Registration;

public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(MainFrame.DefaultWidth, MainFrame.DefaultHeight);
        mainFrame.init();
        mainFrame.renderPage(new Registration());
    }
}
