package com.chris.utopia.module.system.presenter;

import android.content.Context;

import com.chris.utopia.common.constant.Constant;
import com.chris.utopia.common.util.CollectionUtil;
import com.chris.utopia.common.util.DateUtil;
import com.chris.utopia.common.util.SharedPrefsUtil;
import com.chris.utopia.entity.Role;
import com.chris.utopia.entity.User;
import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.idea.interactor.IdeaInteractor;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.chris.utopia.module.role.interactor.RoleInteractor;
import com.chris.utopia.module.system.activity.LoginActionView;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.google.inject.Inject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2016/1/19.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private LoginActionView actionView;
    private Context context;

    @Inject
    private SystemInteractor interactor;
    @Inject
    private RoleInteractor roleInteractor;
    @Inject
    private IdeaInteractor ideaInteractor;
    @Inject
    private PlanInteractor planInteractor;
    @Inject
    private ThingInteractor thingInteractor;

    @Override
    public void setActionView(LoginActionView actionView) {
        this.actionView = actionView;
        this.context = actionView.getContext();
    }

    @Override
    public void login(User user) {
        try {
            List<User> users = interactor.findUser(user);
            if(CollectionUtil.isNotEmpty(users)) {
                //init login
                SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_ID, users.get(0).getUserId());
                SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_NAME, users.get(0).getName());
                SharedPrefsUtil.putStringValue(context, Constant.SP_KEY_LOGIN_USER_EMAIL, users.get(0).getEmail());
                actionView.toMainPage();
            }else {
                actionView.showLoginMessage("登录失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            actionView.showLoginMessage("登录失败");
        }
    }

    @Override
    public void initData() {
        try {
            String userId = SharedPrefsUtil.getStringValue(context, Constant.SP_KEY_LOGIN_USER_ID, "");
            String userName = SharedPrefsUtil.getStringValue(context, Constant.SP_KEY_LOGIN_USER_NAME, "");
            String dateTimeStr = DateUtil.toString(new Date(), Constant.DATETIME_FORMAT_6);
            //init role
            List<Role> roleList = new ArrayList<>();
            Role mySelfRole = new Role("自己", "1. 善良、有爱心\n" +
                    "                    在事情不明朗时，往好的方向理解；\n" +
                    "                    乐于帮助有困难的人；\n" +
                    "                    可以不喜欢，但不能讨厌，尊重别人和事物。\n" +
                    "\n" +
                    "               2. 积极主动\n" +
                    "                    热爱生活、热爱身边的一切\n" +
                    "                    站在面对未来的最前面\n" +
                    "\n" +
                    "               3. 认真、负责、奉献\n" +
                    "                    回答问题或验证结果，不能从心里觉得应该没有问题；要肯定。\n" +
                    "                    与人相处时，不能敷衍，要让对方觉得自己很重要；\n" +
                    "                    主动帮助别人\n" +
                    "\n" +
                    "               4. 学习不倦\n" +
                    "                    不断学习，增强事业的竞争力；\n" +
                    "                    爱看书，改掉自己错误的思想 和 吸收好的想法\n" +
                    "\n" +
                    "               5. 包容\n" +
                    "                    在坚持自己的立场下；首先认同别人的观点，认同不了尝试理解，理解不了一定要尊重。\n", userId, userName, dateTimeStr);
            Role sonRole = new Role("儿子", "关心父母健康、生活；分担他们的忧虑、给予他们期盼。", userId, userName, dateTimeStr);
            Role sisterRole = new Role("兄弟姐妹", "更多感情交流，清楚他们的生活重要事情。帮助他们度过生活的困难，分享他们的生活乐趣。", userId, userName, dateTimeStr);
            Role friendRole = new Role("朋友", "主动联系、互相关心", userId, userName, dateTimeStr);
            Role staffRole = new Role("员工", "积极主动；不推脱工作；不表现不乐意。\n" +
                    "                    稳重，遇到难题不慌乱；\n" +
                    "                    有影响力，帮助同事解决问题，带动同事学习。\n", userId, userName, dateTimeStr);
            Role colleagueRole = new Role("同事", "亲和力，主动和同事交流，同事愿意接近你。", userId, userName, dateTimeStr);
            Role strangerRole = new Role("陌生人", "微笑，给予需要的帮助。", userId, userName, dateTimeStr);
            Role learnRole = new Role("学者", "合理分配 专业知识学习 和 课外阅读。每天整理知识，学到的 和 不懂的。计划性和系统性", userId, userName, dateTimeStr);
            Role girlRole = new Role("异性", "主动，认真聆听", userId, userName, dateTimeStr);
            roleList.add(mySelfRole);
            roleList.add(sonRole);
            roleList.add(sisterRole);
            roleList.add(friendRole);
            roleList.add(staffRole);
            roleList.add(colleagueRole);
            roleList.add(strangerRole);
            roleList.add(learnRole);
            roleList.add(girlRole);
            roleInteractor.addRole(roleList);
            //init label
            /*List<ThingClasses> thingClassesList = new ArrayList<>();
            ThingClasses learnClasses = new ThingClasses("专业知识", "专业知识", userId, userName, dateTimeStr);
            ThingClasses readClasses = new ThingClasses("业余阅读", "业余阅读", userId, userName, dateTimeStr);
            ThingClasses workClasses = new ThingClasses("工作", "工作", userId, userName, dateTimeStr);
            ThingClasses movieClasses = new ThingClasses("电影", "电影", userId, userName, dateTimeStr);
            ThingClasses musicClasses = new ThingClasses("乐器", "乐器", userId, userName, dateTimeStr);
            ThingClasses workOutClasses = new ThingClasses("锻炼", "锻炼", userId, userName, dateTimeStr);
            ThingClasses makeDateClasses = new ThingClasses("约会", "约会", userId, userName, dateTimeStr);
            ThingClasses otherClasses = new ThingClasses("奇怪的", "奇怪的", userId, userName, dateTimeStr);

            thingClassesList.add(learnClasses);
            thingClassesList.add(workClasses);
            thingClassesList.add(readClasses);
            thingClassesList.add(movieClasses);
            thingClassesList.add(musicClasses);
            thingClassesList.add(workOutClasses);
            thingClassesList.add(makeDateClasses);
            thingClassesList.add(otherClasses);
            interactor.addThingClassess(thingClassesList);
            //init idea
            List<Idea> ideas = new ArrayList<>();
            ideas.add(new Idea("学好口琴", "增加生活趣味，培养兴趣，热爱生活", userId, musicClasses.getId(), dateTimeStr, "#C6EBFB", userName, dateTimeStr));
            ideas.add(new Idea("去淋雨", "戴着游泳镜去淋雨", userId, otherClasses.getId(), dateTimeStr, "#DFF4BB", userName, dateTimeStr));
            ideas.add(new Idea("好好学习，天天向上", "好好学习，天天向上。做妈妈的好孩子", userId, readClasses.getId(), dateTimeStr, "#FBF6BF", userName, dateTimeStr));
            ideas.add(new Idea("学好摄影", "拍出漂亮有意义的照片，记录生活的点滴", userId, otherClasses.getId(), dateTimeStr, "#CCFCEC", userName, dateTimeStr));
            ideaInteractor.addIdea(ideas);
            //init plan
            Plan readPlan = new Plan(mySelfRole.getId(), "如何阅读一本书", "通过阅读增进理解力, 学好阅读书籍。阅读分做四个层次基础阅读、检视阅读、分析阅读、主题阅读",
                    "50", userId, readClasses.getId(), "重要不紧迫", Constant.THING_STATUS_NEW, userName, dateTimeStr);
            Plan findWorkPlan = new Plan(staffRole.getId(), "面试准备工作", "寻找下一份工作，完善简历，获取面试经验",
                    "50", userId, workClasses.getId(), "重要紧迫", Constant.THING_STATUS_NEW, userName, dateTimeStr);
            Plan workOutPlan = new Plan(mySelfRole.getId(), "锻炼身体", "想拥有完美肉体，保持健康和强大的精神，这样才能做更多的事",
                    "50", userId, workOutClasses.getId(), "重要不紧迫", Constant.THING_STATUS_NEW, userName, dateTimeStr);
            Plan homePlan = new Plan(sonRole.getId(), "关心爸爸妈妈", "关心父母健康、生活；分担他们的忧虑、给予他们期盼",
                    "50", userId, learnClasses.getId(), "重要不紧迫", Constant.THING_STATUS_NEW, userName, dateTimeStr);
            List<Plan> planList = new ArrayList<>();
            planList.add(readPlan);
            planList.add(findWorkPlan);
            planList.add(workOutPlan);
            planList.add(homePlan);
            planInteractor.addPlan(planList);
            //init plan thing
            List<Thing> thingList = new ArrayList<>();
            thingList.add(new Thing("阅读第一章节", "通过阅读增进理解力, 学好阅读书籍。阅读分做四个层次基础阅读、检视阅读、分析阅读、主题阅读", Constant.THING_STATUS_NEW, "2016/03/12", "07:20:10",
            readClasses.getId(), "重要不紧迫", readPlan.getId(), mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));

            thingList.add(new Thing("哈哈哈1", "哈哈哈哈，红红火火，槐花槐花米", Constant.THING_STATUS_DONE, "2016/03/12", "12:20:10",
                    movieClasses.getId(), "重要紧迫", workOutPlan.getId(), mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));

            thingList.add(new Thing("哈哈哈2", "哈哈哈哈，红红火火，槐花槐花米", Constant.THING_STATUS_IGNORE, "2016/03/12", "11:20:10",
                    makeDateClasses.getId(), "重要紧迫", findWorkPlan.getId(), mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));

            thingList.add(new Thing("阅读第二章节", "通过阅读增进理解力, 学好阅读书籍。阅读分做四个层次基础阅读、检视阅读、分析阅读、主题阅读", Constant.THING_STATUS_NEW, "2016/03/13", "07:20:10",
                    readClasses.getId(), "重要不紧迫", readPlan.getId(), mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));
            thingInteractor.addThing(thingList);
            //init thing
            List<Thing> thingList2 = new ArrayList<>();
            thingList2.add(new Thing("uuuuuuuuuu", "通过阅读增进理解力, 学好阅读书籍。阅读分做四个层次基础阅读、检视阅读、分析阅读、主题阅读", Constant.THING_STATUS_NEW, "2016/03/14", "07:20:10",
                    readClasses.getId(), "重要不紧迫", null, mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));

            thingList2.add(new Thing("哈哈哈1", "哈哈哈哈，红红火火，槐花槐花米", Constant.THING_STATUS_DONE, "2016/03/14", "12:20:10",
                    movieClasses.getId(), "重要紧迫", null, mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));

            thingList2.add(new Thing("哈哈哈2", "哈哈哈哈，红红火火，槐花槐花米", Constant.THING_STATUS_IGNORE, "2016/03/14", "11:20:10",
                    makeDateClasses.getId(), "重要紧迫", null, mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));

            thingList2.add(new Thing("阅读第二章节", "通过阅读增进理解力, 学好阅读书籍。阅读分做四个层次基础阅读、检视阅读、分析阅读、主题阅读", Constant.THING_STATUS_NEW, "2016/03/14", "07:20:10",
                    readClasses.getId(), "重要不紧迫", null, mySelfRole.getId(), userId, "10", Constant.THING_TYPE_THING, userName, dateTimeStr));
            thingInteractor.addThing(thingList2);*/
            //init habit

            actionView.showMessage("初始化数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            actionView.showMessage("初始化数据失败");
        }
    }
}
