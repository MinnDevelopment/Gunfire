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

/**
 * Extension for a [Gun] that will fire the [Bullet] which is constructed by the specified builder.
 *
 * This is a convenience over the [Gun.fireBullet] function as it automatically collects registered targets!
 *
 * @param[builder] Constructs a [Bullet] that should be fired at targets! This is built once before iterating the targets!
 * @param[T] The bullet type that is constructed. This is usually inferred.
 *
 * @return A [List] containing all targets that were hit
 *
 * @receiver[Gun]
 */
@Suppress("UNCHECKED_CAST")
inline infix fun <reified T : Bullet> Gun.fire(builder: () -> T): List<(T) -> Unit> {

    val bullet = builder()
    val targetType = T::class.java
    val birdseye = targets
            .asSequence()
            .filter { it.type.isAssignableFrom(targetType) }
            .map { it as (Bullet) -> Unit }
            .toList()

    fireBullet(bullet, birdseye)
    return birdseye
}

/**
 * This is a convenience over [Gun.registerTarget] which automatically pulls the type from the generic type provided.
 *
 * The [callback] will automatically be called whenever a bullet for this type hits!
 *
 * @param[callback] The callback that will execute when this bullet is fired
 * @param[T] The bullet type
 *
 * @receiver[Gun]
 */
@Suppress("UNCHECKED_CAST")
inline infix fun <reified T : Bullet> Gun.target(noinline callback: (T) -> Unit) {
    registerTarget(T::class.java, callback)
}
