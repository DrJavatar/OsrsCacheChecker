import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
}

group = "me.javatar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("http://legionkt.com:8085/repository/maven-public/")
}

dependencies {
    implementation("com.displee:rs-cache-library:6.6")
    implementation("com.javatar:osrs-definitions:1.0-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.8.7")
    testImplementation(kotlin("test-junit"))
    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "15"
}