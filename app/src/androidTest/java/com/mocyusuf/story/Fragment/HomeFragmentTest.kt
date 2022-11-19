package com.mocyusuf.story.Fragment

import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.mocyusuf.story.Data.ApiModule
import com.mocyusuf.story.Fragment.StoryFragment
import com.mocyusuf.story.Utils.EspressoIdlingResource
import com.mocyusuf.story.Utils.JsonConverter
import com.mocyusuf.story.Utils.launchFragmentInHiltContainer
import com.mocyusuf.story.R
import com.mocyusuf.story.StoryApp
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@MediumTest
@ExperimentalPagingApi
@HiltAndroidTest
class HomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        ApiModule.apiConfig = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun launchHomeFragmentSuccess() {
        launchFragmentInHiltContainer<StoryFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_story)).check(matches(isDisplayed()))

        onView(withText("Lorem")).check(matches(isDisplayed()))
    }

    @Test
    fun launchHomeFragmentEmptyOrError() {
        launchFragmentInHiltContainer<StoryFragment>()

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response_empty.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.tv_not_found)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_error)).check(matches(isDisplayed()))
    }
}