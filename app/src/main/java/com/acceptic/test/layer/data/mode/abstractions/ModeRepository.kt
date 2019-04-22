package com.acceptic.test.layer.data.mode.abstractions

import com.acceptic.test.layer.core.entities.Mode
import io.reactivex.Completable
import io.reactivex.Observable

interface ModeRepository {
    fun observeModes(): Observable<Mode>
    fun setMode(value: Mode): Completable
}