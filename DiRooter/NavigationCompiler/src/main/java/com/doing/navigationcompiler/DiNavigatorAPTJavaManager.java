package com.doing.navigationcompiler;

import com.doing.navigatorannotation.Destination;

import java.util.HashMap;
import java.util.Map;

public class DiNavigatorAPTJavaManager {

    private static final DiNavigatorAPTJavaManager instance = new DiNavigatorAPTJavaManager();
    private final Map<String, Destination> map = new HashMap<>();

    public static DiNavigatorAPTJavaManager getInstance() {
        return instance;
    }

    public Map<String, Destination> getMap() {
        return map;
    }

}
