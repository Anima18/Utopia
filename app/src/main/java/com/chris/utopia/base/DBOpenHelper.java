package com.chris.utopia.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chris.utopia.entity.Idea;
import com.chris.utopia.entity.Plan;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.Thing;
import com.chris.utopia.entity.ThingClasses;
import com.chris.utopia.entity.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBOpenHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "UTOPIA.db";
    private static final int DATABASE_VERSION = 1;

	private static Dao<User, String> userDao = null;
	private static Dao<Idea, String> ideaDao = null;
	private static Dao<ThingClasses, String> thingClassesDao = null;
	private static Dao<Role, String> roleDao = null;

	private static Dao<Thing, String> thingDao = null;
	private static Dao<Plan, String> planDao = null;


	public DBOpenHelper(Context context){  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    } 
	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Idea.class);
			TableUtils.createTable(connectionSource, ThingClasses.class);
			TableUtils.createTable(connectionSource, Role.class);

			TableUtils.createTable(connectionSource, Thing.class);
			TableUtils.createTable(connectionSource, Plan.class);
		} catch (Exception e) {
            e.printStackTrace();  
        }
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer,
			int newVer) {
		try {
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, Idea.class, true);
			TableUtils.dropTable(connectionSource, ThingClasses.class, true);
			TableUtils.dropTable(connectionSource, Role.class, true);

			TableUtils.dropTable(connectionSource, Thing.class, true);
			TableUtils.dropTable(connectionSource, Plan.class, true);

			onCreate(sqLiteDatabase, connectionSource);
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
	}

	public Dao<User, String> getUserDao() throws SQLException {
		if(userDao == null) {
			userDao = getDao(User.class);
		}
		return userDao;
	}

	public Dao<Idea, String> getIdeaDao() throws SQLException {
		if(ideaDao == null) {
			ideaDao = getDao(Idea.class);
		}
		return ideaDao;
	}

	public Dao<ThingClasses, String> getThingClassesDao() throws SQLException {
		if(thingClassesDao == null) {
			thingClassesDao = getDao(ThingClasses.class);
		}
		return thingClassesDao;
	}

	public Dao<Role, String> getRoleDao() throws SQLException {
		if(roleDao == null) {
			roleDao = getDao(Role.class);
		}
		return roleDao;
	}

	public Dao<Thing, String> getThingDao() throws SQLException {
		if(thingDao == null) {
			thingDao = getDao(Thing.class);
		}
		return thingDao;
	}

	public Dao<Plan, String> getPlanDao() throws SQLException {
		if(planDao == null) {
			planDao = getDao(Plan.class);
		}
		return planDao;
	}

	@Override
	public void close() {
		super.close();
	}
}
