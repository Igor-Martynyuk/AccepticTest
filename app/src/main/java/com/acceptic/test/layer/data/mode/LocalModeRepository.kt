package com.acceptic.test.layer.data.mode

import android.content.SharedPreferences
import com.acceptic.test.layer.core.entities.Mode
import com.acceptic.test.layer.data.mode.abstractions.ModeRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LocalModeRepository @Inject constructor(private val prefs: SharedPreferences) : ModeRepository {
    private val keyMode = "mode_value"

    private fun getMode() = Mode.valueOf(
        this.prefs.getString(
            keyMode,
            Mode.NATIVE.name
        )!!
    )

    override fun observeModes(): Observable<Mode> = Observable.create {
        it.onNext(getMode())
        this.prefs.registerOnSharedPreferenceChangeListener { _, key -> if (key == keyMode) it.onNext(getMode()) }
    }

    override fun setMode(value: Mode) =
        Completable.fromAction { this.prefs.edit().putString(this.keyMode, value.name).apply() }


}