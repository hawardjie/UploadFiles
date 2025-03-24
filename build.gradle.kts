plugins {
    id("java")
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.postsea"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starter Web for building REST APIs
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Spring Boot Starter Data JPA for database access
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Include Hibernate dependency
    // OLD: implementation("javax.transaction:javax.transaction-api:1.3")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

    // MySQL Driver for connecting to MySQL Database
    // OLD: runtimeOnly("mysql:mysql-connector-java:8.0.33")
    runtimeOnly("com.mysql:mysql-connector-j:9.2.0")

    // Spring Boot Starter Test for testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Lombok (Optional: for reducing boilerplate code, e.g., getters/setters)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}