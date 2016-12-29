package com.chris.utopia.module.home.interactor;

import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chris on 2016/2/8.
 */
public interface ThingInteractor {
    void addThing(Thing thing) throws SQLException;
    ThingClasses findThingClassessById(String id) throws SQLException;
    Role findRoleById(String id) throws SQLException;
    List<Thing> findThing(Thing thing) throws SQLException;
    List<Thing> findWeekThing(Plan plan) throws SQLException;
    List<Thing> findHabit(Thing thing) throws SQLException;
    List<Thing> findHabitOneDay(Thing thing) throws SQLException;
    void deleteThing(Thing thing) throws SQLException;
    void deleteAll(List<Thing> thingList) throws SQLException;
    Thing findThingById(String id) throws SQLException;
    List<Thing> search(Thing thing) throws SQLException;
    List<Thing> findMessage(Thing thing) throws SQLException;
    void addThing(List<Thing> thingList) throws Exception;
}
