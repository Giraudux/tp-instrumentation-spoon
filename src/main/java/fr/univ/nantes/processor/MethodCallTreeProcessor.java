package fr.univ.nantes.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class MethodCallTreeProcessor extends AbstractProcessor<CtMethod> {
    @Override
    public void process(CtMethod method) {
        String enterCode = "fr.univ.nantes.logger.LogWriter.enterMethod(\"" + getMethodKey(method) + "\")";
        String leaveCode = "fr.univ.nantes.logger.LogWriter.leaveMethod()";
        CtStatement enterCtStatement = getFactory().Code().createCodeSnippetStatement(enterCode);
        CtStatement leaveCtStatement = getFactory().Code().createCodeSnippetStatement(leaveCode);
        CtTry ctTry = getFactory().Core().createTry();
        CtBlock finallyCtBlock = getFactory().Core().createBlock();
        CtBlock bodyCtBlock = getFactory().Core().createBlock();

        finallyCtBlock.addStatement(leaveCtStatement);
        ctTry.setFinalizer(finallyCtBlock);
        ctTry.setBody(method.getBody());
        bodyCtBlock.addStatement(enterCtStatement);
        bodyCtBlock.addStatement(ctTry);
        method.setBody(bodyCtBlock);
    }

    private String getMethodKey(CtMethod<?> method) {
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
