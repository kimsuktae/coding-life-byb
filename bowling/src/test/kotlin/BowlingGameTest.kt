import junit.framework.TestCase.assertEquals
import org.junit.Test

class BowlingGameTest {
    private lateinit var game : BowlingGame

    private fun setup() {
        game = BowlingGame()
    }

    @Test
    fun whenThrowOneBall() {
        setup()

        game.throwBall(1)

        val score = game.score()

        assertEquals(score, 1)
    }

    @Test
    fun whenThrowTwoBalls() {
        setup()

        game.throwBall(1)
        game.throwBall(2)

        val score = game.score()

        assertEquals(3, score)
    }

    @Test
    fun whenThrowThreeBalls() {
        setup()

        game.throwBall(1)
        game.throwBall(2)
        game.throwBall(2)

        val score = game.score()

        assertEquals(5, score)
    }

    @Test
    fun whenThrowSixBalls() {
        setup()

        game.throwBall(1)
        game.throwBall(2)
        game.throwBall(2)
        game.throwBall(2)
        game.throwBall(2)
        game.throwBall(2)

        val score = game.score()

        assertEquals(11, score)
    }

    @Test
    fun whenThrowSpareAndOne() {
        setup()

        game.throwBall(1)
        game.throwBall(9)
        game.throwBall(1)

        val score = game.score()

        assertEquals(12, score)
    }

    @Test
    fun whenThrowSpareAndOneAndOne() {
        setup()

        game.throwBall(1)
        game.throwBall(9)
        game.throwBall(1)
        game.throwBall(1)

        val score = game.score()

        assertEquals(13, score)
    }

    @Test
    fun whenThrowStrikeAndOne() {
        setup()

        game.throwBall(10)
        game.throwBall(1)

        val score = game.score()

        assertEquals(12, score)
    }

    @Test
    fun whenThrowsStrikeAndOneAndOne() {
        setup()

        game.throwBall(10)
        game.throwBall(1)
        game.throwBall(1)

        val score = game.score()

        assertEquals(14, score)
    }

    @Test
    fun whenThrowTwoStrikes() {
        setup()

        game.throwBall(10)
        game.throwBall(10)

        val score = game.score()

        assertEquals(30, score)
    }

    @Test
    fun whenThrowThreeStrikes() {
        setup()

        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)

        val score = game.score()

        assertEquals(60, score)
    }

    @Test
    fun whenThrowRandomly() {
        setup()

        game.throwBall(9)
        game.throwBall(1)
        game.throwBall(9)
        game.throwBall(0)
        game.throwBall(8)
        game.throwBall(0)
        game.throwBall(9)
        game.throwBall(1)
        game.throwBall(7)
        game.throwBall(1)
        game.throwBall(9)
        game.throwBall(0)
        game.throwBall(4)
        game.throwBall(3)
        game.throwBall(7)
        game.throwBall(2)
        game.throwBall(1)
        game.throwBall(1)
        game.throwBall(2)
        game.throwBall(5)

        val score = game.score()

        assertEquals(95, score)
    }

    @Test
    fun whenThrowStrikeTenTimes() {
        setup()

        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)

        val score = game.score()

        assertEquals(300, score)
    }
    @Test
    fun whenThrowSpareAtLastFrame() {
        setup()

        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(10)
        game.throwBall(9)
        game.throwBall(1)
        game.throwBall(10)

        val score = game.score()

        assertEquals(279, score)
    }
}
