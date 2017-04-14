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

package club.minnced.gunfire.core

import club.minnced.gunfire.core.impl.bullets.Backfire

/**
 * Generic Gun which executes targets on the calling thread.
 *
 * Known implementations are:
 *
 * - [Sniper][club.minnced.gunfire.impl.Sniper]
 *
 *     Synchronized layer over Gun for thread-safety
 * - [Revolver][club.minnced.gunfire.impl.Revolver]
 *
 *     Using up to 6 worker threads to fire bullets
 * - [Uzi][club.minnced.gunfire.impl.Uzi]
 *
 *     Using kotlin coroutines to fire bullets
 */
open class Gun {

    /** The registered [Targets][TargetWrapper] for this Gun */
    val targets: MutableList<TargetWrapper<*>> = mutableListOf()

    /**
     * Registers the given function as a target for the specified bullet type.
     *
     * **Note**: Use [target] Extension to easily register targets without having to specify type as a parameter
     *
     * @param[type] The type of [Bullet] which this target will be hit by
     * @param[callback] The function which should be handling the bullets for this target
     */
    open fun <T : Bullet> registerTarget(type: Class<T>, callback: (T) -> Unit) {
        targets += TargetWrapper(type, callback)
    }

    /**
     * Fires the specified bullet at all provided target functions.
     *
     * All [Throwable]s caught by this iteration will be handled by shooting a [Backfire] cycle!
     *
     * **Note**: Use [fire] Extension to easily fire bullets to all registered targets of this gun
     */
    @Suppress("UNCHECKED_CAST")
    open fun <T : Bullet> fireBullet(bullet: T, targets: List<(Bullet) -> Unit>) = targets.forEach {
        try {
            it(bullet)
        }
        catch (ex: Throwable) {
            if (bullet !is Backfire)
                fire { Backfire(bullet, it, ex) }
        }
    }

    /**
     * Wrapper used to store Targets for a Gun.
     *
     * This will wrap a [Bullet] type and a handler callback.
     *
     * @param[type] The bullet type to handle
     * @param[target] The callback for this target
     */
    class TargetWrapper<T : Bullet>(val type: Class<T>, val target: (T) -> Unit) : (T) -> Unit {

        /**
         * Called whenever a bullet matching this type is fired.
         *
         * @param[bullet] The bullet that hit this target
         */
        override fun invoke(bullet: T) {
            target(bullet)
        }

    }

}
