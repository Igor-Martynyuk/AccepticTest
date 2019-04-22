package com.acceptic.test.layer.domain.abstractions

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

abstract class UseCase<T, A>(private val executor: Scheduler, private val handler: Scheduler) {

    protected abstract fun getObservable(args: A): Observable<T>

    protected fun execute(args: A, observer: DisposableObserver<T>): Disposable {
        return this.getObservable(args)
            .subscribeOn(executor)
            .observeOn(handler)
            .subscribeWith(observer)
    }

}