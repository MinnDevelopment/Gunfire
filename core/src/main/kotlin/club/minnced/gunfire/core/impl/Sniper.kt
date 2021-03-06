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

package club.minnced.gunfire.core.impl

import club.minnced.gunfire.core.Bullet
import club.minnced.gunfire.core.Gun

/**
 * Runs a synchronized event cycle at once!
 *
 * @sample [fireBullet]
 */
class Sniper : Gun() {
    /**
     * Synchronization lock for this Sniper
     */
    val lock = Any()

    override fun <T : Bullet> fireBullet(bullet: T, targets: List<(Bullet) -> Unit>) {
        synchronized(lock) { super.fireBullet(bullet, targets) }
    }
}
