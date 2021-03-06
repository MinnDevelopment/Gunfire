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

package club.minnced.gunfire.core.impl.bullets

import club.minnced.gunfire.core.Bullet

/**
 * Fired Whenever a Throwable is caught by a Gun!
 *
 * @param[bullet] The causing bullet
 * @param[target] The target which threw the exception
 * @param[error] The [Throwable] that was caught by the Gun
 */
class Backfire internal constructor(
    override val bullet: Bullet,
    val target: (Bullet) -> Unit,
    val error: Throwable
) : BulletShell<Bullet>
