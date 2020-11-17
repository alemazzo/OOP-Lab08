package it.unibo.oop.lab.advanced;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static final String DEFAULT_CONFIG_PATH = "config.yml";
    private static final int MIN = 0;
    private static final int MAX = 100;
    private static final int ATTEMPTS = 10;
    private final DrawNumber model;
    private final DrawNumberView view;

    /**
     * 
     */

    public DrawNumberApp(final boolean loadConfigFromFile) {
        if (loadConfigFromFile) {
            this.model = this.loadFromConfigFile(DEFAULT_CONFIG_PATH);
        } else {
            this.model = new DrawNumberImpl(MIN, MAX, ATTEMPTS);
        }

        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();
    }

    public DrawNumberApp() {
        this(true);
    }

    private DrawNumberImpl loadFromConfigFile(final String path) {
        final Map<String, String> configurations = new HashMap<>();
        try {

            for (final String conf : Files.readAllLines(Path.of(ClassLoader.getSystemResource(path).getPath()))) {
                final StringTokenizer st = new StringTokenizer(conf, ":");
                final String confName = st.nextToken().trim();
                final String confValue = st.nextToken().trim();
                configurations.put(confName, confValue);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Config file not valid.", e);
        }
        return new DrawNumberImpl(Integer.parseInt(configurations.get("minimum")),
                Integer.parseInt(configurations.get("maximum")), Integer.parseInt(configurations.get("attempts")));
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            this.view.result(result);
        } catch (IllegalArgumentException e) {
            this.view.numberIncorrect();
        } catch (AttemptsLimitReachedException e) {
            view.limitsReached();
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    /**
     * @param args ignored
     */
    public static void main(final String... args) {
        new DrawNumberApp();
    }

}
