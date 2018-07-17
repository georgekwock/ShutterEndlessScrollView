package com.upday.qing.endlessscrollview.di.component;

import com.upday.qing.endlessscrollview.di.module.ShutterModule;
import com.upday.qing.endlessscrollview.di.scope.UserScope;
import com.upday.qing.endlessscrollview.viewmodel.ShutterListModel;

import dagger.Component;

/**
 * @Author Qing Guo
 * @Date 2018/7/15
 * @Description
 */
@UserScope
@Component(dependencies = NetworkComponent.class, modules = ShutterModule.class)
public interface ShutterComponent {
    void inject(ShutterListModel model);
}
