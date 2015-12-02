package fr.univ.nantes.processor;

import fr.univ.nantes.Spoon;
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
    public void process(CtExecutable<?> executable) {
        String code = "fr.univ.nantes.logger.LogWriter.call(\"" + Spoon.getExecutableKey(executable) + "\")";
        CtStatement snippet = getFactory().Code().createCodeSnippetStatement(code);

        executable.getBody().insertBegin(snippet);
    }
}
