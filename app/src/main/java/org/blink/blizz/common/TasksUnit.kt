package org.blink.blizz.common

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks

fun Task<Void>.toUnit(): Task<Unit> =
    onSuccessTask { Tasks.forResult(Unit) }