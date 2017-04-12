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
@file:JvmName("Main")
package club.minnced.backlog

import club.minnced.backlog.err.ExceptionEvent
import club.minnced.backlog.err.IOExceptionEvent
import club.minnced.backlog.out.PrintEvent
import club.minnced.backlog.out.printEvent
import club.minnced.backlog.out.printfEvent
import club.minnced.backlog.out.printlnEvent
import club.minnced.gunfire.fire
import club.minnced.gunfire.target
import java.io.IOException
import java.util.Scanner

fun main(args: Array<String>) {
    LOGGER.target<PrintEvent> { it.out.print(it.message) }
    LOGGER.target<ExceptionEvent> {
        printlnEvent("Something didn't go as planned ${it.message}")
        it.cause.printStackTrace()
    }

    val sc = Scanner(System.`in`)
    var n: Int = -1
    try {
        while (n != 0) {
            printEvent("Enter Number: ")
            n = sc.nextInt()
            printfEvent("Your Number is %seven!", if (n % 2 == 0) "" else "not ")
            printlnEvent()
        }
    }
    catch (ex: Exception) {
        if (ex is IOException) LOGGER.fire {
            IOExceptionEvent(ex, ":(")
        }
        else LOGGER.fire {
            ExceptionEvent(ex, ":(")
        }
    }
}
