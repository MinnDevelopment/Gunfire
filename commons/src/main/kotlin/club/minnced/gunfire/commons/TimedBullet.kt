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

package club.minnced.gunfire.commons

import club.minnced.gunfire.core.Bullet
import club.minnced.gunfire.core.Gun
import club.minnced.gunfire.core.fire
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * Can be used to notify something at a relative point in time.
 * This expects an accurate system time and uses [delay] to await completion.
 *
 * **To fire this bullet use [load] instead of [Gun.fire]**
 */
open class TimedBullet(
    val time: Long,
    val timeUnit: TimeUnit = MILLISECONDS) : Bullet {

    /** The time at which this bullet was created */
    val startTime = System.currentTimeMillis()
    /** The relative point in time at which this bullet will fire */
    val shootTime = System.currentTimeMillis() + timeUnit.toMillis(time)

    /** True, if this timed bullet exceeded the specified delay */
    val isReady: Boolean get() = System.currentTimeMillis() >= shootTime
    /** True if this bullet has been fired already */
    var isFinished = false
        private set

    /**
     * Constructs a coroutine which will suspend until the bullet is ready
     * to be fired, this will also fire when [isFinished] is `true`!
     *
     * You can use [kotlinx.coroutines.experimental.Deferred.await] to suspend your thread
     * and await for this bullet to fire!
     */
    fun load(gun: Gun) = async(CommonPool) {

        delay(shootTime - System.currentTimeMillis())
        gun.fire { this@TimedBullet }
        isFinished = true

    }

}
