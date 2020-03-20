package com.bian.viewapplication.util;

import android.opengl.GLSurfaceView;
import android.os.SystemClock;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadControl {

    private Object lock = new Object();
    private Thread viewThread;

    public volatile boolean isPause;
    public volatile boolean isExit;

    private GLSurfaceView glSurfaceView;

    public void start() {
        isPause = false;
        isExit = true;
        viewThread = new Thread(new MyRunnable());
        viewThread.start();
    }

    public void exit() {
        synchronized (lock) {
            isExit = true;
            notifyAll();
        }
    }

    public void pause() {
        synchronized (lock) {
            isPause = true;
            notifyAll();
        }
    }

    public void resume() {
        synchronized (lock) {
            isPause = false;
            notifyAll();
        }
    }

    public void join() {
        try {
            viewThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            if (false) {
                try {
                    runWrapper();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void runWrapper() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                SystemClock.sleep(10);
                CommonLog.i("刘小寒是一个大傻瓜.....");
            }
        }
    }
}
