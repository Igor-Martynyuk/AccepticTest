package com.acceptic.test.layer.domain

import android.util.Log
import com.acceptic.test.layer.core.entities.Mode
import com.acceptic.test.layer.data.mode.abstractions.ModeRepository
import com.acceptic.test.layer.domain.abstractions.UseCase
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangeModeCase @Inject constructor(private val repository: ModeRepository) :
    UseCase<Unit, Mode>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun getObservable(args: Mode): Observable<Unit> {
        return when (repository.observeModes().firstOrError()) {
            args -> Completable.complete().toObservable()
            else -> repository.setMode(args).toObservable()
        }
    }

    fun execute(args: Mode, onError: (t: Throwable) -> Unit) =
        super.execute(
            args,
            object : DisposableObserver<Unit>() {
                override fun onComplete() {
                    Log.d("temp_log", "complete")
                }

                override fun onNext(ignored: Unit) {}
                override fun onError(t: Throwable) = onError.invoke(t)
            }
        )

}