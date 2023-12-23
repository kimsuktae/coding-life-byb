import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FrameTest {
    @Test
    fun isSpare() {
        val frame = Frame()

        frame.addScore(1)
        frame.addScore(9)

        assertTrue(frame.isSpare())
    }

    @Test
    fun isStrike() {
        val frame = Frame()

        frame.addScore(10)

        assertTrue(frame.isStrike())
    }
}
