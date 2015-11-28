package vv.spoon.processor;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtMethod;

/**
 * @author Alexis Giraudet
 * @author Thomas Minier
 */
public class CountProcessor extends AbstractProcessor<CtMethod> {
    @Override
    public void process(CtMethod method) {
        System.out.println(method.getSignature());
        String code = "\t\tvv.spoon.logger.LogWriter.call(\""+method.getSignature()+"\");\n";
        CtCodeSnippetStatement snippet = getFactory().Core().createCodeSnippetStatement();
        snippet.setValue(code);
        method.getBody().insertBegin(snippet);
    }
}
