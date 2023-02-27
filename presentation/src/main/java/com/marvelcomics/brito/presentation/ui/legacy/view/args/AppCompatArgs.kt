package com.marvelcomics.brito.presentation.ui.legacy.view.args

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface AppCompatArgs {
    fun build(context: Context): Intent?
    fun launch(context: Context) = context.startActivity(build(context))
}

/**
 * Will delegate the corresponding property to be stored in the fragments
 * [Fragment.mArguments]. Not all types are supported. Only the types
 * supported by [bundleOf] will work. Otherwise an exception will be thrown.
 * Also make sure to set a value to the property before reading it. Otherwise
 * an exception will be thrown.
 *
 * To set arguments:
 * ```
 * val fragment = DestinationFragment().apply {
 *      propertyName = "Hello"
 * }
 * ```
 * To parse arguments:
 * ```
 * var propertyName: ObjectType by args()
 * ```
 *
 * @throws IllegalArgumentException In case a not supported type is used
 * @throws UninitializedPropertyAccessException If read before value was set.
 */
fun <T : Any?> args() = object : ReadWriteProperty<Fragment, T> {
    override operator fun getValue(
        thisRef: Fragment,
        property: KProperty<*>
    ): T = thisRef.arguments?.get(property.name) as T

    override operator fun setValue(
        thisRef: Fragment,
        property: KProperty<*>,
        value: T
    ) {
        if (thisRef.arguments == null) thisRef.arguments = bundleOf()

        thisRef.requireArguments().putAll(
            bundleOf(property.name to value)
        )
    }
}
