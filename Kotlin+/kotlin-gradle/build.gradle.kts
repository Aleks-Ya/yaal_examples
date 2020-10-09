plugins {
    kotlin("jvm") version "1.4.10"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
}

