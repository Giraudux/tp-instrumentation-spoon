package fr.univ.nantes;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtParameter;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class Spoon {
    public static String getExecutableKey(CtExecutable<?> executable) {
        StringBuilder methodKey = new StringBuilder();
        String className = executable.getParent(CtClass.class).getSimpleName();
        methodKey.append(className)
                .append(".")
                .append(executable instanceof CtConstructor ? className : executable.getSimpleName())
                .append("(");
        for (CtParameter<?> parameter : executable.getParameters()) {
            methodKey.append(parameter.toString())
                    .append(", ");
        }
        if (methodKey.charAt(methodKey.length() - 1) != '(') {
            methodKey.delete(methodKey.length() - 2, methodKey.length());
        }
        methodKey.append(")");

        return methodKey.toString();
    }

    public static void main(String[] args) throws Exception {
        spoon.Launcher.main(args);
    }
}
