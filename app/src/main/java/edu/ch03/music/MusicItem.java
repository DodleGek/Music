package edu.ch03.music;

public class MusicItem {
    private String name;
    private int resId;

    public MusicItem(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public String toString() {
        return name;
    }
}