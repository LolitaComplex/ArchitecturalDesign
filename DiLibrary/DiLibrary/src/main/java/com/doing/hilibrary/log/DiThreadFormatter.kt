package com.doing.hilibrary.log

class DiThreadFormatter : DiLogFormatter<Thread> {

    override fun format(thread: Thread): String {
        return "Thread: ${thread.name}"
    }
}