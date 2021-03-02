/* Here HFKotlin uses GlobalScope.launch to create thread. */
import java.io.File
import javax.sound.sampled.AudioSystem
import kotlinx.coroutines.*

fun playBeats(beats: String, file: String) {
    val parts = beats.split("x")
    var count = 0
    for (part in parts) {
        count += part.length + 1
        if (part == "") {
            playSound(file)
        }
        else {
            Thread.sleep(100 * (part.length + 1L))
            if (count < beats.length) {
                playSound(file)
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

fun main() {
    val userDir = System.getProperty("user.dir")
    println("user.dir: $userDir")

    GlobalScope.launch {
    playBeats("x--x--x--x--x--x-", "toms.aiff")
    }
    playBeats("x--------x-------", "crash_cymbal.aiff")
}