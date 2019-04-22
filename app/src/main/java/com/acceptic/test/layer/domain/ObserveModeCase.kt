package com.acceptic.test.layer.domain

import com.acceptic.test.layer.core.entities.Mode
import com.acceptic.test.layer.data.mode.abstractions.ModeRepository
import com.acceptic.test.layer.domain.abstractions.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObserveModeCase @Inject constructor(private val repository: ModeRepository) :
    UseCase<Mode, Unit?>(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread()) {

    override fun getObservable(args: Unit?) = this.repository.observeModes()

    fun execute(onNext: (value: Mode) -> Unit, onError: (t: Throwable) -> Unit) = super.execute(
        null,
        object : DisposableObserver<Mode>() {
            override fun onComplete() {}
            override fun onNext(value: Mode) = onNext.invoke(value)
            override fun onError(t: Throwable) = onError.invoke(t)
        }
    )


}