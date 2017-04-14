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
import java.util.concurrent.BlockingQueue
import kotlin.concurrent.thread

/**
 * Creates a worker thread which works through all bullets that are fired.
 *
 * Every bullet passed to [fireBullet] is pulled into the buffer and fired in sequence with all others
 * in the respective worker thread!
 */
open class BufferGun(private val queue: BlockingQueue<BulletInstance<*>>) : Gun() {

    val worker: Thread by lazy {
        thread(isDaemon = true, name = "BufferGun-Worker") {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    val bullet = queue.take()
                    onBufferPoll(bullet.bullet, bullet.targets)
                }
                catch (ex: InterruptedException) {
                    break
                }
            }
        }
    }

    /**
     * Equivalent of [fireBullet] but for buffer drain instead.
     *
     * This is done so that all fired bullets are first pulled into a buffer.
     */
    fun <T : Bullet> onBufferPoll(bullet: T, targets: List<(Bullet) -> Unit>) {
        super.fireBullet(bullet, targets)
    }

    override final fun <T : Bullet> fireBullet(bullet: T, targets: List<(Bullet) -> Unit>) {
        queue += BulletInstance(bullet, targets)
    }

    /**
     * Wrapper for [fireBullet] input.
     *
     * This is the buffer element type!
     */
    class BulletInstance<out T : Bullet>(val bullet: T, val targets: List<(Bullet) -> Unit>)
}
