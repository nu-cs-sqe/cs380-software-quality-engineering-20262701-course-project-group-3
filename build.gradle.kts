plugins {
    id("java")
    id("application")
    id("checkstyle")
    jacoco
    id("info.solidsoft.pitest") version "1.19.0"
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.compileJava {
    options.release = 11
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}


tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}

application {
    mainClass = "ui.Main"
}

checkstyle {
    toolVersion = "10.21.0"
}

pitest {
    junit5PluginVersion.set("1.2.3")

    targetClasses.set(
        setOf(
            "domain.*",
            "domain.piece.*",
            "domain.state.*"
        )
    )

    targetTests.set(setOf("domain.*"))

    outputFormats.set(setOf("HTML", "XML"))
    timestampedReports.set(false)
    threads.set(2)
}