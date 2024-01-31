import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("kapt") version "1.9.20"
    id("com.netflix.dgs.codegen") version "5.2.0" apply true
}

group = "hackathon"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

repositories {
    mavenCentral()
}

val swaggerVersion = "3.0.0"
val dgsVersion = "4.9.16"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // GSON
    implementation("com.google.code.gson:gson")

    // SWAGGER
    implementation("io.springfox:springfox-boot-starter:${swaggerVersion}")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // DGS
    implementation("com.graphql-java:graphql-java-extended-scalars")
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:$dgsVersion"))
    runtimeOnly("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter:$dgsVersion")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework:spring-webflux")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.security:spring-security-test")

    // MOCKK
    testImplementation("io.mockk:mockk:1.13.3")

    // TEST
    testImplementation("io.kotest:kotest-runner-junit5:4.6.3")
    testImplementation("io.kotest:kotest-assertions-core:4.6.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    schemaPaths = mutableListOf("${projectDir}/src/main/resources/schema/schema.graphqls")
    generateDataTypes = true
    snakeCaseConstantNames = true
    language = "kotlin"
    generateKotlinNullableClasses = false
    typeMapping = mutableMapOf(
        "DateTime" to "java.time.OffsetDateTime",
        "Long" to "kotlin.Long",
    )
}
