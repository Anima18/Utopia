package com.chris.utopia.module.idea.interactor;

import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.ThingClasses;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public interface IdeaInteractor {
    List<ThingClasses> findThingClassess(ThingClasses classes) throws SQLException;
    ThingClasses findThingClassessById(String id) throws SQLException;
    void addThingClassess(ThingClasses classes) throws SQLException;
    List<Idea> findIdea(Idea idea) throws SQLException;
    Idea findIdeaById(String id) throws SQLException;
    void addIdea(Idea idea) throws SQLException;
    void addIdea(List<Idea> ideas) throws Exception;
    void delete(Idea idea) throws SQLException;
}
