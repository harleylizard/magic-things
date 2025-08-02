plugins {
    kotlin("jvm") version "2.2.0"
    id("fabric-loom") version "1.11-SNAPSHOT"
}

group = "com.harleylizard"
version = "1.0-SNAPSHOT"

loom {
    accessWidenerPath.set(file("src/main/resources/magic-things.accesswidener"))
}

repositories {
    mavenCentral()
    maven("https://cursemaven.com")
    maven("https://maven.minecraftforge.net/")
    maven("https://maven.ladysnake.org/releases")
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.16.14")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.116.4+1.21.1")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.13.4+kotlin.2.2.0")

    modRuntimeOnly("curse.maven:biomes-o-plenty-220318:6631073")

    modImplementation("com.github.glitchfiend:GlitchCore-fabric:1.21.1-2.1.0.0") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    modImplementation("com.github.glitchfiend:TerraBlender-fabric:1.21.1-4.1.0.8") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-base:6.1.2")
    modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-entity:6.1.2")
    modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-chunk:6.1.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}