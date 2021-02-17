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

package br.com.zup.beagle.newanalytics

/**
 * This interface is used to represent the report generated by the analytics
 * @param type is a String that represent the type of our report, if the report is an action or a screen
 * @param platform is a String that represents the platform that generated the analytics report
 * @param values is a HashMap in which the key is the attribute of the report and the
 * the value is the value of the attribute that will be reported
 * @param timestamp is a Long that have the timestamp when the action was called.
 */
data class AnalyticsRecord(
    val type: String,
    val platform: String = "android",
    val attributes: Map<String, Any>? = null,
    val component: Map<String, Any>? = null,
    val beagleAction: String? = null,
    val event: String? = null,
    val additionalEntries: Map<String, Any>? = null,
    val timestamp: Long,
    val screen: String,
)
