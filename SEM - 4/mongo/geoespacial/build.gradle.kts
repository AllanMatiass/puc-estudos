plugins {
    id("java")
    application
}

group = "br.com.geo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(platform("org.mongodb:mongodb-driver-bom:5.12.0"))
    implementation("org.mongodb:mongodb-driver-sync")
}

application {
    mainClass.set("br.com.geo.Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.test {
    useJUnitPlatform()
}