package org.di;

public class DependencyInjectionApp {

    public static ComponentInstanceHolder run(String packageToScan) {
        try {
            ContextInitializer contextInitializer = new ContextInitializer(new ComponentInstanceHolder());
            contextInitializer.initializeContext(packageToScan);
            return contextInitializer.getContextHolder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
