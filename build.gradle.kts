plugins {
    kotlin("jvm") version "1.4.10"
    `maven-publish`
}

group = "com.rqbik"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
//    compileOnly("com.destroystokyo.paper", "paper-api", "1.16.2-R0.1-SNAPSHOT")
    compileOnly("com.destroystokyo.paper", "paper", "1.16.2-R0.1-SNAPSHOT")
}

publishing {
    val sourcesJar by tasks.creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.getByName("main").allSource)
    }

    publications {
        create<MavenPublication>("maven") {
            groupId = this.groupId
            artifactId = this.name
            version = this.version.toString()
            artifact(sourcesJar)
            from(components["java"])
        }
    }
}
