package com.acceptic.test.layer.core.navigator

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.acceptic.test.layer.core.navigator.abstractions.FragmentOwner
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused", "MemberVisibilityCanBePrivate")
@Singleton
class Navigator @Inject constructor(
    private val context: Context,
    private val activityMap: Map<Class<out LifecycleOwner>, Class<out AppCompatActivity>>,
    private val fragmentMap: Map<Class<out LifecycleOwner>, Class<out androidx.fragment.app.Fragment>>
) {
    val activity = Activity(this.context)
    val fragment = Fragment(this.context)

    inner class Activity(private val context: Context) {

        fun <T> forward(target: Class<out T>) where T : LifecycleOwner = this.forward(target, -1)
        fun <T> forward(target: Class<out T>, flags: Int) where T : LifecycleOwner {
            this.context.startActivity(
                Intent(this.context, activityMap.getValue(target))
                    .addFlags(if (flags < 0) Intent.FLAG_ACTIVITY_NEW_TASK else Intent.FLAG_ACTIVITY_NEW_TASK.or(flags))
            )
        }

    }

    inner class Fragment(private val context: Context?) {

        fun <T> forward(owner: FragmentOwner, target: T) where T : Class<out LifecycleOwner> =
            this.forward(owner, target, -1, -1)

        fun <T> forward(
            owner: FragmentOwner,
            target: T,
            inAnim: Int,
            outAnim: Int
        ) where T : Class<out LifecycleOwner> {
            val manager = owner.getActualFragmentManager()
            val parent = owner.getParentFor(target)
            val current = owner.getActualFragmentManager().findFragmentById(parent.id)

            if (current == null || target != current::class) {
                val transaction = manager.beginTransaction()
                var next = manager.findFragmentByTag(target.name)

                if (next == null)
                    next = fragmentMap.getValue(target).newInstance()
                if (inAnim > 0 && outAnim > 0)
                    transaction.setCustomAnimations(inAnim, outAnim)
                if (current != null)
                    transaction.addToBackStack(target.name)

                transaction.replace(parent.id, next!!, target.name).commitAllowingStateLoss()
            }
        }

        fun <T> replace(owner: FragmentOwner, target: T) where T : Class<out LifecycleOwner> =
            this.replace(owner, target, -1, -1)

        fun <T> replace(
            owner: FragmentOwner,
            target: T,
            inAnim: Int,
            outAnim: Int
        ) where T : Class<out LifecycleOwner> {
            val manager = owner.getActualFragmentManager()

            if (manager.backStackEntryCount > 0)
                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            this.forward(owner, target, inAnim, outAnim)
        }

        fun back(owner: FragmentOwner) = owner.getActualFragmentManager().popBackStack()
        fun <T> backTo(owner: FragmentOwner, target: T) where T : Class<out LifecycleOwner> =
            owner.getActualFragmentManager().popBackStack(target.name, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }

}