package com.luanthanhthai.android.liteworkouttimer;

import java.util.UUID;

/**
 * Created by Thai on 13.05.2016.
 * Copyright (c) [2016] [Luan Thanh Thai]
 * See the file LICENSE.txt for copying permission
 */
public class Routine {

    private final UUID mId;
    private String mTitle;
    private String mDescription;
    private boolean mChecked;

    public Routine() {
        this(UUID.randomUUID());
    }

    public Routine(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
