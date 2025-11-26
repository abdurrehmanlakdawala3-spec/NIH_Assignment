import org.gradle.api.JavaVersion
import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
    java
    idea
    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.4"
    id("checkstyle")
    id("pmd")
    id("jacoco")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_21.toString()))
    }
}

repositories {
    mavenCentral()
}

dependencies {

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

/*  ===============================
       JaCoCo Configuration
    =============================== */
jacoco {
    toolVersion = "0.8.11"
}

tasks.withType<JacocoReport> {
    dependsOn(tasks.test)
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }
}

/*  ===============================
        Checkstyle config
    =============================== */
checkstyle {
    toolVersion = "10.13.0"
    configFile = file("config/checkstyle/checkstyle.xml")
    isIgnoreFailures = true
    isShowViolations = false
}

/*  ===============================
          PMD config
    =============================== */
pmd {
    toolVersion = "6.55.0"
    ruleSets = emptyList()
    ruleSetFiles = files("$rootDir/config/pmd/pmd-ruleset.xml")
    isIgnoreFailures = true
}

/* ==========================
   Encoding & compiler flags
   ========================== */
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(21)
}