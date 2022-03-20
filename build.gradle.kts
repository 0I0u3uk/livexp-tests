plugins {
    java
    id("io.qameta.allure") version "2.9.6"
}

group = "com.livexp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("com.codeborne:selenide:6.3.5")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("io.qameta.allure:allure-junit5:2.17.3")
    testImplementation("io.qameta.allure:allure-selenide:2.17.3")
    implementation("org.aeonbits.owner:owner:1.0.12")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}