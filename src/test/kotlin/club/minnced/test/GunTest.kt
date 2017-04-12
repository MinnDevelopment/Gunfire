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

package club.minnced.test

import club.minnced.gunfire.Bullet
import club.minnced.gunfire.fire
import club.minnced.gunfire.impl.Uzi
import club.minnced.gunfire.impl.bullets.ErrorBullet
import club.minnced.gunfire.target
import org.junit.Before
import org.junit.Test
import org.junit.BeforeClass as Setup

typealias static = JvmStatic

class GunTest {

    private val gun = Uzi()

    @Before
    fun registerTargets() {
        System.err.println("Registering Targets...")
        gun.target<TestBullet> {
            println("First Event Arrived!")
        }

        gun.target<Bullet> {
            println("Second Event Arrived! Why??")
        }

        gun.target<ErrorBullet> {
            println("Caught an Error?!?! Message: ${it.error.message} - Bullet: ${it.bullet} - Target: ${it.target}")
        }

        gun.target<TestBullet> {
            println("OH NO!! GUN IS LOSE!!")
            throw Exception("SHOOT!!")
        }
    }

    @Test
    fun fireBullets() {
        System.err.println("FIRE!!!!")
        gun.fire { TestBullet }
        Thread.sleep(2000)
    }

    object TestBullet : Bullet

}
