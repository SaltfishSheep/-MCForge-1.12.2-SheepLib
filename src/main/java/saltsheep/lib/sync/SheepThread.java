package saltsheep.lib.sync;

public abstract class SheepThread extends Thread {

    private boolean routine = true;
    //private Integer routineCount = 0;
    private Object running = new Object();
    private Object freeze = new Object();
    private Object freezeData = new Object();
    /**
     * The only time it will turn true to false, is the thread unfreeze or end.
     */
    private boolean needFreeze = false;
    //private boolean willFreeze = false;
    /**
     * The only time it will turn true to false, is the thread end.
     */
    private boolean autoFreeze = false;

    public SheepThread(boolean autoFreeze) {
        this.autoFreeze = autoFreeze;
    }

    @Override
    public final void run() {
        while (routine) {
            synchronized (running) {
                this.runIn();
            }
            if (this.isInterrupted())
                return;
            synchronized (freeze) {
                if (willFreeze()) {
                    try {
                        freeze.wait();
                        synchronized (freezeData) {
                            needFreeze = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (this.isInterrupted())
                return;
        }
    }

    public final void waitRoutineEnd() {
        synchronized (running) {
        }
    }

    public final void waitFreezeStart() {
        while (this.getState() != State.WAITING) {
            try {
                Thread.sleep(0, 1);//*尝试等待1纳秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public final boolean willFreeze() {
        synchronized (freezeData) {
            return autoFreeze || needFreeze;
        }
    }

    /**
     * It is no useful in many times, you can use autoFreeze instead of it, because you don't know when will it invoke.
     */
    public final void freeze() {
        synchronized (freezeData) {
            needFreeze = true;
        }
    }

    /**
     * It sounds to strange, if you want to unfreeze the thread, you must wait until it is freezing.
     * Yes it is!This is to avoid thread rick.
     */
    public final void unfreeze() {
        if (willFreeze())
            waitFreezeStart();
        synchronized (freeze) {
            freeze.notify();
        }
    }

    /**
     * Stop the next routine, and unfreeze the thread, make it dead naturally.
     */
    public final void end() {
        this.routine = false;
        unfreeze();
        synchronized (freezeData) {
            this.autoFreeze = false;
            this.needFreeze = false;
        }
    }

    protected abstract void runIn();

}
