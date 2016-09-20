package com.chris.utopia.common.config;

import com.chris.utopia.module.home.interactor.ThingInteractor;
import com.chris.utopia.module.home.interactor.ThingInteractorImpl;
import com.chris.utopia.module.home.presenter.HabitPresenter;
import com.chris.utopia.module.home.presenter.HabitPresenterImpl;
import com.chris.utopia.module.home.presenter.MessagePresenter;
import com.chris.utopia.module.home.presenter.MessagePresenterImpl;
import com.chris.utopia.module.home.presenter.ProfilePresenter;
import com.chris.utopia.module.home.presenter.ProfilePresenterImpl;
import com.chris.utopia.module.home.presenter.SearchPresenter;
import com.chris.utopia.module.home.presenter.SearchPresenterImpl;
import com.chris.utopia.module.home.presenter.ThingCreatePresenter;
import com.chris.utopia.module.home.presenter.ThingCreatePresenterImpl;
import com.chris.utopia.module.home.presenter.TimeAnalysisPresenter;
import com.chris.utopia.module.home.presenter.TimeAnalysisPresenterImpl;
import com.chris.utopia.module.home.presenter.TimerPresenter;
import com.chris.utopia.module.home.presenter.TimerPresenterImpl;
import com.chris.utopia.module.home.presenter.TodayTaskPresenter;
import com.chris.utopia.module.home.presenter.TodayTaskPresenterImpl;
import com.chris.utopia.module.home.presenter.WeekPlanPresenter;
import com.chris.utopia.module.home.presenter.WeekPlanPresenterImpl;
import com.chris.utopia.module.idea.interactor.IdeaInteractor;
import com.chris.utopia.module.idea.interactor.IdeaInteractorImpl;
import com.chris.utopia.module.idea.presenter.IdeaCreatePresenter;
import com.chris.utopia.module.idea.presenter.IdeaCreatePresenterImpl;
import com.chris.utopia.module.idea.presenter.IdeaPresenter;
import com.chris.utopia.module.idea.presenter.IdeaPresenterImpl;
import com.chris.utopia.module.plan.interactor.PlanInteractor;
import com.chris.utopia.module.plan.interactor.PlanInteractorImpl;
import com.chris.utopia.module.plan.presenter.PlanCreatePresenter;
import com.chris.utopia.module.plan.presenter.PlanCreatePresenterImpl;
import com.chris.utopia.module.plan.presenter.PlanPresenter;
import com.chris.utopia.module.plan.presenter.PlanPresenterImpl;
import com.chris.utopia.module.role.interactor.RoleInteractor;
import com.chris.utopia.module.role.interactor.RoleInteractorImpl;
import com.chris.utopia.module.role.presenter.RoleCreatePresenter;
import com.chris.utopia.module.role.presenter.RoleCreatePresenterImpl;
import com.chris.utopia.module.role.presenter.RolePresenter;
import com.chris.utopia.module.role.presenter.RolePresenterImpl;
import com.chris.utopia.module.system.interactor.SystemInteractor;
import com.chris.utopia.module.system.interactor.SystemInteractorImpl;
import com.chris.utopia.module.system.presenter.LoginPresenter;
import com.chris.utopia.module.system.presenter.LoginPresenterImpl;
import com.chris.utopia.module.system.presenter.RegisterPresenter;
import com.chris.utopia.module.system.presenter.RegisterPresenterImpl;
import com.google.inject.AbstractModule;

/**
 * Created by Chris on 2015/8/25.
 */
public class MsmModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SystemInteractor.class).to(SystemInteractorImpl.class);
        bind(RegisterPresenter.class).to(RegisterPresenterImpl.class);
        bind(LoginPresenter.class).to(LoginPresenterImpl.class);

        bind(IdeaCreatePresenter.class).to(IdeaCreatePresenterImpl.class);
        bind(IdeaInteractor.class).to(IdeaInteractorImpl.class);
        bind(IdeaPresenter.class).to(IdeaPresenterImpl.class);

        bind(RoleCreatePresenter.class).to(RoleCreatePresenterImpl.class);
        bind(RoleInteractor.class).to(RoleInteractorImpl.class);
        bind(RolePresenter.class).to(RolePresenterImpl.class);

        bind(PlanCreatePresenter.class).to(PlanCreatePresenterImpl.class);
        bind(PlanInteractor.class).to(PlanInteractorImpl.class);
        bind(ThingCreatePresenter.class).to(ThingCreatePresenterImpl.class);

        bind(ThingInteractor.class).to(ThingInteractorImpl.class);
        bind(PlanPresenter.class).to(PlanPresenterImpl.class);
        bind(TodayTaskPresenter.class).to(TodayTaskPresenterImpl.class);

        bind(WeekPlanPresenter.class).to(WeekPlanPresenterImpl.class);
        bind(HabitPresenter.class).to(HabitPresenterImpl.class);
        bind(SearchPresenter.class).to(SearchPresenterImpl.class);

        bind(MessagePresenter.class).to(MessagePresenterImpl.class);
        bind(TimerPresenter.class).to(TimerPresenterImpl.class);
        bind(ProfilePresenter.class).to(ProfilePresenterImpl.class);

        bind(TimeAnalysisPresenter.class).to(TimeAnalysisPresenterImpl.class);
    }
}
