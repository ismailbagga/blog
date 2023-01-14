package com.ismail.personalblogpost.projectors;

import com.ismail.personalblogpost.Tag.Tag;

public interface TagWithCountProjector {

    Long getId();

    String getSlug();

    String getTitle();

    int getCount();
}
