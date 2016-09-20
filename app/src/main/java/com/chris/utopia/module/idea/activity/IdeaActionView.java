package com.chris.utopia.module.idea.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.Idea;

import java.util.List;

/**
 * Created by Chris on 2016/1/24.
 */
public interface IdeaActionView extends BaseActionView {
    void loadIdeaData(List<Idea> ideaList);
}
