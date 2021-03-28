package com.company.core.components.ComponentEntity;

import javax.swing.*;
import java.util.HashMap;

public abstract class ComponentEntity {
    static public HashMap<String, String> Text = new HashMap<String, String>();

    public abstract JPanel render();
}
