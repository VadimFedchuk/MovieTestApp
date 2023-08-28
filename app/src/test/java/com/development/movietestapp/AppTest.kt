package com.development.movietestapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.development.movietestapp.utils.PreferencesManager
import com.development.movietestapp.utils.USER_AVATAR_KEY
import com.development.movietestapp.utils.isSecondDateBeforeFirst
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AppTest {

    private lateinit var preferencesManager: PreferencesManager

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        preferencesManager = PreferencesManager(context)
    }

    @Test
    fun `check if user avatar saved`() {
        val testUrl = "some url"
        preferencesManager.saveData(USER_AVATAR_KEY, testUrl)
        val saved = preferencesManager.getData(USER_AVATAR_KEY, "")
        assertTrue(saved == testUrl)
    }

    @Test
    fun `check is date second is before first`() {
        val firstDate = "Aug 2023"
        val secondDate = "Jul 2023"
        assertTrue(firstDate.isSecondDateBeforeFirst(secondDate))

    }
}
