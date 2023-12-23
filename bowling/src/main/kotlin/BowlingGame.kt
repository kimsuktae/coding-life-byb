import java.util.Scanner

class BowlingGame(
    private val isPlaying: Boolean = true,
    private var frame: Frame = Frame(),
) {

    fun play() {
        while (isPlaying) {
            val scanner = Scanner(System.`in`)
            println("공을 굴려주세요")

            val score = scanner.next()
        }
    }

    fun throwBall(ball: Int) {
        frame = frame.addScore(ball)
    }

    fun score(): Int {
        return frame.score()
    }
}

open class Frame(
    var previousFame: Frame? = null,
    private var nextFrame: Frame? = null,
    private var current: Int = 1,
    var throws: Int = 0,
    var first: Int = 0,
    var second: Int = 0,
    private var bonus: Int = 0,
) {
    open val total: Int
        get() {
            return first + second + bonus
        }

    fun next(): Frame {
        if (current == 9) {
            return FinalFrame(previousFame = this, current = current + 1)
        }

        return Frame(previousFame = this, current = current + 1)
    }

    open fun addScore(ball: Int): Frame {
        if (ball == 10) {
            first = ball

            return next()
        }

        if (throws == 0) {
            first = ball
            throws += 1

            return this
        }

        second = ball
        throws += 1

        return next()
    }

    open fun score(): Int {
        var score = 0
        var current = this as Frame?

        while (current != null) {
            current.calculateBonus()

            score += current.total

            val next = current

            current = current.previousFame
            current?.nextFrame = next
        }

        return score
    }

    open fun calculateBonus() {
        if (this.isSpare() || this.isStrike()) {
            this.nextFrame?.addPreviousFrameBonus()
        }
    }

    open fun addPreviousFrameBonus() {
        if (this.previousFame?.isSpare()!!) {
            this.previousFame?.addBonus(this.first)

            return
        }

        if (this.previousFame?.isStrike()!!) {
            if (this.isStrike()) {
                this.previousFame?.addBonus(this.first + this.nextFrame?.first!!)

                return
            }

            this.previousFame?.addBonus(this.first + this.second)
        }
    }

    fun addBonus(bonus: Int) {
        this.bonus = bonus
    }

    fun isStrike(): Boolean {
        return first == 10
    }

    fun isSpare(): Boolean {
        return first != 10 && first + second == 10
    }

    override fun toString(): String {
        val score = mutableListOf<Int>()

        var current = this as Frame?

        while (current != null) {
            score.add(current.total)
            current = current.previousFame
        }

        return score.toString()
    }
}

class FinalFrame(
    previousFame: Frame?,
    current: Int,
) : Frame(
    previousFame = previousFame,
    current = current,
) {
    private var third: Int = 0

    override val total: Int
        get() {
            return first + second + third
        }

    override fun addScore(ball: Int): Frame {
        if (throws == 0) {
            first = ball
            throws += 1

            return this
        }

        if (throws == 1) {
            second = ball
            throws += 1

            return this
        }

        if (throws == 2) {
            third = ball
        }

        return this
    }

    override fun calculateBonus() {
        return
    }

    override fun addPreviousFrameBonus() {
        this.previousFame?.addBonus(this.first + this.second)
    }
}
