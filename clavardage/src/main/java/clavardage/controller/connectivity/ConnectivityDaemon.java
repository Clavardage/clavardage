package clavardage.controller.connectivity;

/**
 * Handle the network connectivity activities
 */
public class ConnectivityDaemon {

    private static final Thread daemon;
    private static boolean kill;

    static {
        kill = false;
        daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(daemon) {
                    while(keepDaemonAlive()) {
                        // TODO: handle network connectivity with other threads
                        // ----------------------------------------------------

                        /* WAIT UNTIL A COMPONENT NOTIFIES THE DAEMON */
                        try {
                            daemon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private static boolean keepDaemonAlive() {
        return !kill;
    }

    public static void stop() {
        kill = true;
        notifyThread();
    }

    public static void start() {
        daemon.start();
    }

    public static void notifyThread() {
        synchronized (daemon) {
            daemon.notify();
        }
    }
}
