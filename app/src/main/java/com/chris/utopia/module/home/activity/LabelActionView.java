package com.chris.utopia.module.home.activity;

import com.chris.utopia.common.view.BaseActionView;
import com.chris.utopia.entity.ThingClasses;

import java.util.List;

/**
 * Created by Chris on 2016/1/26.
 */
public interface LabelActionView extends BaseActionView {
    void loadLabelData(List<ThingClasses> labelList);
}
