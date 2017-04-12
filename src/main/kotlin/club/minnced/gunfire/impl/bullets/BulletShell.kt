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

package club.minnced.gunfire.impl.bullets

import club.minnced.gunfire.Bullet

/**
 * Wrapper Bullet holding a child bullet of type `E`.
 * This can be fired by any Gun!
 *
 * A sample implementation is the [ErrorBullet] which holds a [Throwable] and the cause [Bullet]
 *
 * @param[E] The wrapped Bullet type
 */
interface BulletShell<out E : Bullet> : Bullet {

    val bullet: E

}