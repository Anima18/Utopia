package com.chris.utopia.module.idea.presenter;

import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.module.idea.activity.IdeaCreateActionView;

/**
 * Created by Chris on 2016/1/22.
 */
public interface IdeaCreatePresenter {
    void setActionView(IdeaCreateActionView actionView);
    ThingClasses getThingClassById(String id);
    void addIdea(Idea idea);
}
