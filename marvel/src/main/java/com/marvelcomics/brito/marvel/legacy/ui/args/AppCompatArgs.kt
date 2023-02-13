package com.marvelcomics.brito.marvel.legacy.ui.args

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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

private class BundleDelegate<T>(
    private val default: T? = null
) : ReadWriteProperty<Bundle, T> {
    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(
        thisRef: Bundle,
        property: KProperty<*>
    ): T = when (val value = thisRef.get(property.name)) {
        null -> default
        else -> value
    } as T

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: T) =
        thisRef.putAll(bundleOf(property.name to value))
}

private class MappedDelegate<In, Out, T>(
    private val source: ReadWriteProperty<In, T>,
    private val postWrite: ((Out, In) -> Unit)? = null,
    private val mapper: (Out) -> In
) : ReadWriteProperty<Out, T> {

    override fun getValue(thisRef: Out, property: KProperty<*>): T =
        source.getValue(mapper(thisRef), property)

    override fun setValue(thisRef: Out, property: KProperty<*>, value: T) {
        val mapped = mapper(thisRef)
        source.setValue(mapped, property, value)
        postWrite?.invoke(thisRef, mapped)
    }
}

fun <In, Out, T> ReadWriteProperty<In, T>.map(
    postWrite: ((Out, In) -> Unit)? = null,
    mapper: (Out) -> In
): ReadWriteProperty<Out, T> =
    MappedDelegate(source = this, postWrite = postWrite, mapper = mapper)

fun <T> bundleDelegate(default: T? = null): ReadWriteProperty<Bundle, T> =
    BundleDelegate(default)

fun <T> intentExtras(default: T? = null): ReadWriteProperty<Intent, T> =
    bundleDelegate(default).map(
        postWrite = Intent::replaceExtras,
        mapper = Intent::ensureExtras
    )

fun <T> activityIntent(default: T? = null): ReadWriteProperty<Activity, T> =
    intentExtras(default).map(
        postWrite = Activity::setIntent,
        mapper = Activity::getIntent
    )

fun <T> fragmentArgs(): ReadWriteProperty<Fragment, T> = bundleDelegate<T>().map(
    mapper = Fragment::ensureArgs
)

fun <T> Bundle.asDelegate(default: T? = null): ReadWriteProperty<Any?, T> =
    bundleDelegate(default).map(
        mapper = { this }
    )

private val Intent.ensureExtras get() = extras ?: putExtras(Bundle()).let { extras!! }

private val Fragment.ensureArgs get() = arguments ?: Bundle().also(::setArguments)

/**
 * Returns a [Lazy] instance that parses the receiver's input intent and extracts an extra from type
 * [Type] mapped by the input [key].
 *
 * The returned value can be used as a property delegate, for instance:
 *
 * ```kotlin
 * class MyActivity : Activity() {
 *
 *   private val name by extra<String>("name")
 * }
 * ```
 *
 * If such value can't be retrieved, an exception will be thrown. This will happen when:
 *
 * - There is an extra mapped by [key] and it can't be cast to [Type]
 * - There's no extra mapped by [key] and [Type] is not a nullable type
 *
 * @param key The extra key
 * @param Type The extra type
 * @return A [Lazy] that returns an intent extra
 */
inline fun <reified Type> Activity.extra(key: String): Lazy<Type> = lazy {
    val value = intent.extras?.get(key)
    if (value is Type) {
        value
    } else {
        throw IllegalArgumentException(
            "Couldn't find extra with key \"$key\" from type " +
                Type::class.java.canonicalName
        )
    }
}

/**
 * Returns a [Lazy] instance that parses the receiver's input intent and extracts an extra from type
 * [Type] mapped by the input [key], returning the result of the [default] function in case the extra
 * can't be extracted.
 *
 * The returned value can be used as a property delegate, for instance:
 *
 * ```kotlin
 * class MyActivity : Activity() {
 *
 *   private val name by extra("name") { "Default name" }
 * }
 * ```
 *
 * If such value can't be retrieved, the result from [default] will be returned. This will happen
 * when:
 *
 * - There is an extra mapped by [key] and it can't be cast to [Type]
 * - There's no extra mapped by [key] and [Type] is not a nullable type
 *
 * @param key The extra key
 * @param default A function that returns the default value
 * @param Type The extra type
 * @return A [Lazy] that returns an intent extra
 */
inline fun <reified Type> Activity.extra(key: String, crossinline default: () -> Type): Lazy<Type> =
    lazy {
        val value = intent.extras?.get(key)
        if (value is Type) value else default()
    }
