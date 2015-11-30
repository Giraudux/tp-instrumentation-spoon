package fr.univ.nantes.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.code.CtTry;
import spoon.reflect.declaration.CtMethod;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class MethodCallTreeProcessor extends AbstractProcessor<CtMethod> {
    @Override
    public void process(CtMethod method) {
        System.out.println(method.getParent().getSignature() + ": " + method.getSignature());

        String enterCode = "fr.univ.nantes.logger.LogWriter.enterMethod(\"" + method.getParent().getSignature() + ": " + method.getSignature() + "\")";
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
}
