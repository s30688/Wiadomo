plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "app.wiadomo"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Obsługa modemu przez port szeregowy (AT Commands)
    implementation("com.fazecast:jSerialComm:2.9.3")

    // Korutyny – scheduler, pobieranie co 5 minut itd.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Logowanie
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // Testy (opcjonalnie, jeśli będziesz pisać testy jednostkowe)
    testImplementation(kotlin("test"))
}

application {
    // To musi odpowiadać Twojemu pakietowi i nazwie klasy main
    mainClass.set("app.MainKt")
}

kotlin {
    jvmToolchain(17) // używasz Corretto 17 – bardzo dobrze!
}
