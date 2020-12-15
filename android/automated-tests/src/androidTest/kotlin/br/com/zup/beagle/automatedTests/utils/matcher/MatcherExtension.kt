/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.zup.beagle.automatedTests.utils.matcher

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.support.design.widget.TabLayout
import android.support.test.espresso.*
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.TextView
import br.com.zup.beagle.widget.core.TextAlignment
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


class MatcherExtension {

    companion object {
        fun exists(interaction: ViewInteraction): Boolean {
            return try {
                interaction.perform(object : ViewAction {
                    override fun getConstraints(): Matcher<View> {
                        return any(View::class.java)
                    }

                    override fun getDescription(): String {
                        return "check for existence"
                    }

                    override fun perform(
                        uiController: UiController?,
                        view: View?
                    ) {
                    }
                })
                true
            } catch (ex: AmbiguousViewMatcherException) {
                true
            } catch (ex: NoMatchingViewException) {
                false
            } catch (ex: NoMatchingRootException) {
                false
            }
        }

        fun withTextAlignment(textAlignment: TextAlignment): Matcher<View?>? {
            return object : TypeSafeMatcher<View>() {
                override fun matchesSafely(item: View): Boolean {
                    if (item is TextView) {
                        return when (textAlignment) {
                            TextAlignment.CENTER -> {
                                item.gravity == Gravity.CENTER
                            }
                            TextAlignment.LEFT -> {
                                item.gravity == (Gravity.START + Gravity.TOP)
                            }
                            TextAlignment.RIGHT -> {
                                item.gravity == (Gravity.END + Gravity.TOP)
                            }
                        }
                    }
                    return false
                }

                override fun describeTo(description: Description) {
                    description.appendText("textAlignment is $textAlignment ")
                }
            }
        }

        fun withTextColor(color: String): Matcher<View?>? {
            return object : TypeSafeMatcher<View>() {
                override fun matchesSafely(item: View): Boolean {
                    if (item is TextView) {
                        return item.currentTextColor == Color.parseColor(color)
                    }
                    return false
                }

                override fun describeTo(description: Description) {
                    description.appendText("textColor is $color ")
                }
            }
        }

        fun tabBarItemWithIconAndTitle(title: String? = null, icon: Int): Matcher<View?>? {
            return object : TypeSafeMatcher<View>() {
                override fun matchesSafely(item: View): Boolean {
                    if (item is TabLayout) {
                        for (i: Int in 0 until item.tabCount) {
                            val tabItem = item.getTabAt(i)
                            return try {
                                val bitmap: Bitmap = (tabItem?.icon as BitmapDrawable).bitmap
                                val otherBitmap: Bitmap = (ContextCompat.getDrawable(item.context, icon) as BitmapDrawable).bitmap
                                if(title == null){
                                    bitmap.sameAs(otherBitmap)
                                } else {
                                    bitmap.sameAs(otherBitmap) && tabItem.text == title
                                }
                            } catch (e: Exception) {
                                false
                            }
                        }
                    }
                    return false
                }

                override fun describeTo(description: Description) {
                    description.appendText("tab.icon is $icon and tab.text is $title")
                }
            }
        }
    }

}
