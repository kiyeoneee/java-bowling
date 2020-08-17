package bowling.domian.frame;

import bowling.domian.state.State;

import java.util.Objects;

public class FrameResult {
    private State state;
    private Score score;
    private int totalScore;

    private FrameResult(State state) {
        this.state = state;
        this.totalScore = -1;

        if (state.canGetScore()) {
            score = state.getScore();
        }
    }

    private FrameResult(State state, int totalScore) {
        this.state = state;
        this.totalScore = totalScore;

        if (state.canGetScore()) {
            score = state.getScore();
        }
    }

    public static FrameResult get(State state) {
        return new FrameResult(state);
    }

    public static FrameResult get(State state, int totalScore) {
        return new FrameResult(state, totalScore);
    }

    public boolean isTotalCalculated() {
        return this.totalScore > 0;
    }

    public boolean canCalculateScore() {
        return state.canGetScore();
    }

    public boolean  isCalculateDone() {
        if (!state.canGetScore()) {
            return false;
        }

        if (Objects.isNull(score)) {
            return false;
        }

        return this.score.isCalculateDone();
    }

    public void calculateAdditional(FrameResult lastFrameResult) {
        Score additionalScore = this.state.calculateAdditional(lastFrameResult.score);

        if (this.state.isFinished() || additionalScore.isCalculateDone()) {
            lastFrameResult.score = additionalScore;
        }
    }

    public int getTotalScore() {
        return this.totalScore + score.getScore();
    }

    public void addLastTotalScore(FrameResult lastFrameResult) {
        this.totalScore = lastFrameResult.getTotalScore();
    }
}
