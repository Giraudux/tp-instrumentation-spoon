package fr.univ.nantes.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class MethodCallTreeProcessor extends AbstractProcessor<CtMethod> {
    @Override
    public void process(CtMethod method) {
        System.out.println(method.getParent().getSignature() + ": " + method.getSignature());
        String enterCode = "fr.univ.nantes.logger.LogWriter.enterMethod(\"" + method.getParent().getSignature() + ": " + method.getSignature() + "\"); try {//";
        String leaveCode = "} finally { fr.univ.nantes.logger.LogWriter.leaveMethod(); }//";
        CtStatement enterStatement = getFactory().Code().createCodeSnippetStatement(enterCode);
        CtStatement leaveStatement = getFactory().Code().createCodeSnippetStatement(leaveCode);
        method.getBody().insertBegin(enterStatement);
        method.getBody().insertEnd(leaveStatement);
    }
}
