package org.swordess.toy.kotlin.misc.concurrent

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DelayedTask(val name: String, val executor: ExecutorService, val delay: Long, val task: Runnable) : Runnable {

    private val createdTimeMillis = System.currentTimeMillis()

    override fun run() {
        if (System.currentTimeMillis() - createdTimeMillis < delay) {
            executor.submit(this)
            println("reschedule $name")
        } else {
            task.run()
            println("$name completed")
        }
    }

}

fun main(args: Array<String>) {
    val executor: ExecutorService = Executors.newFixedThreadPool(2)
    for (i in 1..10) {
        executor.submit(DelayedTask("Task $i", executor, 2000, Runnable { println("Task $i doing...") }))
    }
}
