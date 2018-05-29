package com.sacombank.consumers.models.jsonobjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.sacombank.consumers.R;


/**
 * Created by DUY on 7/15/2017.
 */

public class User implements Parcelable {

    public static final int MALE = 1;
    public static final int FEMALE = 0;

    private String name;
    private String avatar;
    private int gender;
    private ProjectsEntity projects;

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setProjects(ProjectsEntity projects) {
        this.projects = projects;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public ProjectsEntity getProjects() {
        return projects;
    }

    public int getAvatarResource() {
        if (gender == MALE) return R.drawable.male;
        if (gender == FEMALE) return R.drawable.female;
        return R.drawable.male;
    }

    public String getGenderString() {
        if (gender == MALE) return "Male";
        if (gender == FEMALE) return "Female";
        return "Undefine";
    }

    public User(String name, String avatar, int gender) {
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
    }

    public User(Parcel in) {
        name = in.readString();
        avatar = in.readString();
        gender = in.readInt();
        projects = (ProjectsEntity) in.readValue(ProjectsEntity.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeInt(gender);
        dest.writeValue(projects);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public static class ProjectsEntity implements Parcelable {
        private String name;
        private String role;

        public void setName(String name) {
            this.name = name;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        protected ProjectsEntity(Parcel in) {
            name = in.readString();
            role = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(role);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<ProjectsEntity> CREATOR = new Parcelable.Creator<ProjectsEntity>() {
            @Override
            public ProjectsEntity createFromParcel(Parcel in) {
                return new ProjectsEntity(in);
            }

            @Override
            public ProjectsEntity[] newArray(int size) {
                return new ProjectsEntity[size];
            }
        };
    }
}
