/* Here HFKotlin tries to explain
 * - globalScope.launch vs. launch
 * - threads vs. coroutines
 * - delay suspends only this coroutine
 * - ways to start things and let them pause
 *
 * ...but do I get it???
 */

import java.io.File
import javax.sound.sampled.AudioSystem
import kotlinx.coroutines.*

// keyword for the compiler: this can be suspended
suspend fun playBeats(beats: String, file: String) {
    val parts = beats.split("x")
    println(parts)
    // the sound file is played when there is no pause ("") => sound
    // or at the end of a pause "-" => pause, sound
    var count = 0
    for (part in parts) {
        count += part.length + 1
        if (part == "") {
            playSound(file)
        } else {
            // "delay" only suspends coroutine, not the whole thread
            delay(100 * (part.length + 1L))
            //Thread.sleep(100 * (part.length + 1L))
            if (count < beats.length) {
                playSound(file)
                // so it plays the file anyway???
            }
        }
    }
}

fun playSound(file: String) {
    val clip = AudioSystem.getClip()
    val audioInputStream = AudioSystem.getAudioInputStream(
        File(
            file
        )
    )
    clip.open(audioInputStream)
    clip.start()
}

// main has to have this keyword so that it may call playBeats(), which
// in turn is suspendable and will be paused when it calls sleep()
suspend fun main() {
    // val userDir = System.getProperty("user.dir")
    // println("user.dir: $userDir")

    runBlocking {
        // here, the coroutine runs in the same thread, not in
        // a separate thread (as with "globalScope.launch")
        // coroutines in the same thread cannot run simultaneously
        // (HFKotlin p406)
        // [isn't that the sense of coroutines in the first place?]
        launch { playBeats("x---x-x-x----x", "toms.aiff") }
        //playBeats("x-----x-----", "crash_cymbal.aiff")
    }
}