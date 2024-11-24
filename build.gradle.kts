plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.4.30"
}

group = "com.mercadolivro"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.flywaydb:flyway-core:9.0.0")
	implementation("org.flywaydb:flyway-mysql:8.4.4")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation ("javax.servlet:javax.servlet-api:4.0.1")
	//implementation("org.springdoc:springdoc-openapi-ui:1.8.0")
	//implementation("org.springframework.boot:spring-boot-starter-parent:3.3.5")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.6.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	runtimeOnly("mysql:mysql-connector-java:8.0.25")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
