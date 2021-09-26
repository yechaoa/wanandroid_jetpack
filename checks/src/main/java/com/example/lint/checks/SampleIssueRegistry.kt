package com.example.lint.checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

/*
 * The list of issues that will be checked when running <code>lint</code>.
 */
@Suppress("UnstableApiUsage")
class SampleIssueRegistry : IssueRegistry() {
    override val issues = listOf(SampleCodeDetector.ISSUE)

    override val api: Int
        get() = CURRENT_API

    override val vendor: Vendor = Vendor(
        vendorName = "CustomLint",
        contact = "yechaoa"
    )
}
