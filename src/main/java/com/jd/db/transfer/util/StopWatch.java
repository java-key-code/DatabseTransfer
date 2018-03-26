package com.jd.db.transfer.util;

import java.util.concurrent.TimeUnit;

/**
 * 功能：进行时间检测
 * 
 * @author miaojundong
 *
 */
public class StopWatch {
	private static enum State {
		NEW, STOPPED, RUNNING
	};

	private State state = State.NEW;
	long miliTimeStart;
	long miliTimeStop;

	/**
	 * Create a new Stopwatch, and start it. You may restart the watch by
	 * calling {@code start()}
	 */
	public StopWatch() {
		start();
	}

	/**
	 * Start or restart stopwatch
	 */
	public synchronized long start() {
		state = State.RUNNING;
		miliTimeStart = System.currentTimeMillis();
		return miliTimeStart;
	}

	public long getMiliTimeStart() {
		return miliTimeStart;
	}

	public long getMiliTimeStop() {
		return miliTimeStop;
	}

	/**
	 * Stop (or re-stop) stopwatch
	 * 
	 * @return
	 */
	public synchronized long stop() {
		if (state != State.STOPPED) {
			miliTimeStop = System.currentTimeMillis();
			state = State.STOPPED;
		}
		return miliTimeStop;
	}

	/**
	 * @return the time, in milliseconds that has elapsed so far.
	 *         <p>
	 *         Calling this method is identical to calling:
	 *         {@code elapsedMillis(TimeUnit.MILLISECONDS);}
	 */
	public synchronized long elapsedMillis() {
		return elapsed(TimeUnit.MILLISECONDS);
	}

	/**
	 * @param unit
	 *            The TimeUnit that should be reported
	 * @return the time, in milliseconds that has elapsed so far.
	 *         <p>
	 *         Calling this method is identical to calling:
	 *         {@code elapsedMillis(TimeUnit.MILLISECONDS);}
	 */
	public synchronized long elapsed(TimeUnit unit) {
		final long elapsed;
		switch (state) {
		case RUNNING:
			elapsed = System.nanoTime() - miliTimeStart;
			break;

		case STOPPED:
			elapsed = miliTimeStop - miliTimeStart;
			break;

		default:
			throw new IllegalStateException("State: " + state.toString());
		}
		if (unit == null) {
			throw new NullPointerException("unit");
		}

		return unit.convert(elapsed, TimeUnit.MILLISECONDS);
	}

	public boolean isRunning() {
		return state == State.RUNNING;
	}

	@Override
	public String toString() {
		return "StopWatch " + state + " @ " + elapsed(TimeUnit.MILLISECONDS)
				+ "ms";
	}
}
