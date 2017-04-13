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

package ii_hierarchy

import club.minnced.gunfire.fire
import club.minnced.gunfire.impl.Sniper
import club.minnced.gunfire.target

/*
-- Hierarchy --

In this section we will look into hierarchy.
For this we use the example of a Smurf abstraction:

-> Smurf
|__PapaSmurf
|__Smurfette
\__HeftySmurf

 */


fun main(args: Array<String>) {

    val gun = Sniper()

    gun.target<Smurf> {
        println("${it.name} appeared!")
    }

    gun.target<PapaSmurf> {
        println("=> Papa Smurf is important! Mentioning him twice! <=")
    }

    // This will hit two targets
    gun.fire { PapaSmurf }


    // Not so important smurfs
    gun.fire { Smurfette }

    gun.fire { Smurf("Jokey Smurf") }

}

