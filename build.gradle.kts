plugins {
    kotlin("jvm") version "1.4.10"
    maven
}

group = "com.rqbik"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
    maven("https://gitlab.com/XjCyan1de/maven-repo/-/raw/master/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper", "paper", "1.16.3-R0.1-SNAPSHOT")
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileJava { options.encoding = "UTF-8" }
}
