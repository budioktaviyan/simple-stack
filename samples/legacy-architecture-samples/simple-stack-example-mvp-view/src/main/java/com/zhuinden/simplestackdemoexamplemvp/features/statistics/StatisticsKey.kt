package com.zhuinden.simplestackdemoexamplemvp.features.statistics

import android.view.View
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestack.navigator.ViewChangeHandler
import com.zhuinden.simplestack.navigator.changehandlers.SegueViewChangeHandler
import com.zhuinden.simplestackdemoexamplemvp.R
import com.zhuinden.simplestackdemoexamplemvp.core.navigation.ViewKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.lookup
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticsKey(val placeholder: String) : ViewKey, DefaultServiceProvider.HasServices {
    override fun getScopeTag(): String = " Statistics"

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            addService(StatisticsView.CONTROLLER_TAG, StatisticsPresenter(
                lookup()
            ))
        }
    }

    constructor() : this("")

    override fun layout(): Int = R.layout.path_statistics

    override val isFabVisible: Boolean
        get() = false

    override fun viewChangeHandler(): ViewChangeHandler = SegueViewChangeHandler()

    override fun menu(): Int = R.menu.empty_menu

    override fun navigationViewId(): Int = R.id.statistics_navigation_menu_item

    override fun shouldShowUp(): Boolean = false

    override fun fabClickListener(view: View): View.OnClickListener = View.OnClickListener { v -> }

    override fun fabDrawableIcon(): Int = 0
}
