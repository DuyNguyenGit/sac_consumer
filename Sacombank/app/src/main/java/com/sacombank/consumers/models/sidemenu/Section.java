package com.sacombank.consumers.models.sidemenu;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by DUY on 7/12/2017.
 */

public class Section extends ExpandableGroup<SectionItem> {

    private boolean isLogined;
    private int iconResId;
    private int sectionId;


    public Section(String title, List<SectionItem> items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    public Section(int sectionId, String title, List<SectionItem> items, int iconResId, boolean isLogined) {
        super(title, items);
        this.iconResId = iconResId;
        this.sectionId = sectionId;
        this.isLogined = isLogined;
    }

    public int getIconResId() {
        return iconResId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;

        Section section = (Section) o;

        return getIconResId() == section.getIconResId();

    }

    @Override
    public int hashCode() {
        return getIconResId();
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }
}