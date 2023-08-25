package com.example.prueba_tecnica.base

import android.os.Handler
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.slot
import java.lang.reflect.Method

/**
 * Class base de test para acceder a variables y metodos privados
 * también para invocar métodos privados
 */
open class BaseTest {

    protected inline fun <reified T : Any> T.setPropertyValue(
        propertyName: String,
        propertyValue: Any?
    ) {
        this.javaClass.getDeclaredField(propertyName).apply { isAccessible = true }
            .set(this, propertyValue)
    }

    protected fun <T : Any> Any.getPropertyValue(propertyName: String): T =
        this.javaClass.getDeclaredField(propertyName).apply { isAccessible = true }.get(this) as T

    protected fun Any.accessMethod(methodName: String, vararg parameters: Class<*>?): Method {
        return this.javaClass.getDeclaredMethod(methodName, *parameters).apply {
            isAccessible = true
        }
    }

    protected fun Any.runMethod(methodName: String, vararg parameters: Any) =
        runMethod<Unit>(methodName, *parameters)

    protected fun <T> Any.runMethod(methodName: String, vararg parameters: Any): T =
        accessMethod(methodName, *parameters.map { it::class.java }.toTypedArray()).invoke(
            this,
            *parameters
        ) as T

    protected inline fun <reified T : Any> mockkRelaxed(b: T.() -> Unit = {}): T = mockk(relaxed = true, block = b)

    protected fun mockkHandler() {
        mockkConstructor(Handler::class)
        val runnableSlot = slot<Runnable>()
        every { anyConstructed<Handler>().post(capture(runnableSlot)) } answers {
            runnableSlot.captured.run()
            true
        }
    }
}