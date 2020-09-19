plugins {
    kotlin("jvm") version "1.4.10"
    maven
}

group = "com.rqbik"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
    maven { setUrl("https://gitlab.com/XjCyan1de/maven-repo") }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper", "paper", "1.16.2-R0.1-SNAPSHOT")
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileJava { options.encoding = "UTF-8" }
}
