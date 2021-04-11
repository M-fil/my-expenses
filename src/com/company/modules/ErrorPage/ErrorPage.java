package com.company.modules.ErrorPage;

import com.company.core.components.ErrorBlock.ErrorBlock;
import com.company.core.components.PageEntity.PageEntity;

import javax.swing.*;
import java.awt.*;

public class ErrorPage extends PageEntity {
    private String errorMessage;
    private ErrorBlock errorBlock;

    static private int DEFAULT_ERROR_BLOCK_WIDTH = 400;

    public ErrorPage(String errorMessage) {
        ErrorPage.Text.put("error-title", "Error");
        this.errorMessage = errorMessage;
        errorBlock = new ErrorBlock(this.errorMessage, DEFAULT_ERROR_BLOCK_WIDTH, "center", Color.RED);
    }

    @Override
    public JPanel render() {
        pageWrapper = new JPanel();
        pageWrapper.setLayout(new GridBagLayout());
        JPanel errorBlockPanel = errorBlock.render();
        errorBlockPanel.setLayout(new BoxLayout(errorBlockPanel, BoxLayout.Y_AXIS));
        pageWrapper.add(errorBlockPanel);

        return pageWrapper;
    }
}
