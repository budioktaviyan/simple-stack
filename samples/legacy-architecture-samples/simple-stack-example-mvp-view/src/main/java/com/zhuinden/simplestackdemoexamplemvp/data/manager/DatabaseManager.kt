package com.zhuinden.simplestackdemoexamplemvp.data.manager

import android.content.Context
import com.zhuinden.simplestackdemoexamplemvp.util.SchedulerHolder
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmConfiguration

/**
 * Created by Owner on 2017. 01. 26..
 */

class DatabaseManager(
    private val looperScheduler: SchedulerHolder
) {
    private var disposable: Disposable? = null

    fun init(context: Context) {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

    fun openDatabase() {
        disposable = Observable.create(ObservableOnSubscribe<Realm> { emitter ->
            val observableRealm = Realm.getDefaultInstance()
            val listener = RealmChangeListener<Realm> { realm ->
                if (!emitter.isDisposed()) {
                    emitter.onNext(observableRealm)
                }
            }
            observableRealm.addChangeListener(listener)
            emitter.setDisposable(Disposables.fromAction {
                observableRealm.removeChangeListener(listener)
                observableRealm.close()
            })
            emitter.onNext(observableRealm)
        }).subscribeOn(looperScheduler.scheduler).unsubscribeOn(looperScheduler.scheduler).subscribe()
    }

    fun closeDatabase() {
        val disposable = disposable
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
            this.disposable = null
        }
    }

    companion object {
        private const val TAG = "DatabaseManager"
    }
}
