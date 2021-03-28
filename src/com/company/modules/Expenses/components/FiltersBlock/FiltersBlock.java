package com.company.modules.Expenses.components.FiltersBlock;

import com.company.core.components.ComponentEntity.ComponentEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class FiltersBlock extends ComponentEntity {
    public FiltersBlock() {
        FiltersBlock.Text.put("apply-filters-button-text", "Apply Filters");
        FiltersBlock.Text.put("reset-filters-button-text", "Reset");
    }

    @Override
    public JPanel render() {
        JPanel filtersContainer = new JPanel();

        JButton applyFiltersButton = new JButton(FiltersBlock.Text.get("apply-filters-button-text"));
        JButton resetFilterButton = new JButton(FiltersBlock.Text.get("reset-filters-button-text"));
        JButton searchExpensesButton = new JButton();

        try {
            Image img = ImageIO.read(getClass().getResource("assets/images/expenses/search.png"));
            searchExpensesButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        filtersContainer.add(applyFiltersButton);
        filtersContainer.add(resetFilterButton);
        filtersContainer.add(searchExpensesButton);

        return filtersContainer;
    }
}
