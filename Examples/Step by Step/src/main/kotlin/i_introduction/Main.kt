/*
 *     Copyright 2016 - 2017 Florian Spieß
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package i_introduction

import club.minnced.gunfire.core.Bullet
import club.minnced.gunfire.core.Gun
import club.minnced.gunfire.core.fire
import club.minnced.gunfire.core.impl.Revolver
import club.minnced.gunfire.core.impl.Sniper
import club.minnced.gunfire.core.impl.Uzi
import club.minnced.gunfire.core.target
import kotlinx.coroutines.experimental.newSingleThreadContext

/*
-- Welcome to Project Gunfire --

We will begin by deciding on which gun to use.
Our choices are Gun, Sniper, Revolver and Uzi!

=> Gun
This one is just really simple and has no extensions,
we can use this for testing and single threaded applications.

=> Sniper
This Sniper synchronizes each shot with an internal lock.
We can use this for thread-safety when needed.

=> Revolver
When we want to have parallel bullet shots we can this as it provides up to
6 concurrent worker threads with an Executor.

=> Uzi
This makes use of the coroutines experiment that kotlin 1.1.1 introduced.
It is much more lightweight compared to threads but is executed in sequence, this means
bullets will fire in sequence but the target execution will not block the gun fire thread.

*/

fun main(args: Array<String>) {

    println("-- Starting with Gun --")
    var gun: Gun = Gun()


    // Catch every message and print it as a line to System.out
    gun.target<MessageBullet>(::println)

    // Fire the message bullet
    gun.fire { MessageBullet("This was fired by a Gun!") }


    println("-- Switching to Sniper --")
    gun = Sniper()

    gun.target<MessageBullet> {
        println(it.message)
        gun.fire { BlankBullet }
    }

    // When getting a BlankBullet we end transmission!
    gun.target<BlankBullet> { println("End of transmission!") }

    gun.fire {
        MessageBullet("This is fired with precision!")
    }


    println("-- Switching to Revolver --")
    gun = Revolver()

    gun.target<Bullet> { println("I was shot!!") }

    // Fire 10 blanks
    for (i in 0..10) gun.fire { BlankBullet }


    println("-- Switching to Uzi --")
    gun = Uzi(newSingleThreadContext("Uzi-Worker"))

    gun.target<CountingBullet> { println("Count: ${it.count}") }

    for (i in 0..10) gun.fire { CountingBullet(i) }

    // -- Waiting for Completion --
    Thread.sleep(10000)
}

