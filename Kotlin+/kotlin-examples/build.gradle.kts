plugins {
    kotlin("jvm") version "1.4.21"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("io.mockk:mockk:1.10.2")
    testImplementation("org.assertj:assertj-core:3.17.2")
}

tasks.test {
    useJUnitPlatform()
}
