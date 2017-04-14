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
import kotlinx.coroutines.experimental.asCoroutineDispatcher
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.concurrent.thread

/**
 * Static Gun which uses coroutines from a cached thread pool.
 */
object AttachedMachineRifle : Gun() {

    /**
     * The cached thread pool that is used by this object to fire bullets
     *
     * Thread created by this pool are named `Bullet-Time` and are daemon!
     */
    val executor: Executor by lazy {
        Executors.newCachedThreadPool {
            thread(start = false, name = "Bullet-Time", isDaemon = true) { it.run() }
        }
    }

    override fun <T : Bullet> fireBullet(bullet: T, targets: List<(Bullet) -> Unit>) {
        launch(executor.asCoroutineDispatcher()) {
            super.fireBullet(bullet, targets)
        }
    }
}
