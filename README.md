# Project Gunfire

Project Gunfire is a lightweight event bus framework which is built in and for the Kotlin Programming Language.
<br>This can be used to fire `bullets` with `guns`!

## Requirements

- Kotlin/JVM 1.1.1+ (including coroutines)
- Java Runtime for Java 8

### Dependencies

- Kotlinx-Coroutines for Kotlin 1.1.1
- Kotlin-Reflect 1.1.1 (included as transitive)

Recommended IDE: Intellij IDEA 2017.1

## Getting Started

To create an EventBus for your project you have to choose your **Gun**:

- Gun
    <br>The generic `Gun` which executes on the calling thread
- Sniper
    <br>Extension of a simple `Gun` which synchronizes each bullet shot
- Revolver
    <br>Extension of a simple `Gun` which can fire up to **6** bullets at once
- Uzi
    <br>Fully automatic `Gun` which uses kotlin coroutines for each bullet shot

Every Gun implementation inherits from the `Gun` class and iterates over all registered `targets`.

### Setup Target

```kotlin

import club.minnced.gunfire.Gun
import club.minnced.gunfire.impl.Sniper
import club.minnced.gunfire.target

val gun: Gun = Sniper()

fun main(args: Array<String>) {
    gun.target<Backfire> {
        it.error.printStackTrace()
    }
    gun.target<LogBullet> {
        println(it.message)
    }
}
```

> Note: Here we register a target that will print every **Throwable** that is caught by the Gun!<br>
> Using the `Backfire` bullet specification!

### Fire Gun

```kotlin
import club.minnced.gunfire.Gun
import club.minnced.gunfire.impl.Sniper
import club.minnced.gunfire.fire

class LogBullet(val message: String)

fun logMessage(message: String) {
    gun.fire {
        LogBullet(message)
    }
}
```

> Note: Here we fire a LogBullet which holds a message for the targets!

## Setup

JitPack Version: [ ![version](https://jitpack.io/v/MinnDevelopment/Gunfire.svg) ](https://jitpack.io/#MinnDevelopment/Gunfire)

### Gradle

```gradle
respositories {
    jcenter()
    maven {
        name 'jitpack.io'
        url 'https://jitpack.io'
    }
    // ...
}

dependencies {
    compile 'org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.1'
    compile 'org.jetbrains.kotlinx:kotlinx-coroutines-core:0.14'
    compile 'com.github.MinnDevelopment:gunfire:master-SNAPSHOT'
    // ...
}
```

### Maven

```xml
<repository>
    <url>https://jitpack.io</url>
</repository>
<!-- 
Add other repositories for Kotlin 1.1.1 and Kotlinx-Coroutines
This may require jcenter as repository listing!
-->
<dependency>
    <groupid>com.github.MinnDevelopment</groupid>
    <artifactId>gunfire</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```
