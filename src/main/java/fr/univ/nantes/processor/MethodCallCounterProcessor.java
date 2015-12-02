package fr.univ.nantes.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class MethodCallCounterProcessor extends AbstractProcessor<CtExecutable<?>> {
    @Override
    public boolean isToBeProcessed(CtExecutable<?> executable) {
        return executable instanceof CtMethod || executable instanceof CtConstructor;
    }

    @Override
    public void process(CtExecutable<?> method) {
        String code = "fr.univ.nantes.logger.LogWriter.call(\"" + getMethodKey(method) + "\")";
        CtStatement snippet = getFactory().Code().createCodeSnippetStatement(code);

        method.getBody().insertBegin(snippet);
    }

    private String getMethodKey(CtExecutable<?> method) {
        StringBuilder methodKey = new StringBuilder();
        methodKey.append(method.getParent(CtClass.class).getSimpleName())
                .append(".").append(method.getSimpleName())
                .append("(");
        for (CtParameter<?> parameter : method.getParameters()) {
            methodKey.append(parameter.toString())
                    .append(", ");
        }
        if (methodKey.charAt(methodKey.length() - 1) != '(') {
            methodKey.delete(methodKey.length() - 2, methodKey.length());
        }
        methodKey.append(")");

        return methodKey.toString();
    }
}
