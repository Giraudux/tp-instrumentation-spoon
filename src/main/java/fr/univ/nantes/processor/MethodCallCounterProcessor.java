package fr.univ.nantes.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class MethodCallCounterProcessor extends AbstractProcessor<CtMethod> {
    @Override
    public void process(CtMethod method) {
        System.out.println(method.getParent().getSignature() + ": " + method.getSignature());
        String code = "fr.univ.nantes.logger.LogWriter.call(\"" + method.getParent().getSignature() + ": " + method.getSignature() + "\")";
        CtStatement snippet = getFactory().Code().createCodeSnippetStatement(code);
        method.getBody().insertBegin(snippet);
    }
}
