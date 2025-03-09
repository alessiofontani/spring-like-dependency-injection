package org.di;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class ContextInitializer {

    private final ComponentInstanceHolder componentInstanceHolder;

    public ContextInitializer(ComponentInstanceHolder componentInstanceHolder) {
        this.componentInstanceHolder = componentInstanceHolder;
    }

    public ComponentInstanceHolder getContextHolder() {
        return componentInstanceHolder;
    }

    public void initializeContext(String packageToScan) throws URISyntaxException {
        List<Class<?>> classes = findClassesInPackage(packageToScan);
        List<Class<?>> classesWithComponentAnnotation = classes.stream().filter(c -> c.isAnnotationPresent(Component.class)).toList();
        for (Class<?> clazz : classesWithComponentAnnotation) {
            initializeClass(clazz, classesWithComponentAnnotation);
        }
    }

    private List<Class<?>> findClassesInPackage(String packageToScan) throws URISyntaxException {
        String path = packageToScan.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(path);
        assert resource != null;
        return findClassesInFolder(resource.toURI(), packageToScan);
    }

    private List<Class<?>> findClassesInFolder(URI uri, String currentPackage) {
        List<Class<?>> classes = new ArrayList<>();
        File currentDir = new File(uri);
        if(currentDir.isDirectory()) {
            File[] files = currentDir.listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClassesInFolder(file.toURI(), currentPackage + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    try {
                        String className = file.getName().substring(0, file.getName().length() - 6);
                        classes.add(Class.forName(currentPackage + "." + className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }

    private void initializeClass(Class<?> clazz, List<Class<?>> classesWithComponentAnnotation) {
        if(!componentInstanceHolder.hasComponent(clazz)) {
            Constructor<?> constructor = retrieveClassConstructor(clazz);

            try {
                Object[] parameters = new Object[constructor.getParameterCount()];
                int i = 0;
                for (Class<?> parameterType : constructor.getParameterTypes()) {
                    if(classesWithComponentAnnotation.contains(parameterType) && !componentInstanceHolder.hasComponent(parameterType)) {
                        initializeClass(parameterType, classesWithComponentAnnotation);
                    } else {
                        throw new RuntimeException("Cannot find eligible bean for " + clazz.getName());
                    }
                    parameters[i] = componentInstanceHolder.getComponent(parameterType);
                    i++;
                }
                Object instance = constructor.newInstance(parameters);
                componentInstanceHolder.putComponent(clazz, instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Constructor<?> retrieveClassConstructor(Class<?> clazz) {
        Constructor<?> constructor = null;

        List<Constructor<?>> publicAutowiredConstructors = Arrays.stream(clazz.getConstructors())
                .filter(c -> c.isAnnotationPresent(Autowired.class))
                .toList();

        if(publicAutowiredConstructors.size() > 1) {
            throw new RuntimeException("Only one constructor with Autowired annotation allowed");
        } else if(publicAutowiredConstructors.size() == 1) {
            constructor = publicAutowiredConstructors.getFirst();
        } else {
            try {
                constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("No public no-arg constructor found for " + clazz.getName() +
                        " and no public constructor with @Autowired annotation");
            }
        }
        return constructor;
    }



}
