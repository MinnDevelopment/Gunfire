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

package club.minnced.gunfire

import club.minnced.gunfire.impl.bullets.ErrorBullet
import kotlin.reflect.KClass

open class Gun {

    val targets: MutableMap<KClass<out Bullet>, MutableList<(Bullet) -> Unit>> = hashMapOf()

    open fun registerTarget(type: KClass<out Bullet>, callback: (Bullet) -> Unit) {
        targets.compute(type) { _, list->
            if (list === null) {
                return@compute mutableListOf(callback)
            }
            else {
                list.add(callback)
                return@compute list
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    open fun <T : Bullet> fireBullet(bullet: T, targets: List<(T) -> Unit>) = targets.forEach {
        try {
            it(bullet)
        }
        catch (ex: Throwable) {
            if (bullet !is ErrorBullet)
                fire { ErrorBullet(bullet, it as (Bullet) -> Unit, ex) }
        }
    }

}
