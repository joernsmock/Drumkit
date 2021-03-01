import java.io.File
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem

fun playBeats(beats: String, file: String) {
    val parts = beats.split("x")
    println(parts)
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
    println(file)
    clip.open(audioInputStream)
    clip.start()
}

fun main() {
    val tomsString = "x-x-x-x-x-x-"
    println(tomsString)
    val cymbalString = "x-----x-----"
    println(cymbalString)
    playBeats(tomsString, "toms.aiff")
    playBeats(cymbalString, "crash_cymbal.aiff")
}