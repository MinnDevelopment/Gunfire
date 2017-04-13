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

package club.minnced.gunfire.impl

import club.minnced.gunfire.Bullet
import club.minnced.gunfire.Gun
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Uses coroutines to fire events!
 *
 * @param[context] The [CoroutineContext] which should be provided to [launch]! Default [CommonPool]
 *
 * @sample[fireBullet]
 */
class Uzi(val context: CoroutineContext = CommonPool) : Gun() {

    override fun <T : Bullet> fireBullet(bullet: T, targets: List<(Bullet) -> Unit>) {
        launch(context) {
            super.fireBullet(bullet, targets)
        }
    }
}
