package com.example.nettruyennews

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.nettruyennews.data.database.BookDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val url = "http://www.nettruyengo.com/truyen-tranh/nguoi-xau-41532"

        var rata = "http://www.nettruyengo.com/truyen-tranh/nguoi-xau-41532"
        val database = BookDatabase.getInstance(appContext)

        runBlocking {
            val text = database.chapterDao.chapterByLink(rata)

            assertEquals(null, text)
        }
    }
}