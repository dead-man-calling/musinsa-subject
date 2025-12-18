import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "2.1.0"
	kotlin("plugin.spring") version "2.1.0"
	kotlin("plugin.jpa") version "2.1.0"
	kotlin("kapt") version "2.1.0"
	id("groovy")
}

group = "com.musinsa"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

kotlin {
	jvmToolchain(21)
}

repositories {
	mavenCentral()
}

val springDocVersion = "2.8.14"
val mapstructVersion = "1.6.3"
val spockVersion = "2.4-M5-groovy-4.0"
val queryDslVersion = "5.6.1"
val caffeineVersion = "3.2.0"
val commonsLangVersion = "3.18.0"

dependencies {
	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Documentation
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
	// CVE-2025-48924 수정 (Commons Lang 버전 강제)
	implementation("org.apache.commons:commons-lang3:$commonsLangVersion")

	// MapStruct
	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")

	// QueryDSL (OpenFeign fork - actively maintained)
	implementation("io.github.openfeign.querydsl:querydsl-jpa:$queryDslVersion:jakarta")  // 런타임 라이브러리 (필수!)
	kapt("io.github.openfeign.querydsl:querydsl-apt:$queryDslVersion:jakarta")            // Q 클래스 생성
	kapt("jakarta.annotation:jakarta.annotation-api")
	kapt("jakarta.persistence:jakarta.persistence-api")

	// Cache
	implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")

	// Database
	runtimeOnly("com.h2database:h2")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.spockframework:spock-core:$spockVersion")
	testImplementation("org.spockframework:spock-spring:$spockVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// JPA plugin configuration
allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

noArg {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

// kapt configuration
kapt {
	arguments {
		arg("mapstruct.defaultComponentModel", "spring")
		arg("mapstruct.unmappedTargetPolicy", "IGNORE")
	}
	keepJavacAnnotationProcessors = true
}

tasks.withType<KotlinCompile> {
	compilerOptions {
		freeCompilerArgs.set(
            listOf(
                "-Xjsr305=strict",
                "-Xjvm-default=all"
            )
        )
        jvmTarget.set(JvmTarget.JVM_21)
	}
}

tasks.named<Test>("test") {
	useJUnitPlatform()
}
