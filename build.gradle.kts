plugins {
    id("java")
    id("application")
}


version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("com.gridnine.testing.main.Main")
}

tasks.jar {
    archiveBaseName.set("flight-filter")
    archiveVersion.set("1.0")
    manifest {
        attributes("Main-Class" to "com.gridnine.testing.main.Main")
    }
}

tasks.test {
    useJUnitPlatform()
}