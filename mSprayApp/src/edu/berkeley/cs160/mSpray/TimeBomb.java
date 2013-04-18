package edu.berkeley.cs160.mSpray;

public class TimeBomb {
    public void ignite() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(6000);
                        explode();

                    }
                } catch (InterruptedException ex) {
                }

            }
        };

        thread.start();
    }

    public void explode() {
        // Override
    }
}