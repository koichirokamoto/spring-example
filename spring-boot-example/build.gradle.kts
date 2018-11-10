import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  val kotlinVersion = "1.3.0"
  application
  java
  kotlin("jvm") version kotlinVersion
  id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
  id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
  id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
  id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
  id("org.springframework.boot") version "2.1.0.RELEASE"
  id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

group = "koichirokamoto"
version = "1.0-SNAPSHOT"

val kotlinVersion by extra("1.3.0")
val springbootVersion by extra("2.1.0.RELEASE")

repositories {
  mavenCentral()
}

dependencies {
  compile(kotlin("stdlib-jdk8"))
  compile("org.jetbrains.kotlin", "kotlin-reflect")

  /** spring */
  compile("org.springframework.boot", "spring-boot-starter-data-jpa", springbootVersion)
  compile("org.springframework.boot", "spring-boot-starter-web", springbootVersion)
  compile("org.springframework.boot", "spring-boot-starter-mustache", springbootVersion)

  /** autolink */
  compile("com.atlassian.commonmark", "commonmark", "0.11.0")
  compile("com.atlassian.commonmark", "commonmark-ext-autolink", "0.11.0")

  /** h2 database */
  compile("com.h2database", "h2", "1.4.197")

  /** test */
  testCompile("org.springframework.boot", "spring-boot-starter-test", springbootVersion) {
    exclude("junit")
  }
  testImplementation("org.junit.jupiter", "junit-jupiter-api")
  testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine")

  testCompile("com.nhaarman", "mockito-kotlin", "1.5.0")
}

configure<JavaPluginConvention> {
  sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
  kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}

tasks.withType<JavaExec> {
  application {
    mainClassName = "blog.BlogApplicationKt"
  }
}