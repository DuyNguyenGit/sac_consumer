package com.sacombank.consumers.models.sidemenu;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DUY on 7/12/2017.
 */

public class SectionItem implements Parcelable {

    private String name;
    private boolean isLogined;

    public SectionItem(String name, boolean isLogined) {
        this.name = name;
        this.isLogined = isLogined;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionItem)) return false;

        SectionItem sectionItem = (SectionItem) o;

        return getName() != null ? getName().equals(sectionItem.getName()) : sectionItem.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        return result;
    }


    protected SectionItem(Parcel in) {
        name = in.readString();
        isLogined = in.readByte() != 0;
    }

    public static final Creator<SectionItem> CREATOR = new Creator<SectionItem>() {
        @Override
        public SectionItem createFromParcel(Parcel in) {
            return new SectionItem(in);
        }

        @Override
        public SectionItem[] newArray(int size) {
            return new SectionItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isLogined ? 1 : 0));
    }
}
