package com.example.lint.checks

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Detector.UastScanner
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULiteralExpression
import org.jetbrains.uast.evaluateString
import java.io.File
import java.io.FileWriter

/**
 * Sample detector showing how to analyze Kotlin/Java code. This example
 * flags all string literals in the code that contain the word "lint".
 * 负责扫描代码，发现问题并报告。
 * UastScanner java代码，支持kotlin
 */
@Suppress("UnstableApiUsage")
class SampleCodeDetector : Detector(), UastScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement?>> {
        return listOf(ULiteralExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitLiteralExpression(node: ULiteralExpression) {

                val string = node.evaluateString() ?: return

                val fileList = mutableListOf<String>()

                if (string.contains("lint") && string.matches(Regex(".*\\blint\\b.*"))) {

                    val location = context.getLocation(node)
                    println(location.file)
                    println(location.file.name)
                    fileList.add(location.file.name)

                    //写入文件 可做定制化操作
                    val filePath = "/Users/yechao/AndroidStudioProjects/wanandroid_jetpack/app/build/reports/"
                    val outPutFile = File("$filePath/cla.txt")
                    outPutFile.createNewFile()
                    val fileWriter = FileWriter(outPutFile)
                    fileList.forEach {
                        fileWriter.write(it + "\n")
                    }
                    fileWriter.close()

                    context.report(
                        ISSUE, node, context.getLocation(node),
                        "This code mentions `lint`: **Congratulations**"
                    )
                }

            }
        }
    }

    /**
     * 检测注解调用
     */
    override fun applicableAnnotations(): List<String> {
        return listOf("retrofit2.http.FormUrlEncoded")
    }

    override fun visitAnnotationUsage(
        context: JavaContext,
        usage: UElement,
        type: AnnotationUsageType,
        annotation: UAnnotation,
        qualifiedName: String,
        method: PsiMethod?,
        annotations: List<UAnnotation>,
        allMemberAnnotations: List<UAnnotation>,
        allClassAnnotations: List<UAnnotation>,
        allPackageAnnotations: List<UAnnotation>
    ) {
        super.visitAnnotationUsage(
            context,
            usage,
            type,
            annotation,
            qualifiedName,
            method,
            annotations,
            allMemberAnnotations,
            allClassAnnotations,
            allPackageAnnotations
        )
        val name = qualifiedName.substringAfterLast('.')
        val message = "`${type.name}` usage associated with `@$name`"
        val location = context.getLocation(usage)
        context.report(ISSUE, usage, location, message)
    }

    companion object {
        /**
         * Issue describing the problem and pointing to the detector
         * implementation.
         * 参数含义：
         * id - the fixed id of the issue
         * briefDescription - short summary (typically 5-6 words or less), typically describing the problem rather than the fix (e.g. "Missing minSdkVersion")
         * explanation - a full explanation of the issue, with suggestions for how to fix it
         * category - the associated category, if any
         * priority - the priority, a number from 1 to 10 with 10 being most important/severe
         * severity - the default severity of the issue
         * implementation - the default implementation for this issue
         */
        @JvmField
        val ISSUE: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "CustomLintId",
            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            briefDescription = "Lint Mentions",
            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            explanation = """
                    This check highlights string literals in code which mentions the word `lint`. \
                    Blah blah blah.

                    Another paragraph here.
                    """, // no need to .trimIndent(), lint does that automatically
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(SampleCodeDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }
}
