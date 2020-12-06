package gutizia.tasks;

import org.powerbot.script.Random;

public interface Activatable {

    boolean activate();

    class Activate {
        private long lastCheck;
        private final long COOLDOWN;
        private int chances;
        private int total;

        Activate(int cooldown, int chances, int total) {
            COOLDOWN = cooldown;
            lastCheck = System.currentTimeMillis();
            this.chances = chances;
            this.total = total;
        }

        public boolean activate() {
            if (timeToCheck()) {
                return random();
            }
            return false;
        }

        private boolean random() {
            lastCheck = System.currentTimeMillis();
            return Random.nextInt(0,total + 1) <= chances;
        }

        private boolean timeToCheck() {
            return System.currentTimeMillis() > lastCheck + COOLDOWN;
        }
    }
}
