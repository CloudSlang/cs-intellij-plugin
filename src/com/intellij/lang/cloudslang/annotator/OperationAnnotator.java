package com.intellij.lang.cloudslang.annotator;

import com.google.common.collect.ImmutableSet;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.cloudslang.Keywords;
import com.intellij.psi.PsiElement;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLPsiElement;
import org.jetbrains.yaml.psi.impl.YAMLKeyValueImpl;

import static com.intellij.lang.cloudslang.ErrorMessages.DUPLICATED_OPERATION_ACTION;
import static com.intellij.lang.cloudslang.ErrorMessages.INVALID_ELEMENT;
import static com.intellij.lang.cloudslang.ErrorMessages.MISSING_OPERATION_ACTION;
import static com.intellij.lang.cloudslang.ErrorMessages.MISSING_OPERATION_NAME;
import static com.intellij.lang.cloudslang.ErrorMessages.UNKNOWN_ELEMENT;
import static com.intellij.lang.cloudslang.Keywords.INPUTS;
import static com.intellij.lang.cloudslang.Keywords.JAVA_ACTION;
import static com.intellij.lang.cloudslang.Keywords.NAME;
import static com.intellij.lang.cloudslang.Keywords.OPERATION;
import static com.intellij.lang.cloudslang.Keywords.OUTPUTS;
import static com.intellij.lang.cloudslang.Keywords.PYTHON_ACTION;
import static com.intellij.lang.cloudslang.Keywords.RESULTS;
import static java.util.stream.Collectors.toList;
import static org.jetbrains.yaml.YAMLElementTypes.SCALAR_VALUES;

/**
 * Author: Ligia Centea
 * Date: 6/30/2016.
 */
public class OperationAnnotator implements Annotator {

    private static final ImmutableSet<String> allowedChildren = ImmutableSet.of(NAME, JAVA_ACTION, PYTHON_ACTION, INPUTS, OUTPUTS, RESULTS);

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof YAMLKeyValue) {
            YAMLKeyValue yamlKeyValue = (YAMLKeyValueImpl) psiElement;

            if (yamlKeyValue.getKeyText().equals(OPERATION)) { //validate operation
                YAMLPsiElement operationBody = getValue(yamlKeyValue);

                if (operationBody != null) {
                    //validate that the operation has a name
                    if (!childExists(operationBody, NAME)) {
                        annotationHolder.createErrorAnnotation(yamlKeyValue.getKey(), MISSING_OPERATION_NAME);
                    }

                    //validate that the operation has a SINGLE action
                    if (!childExists(operationBody, Keywords.JAVA_ACTION) && !childExists(operationBody, Keywords.PYTHON_ACTION)) {
                        annotationHolder.createErrorAnnotation(yamlKeyValue.getKey(), MISSING_OPERATION_ACTION);
                    } else if (childExists(operationBody, Keywords.JAVA_ACTION) && childExists(operationBody, Keywords.PYTHON_ACTION)) {
                        annotationHolder.createErrorAnnotation(yamlKeyValue.getKey(), DUPLICATED_OPERATION_ACTION);
                    }

                    //validate that the operation does not contain any child that are not in key: value format
                    List<PsiElement> childrenWithIllegalType = getChildrenWithIllegalType(operationBody);
                    if (childrenWithIllegalType != null) {
                        childrenWithIllegalType.stream()
                                .forEach(c -> annotationHolder.createErrorAnnotation(c, INVALID_ELEMENT));
                    }

                    //validate that the operation does not contain any childes except the ones allowed
                    List<YAMLPsiElement> childrenWithIllegalName = getChildrenWithIllegalName(operationBody);
                    if (childrenWithIllegalName != null) {
                        childrenWithIllegalName.stream()
                                .forEach(c -> annotationHolder.createErrorAnnotation(c, UNKNOWN_ELEMENT));
                    }

                } else {
                    annotationHolder.createErrorAnnotation(yamlKeyValue.getKey(), MISSING_OPERATION_NAME);
                    annotationHolder.createErrorAnnotation(yamlKeyValue.getKey(), DUPLICATED_OPERATION_ACTION);
                }
            }
        }
    }

    private List<PsiElement> getChildrenWithIllegalType(YAMLPsiElement yamlPsiElement) {
        return Stream.of(yamlPsiElement.getNode().getChildren(SCALAR_VALUES))
                .map(ASTNode::getPsi)
                .collect(toList());
    }

    private List<YAMLPsiElement> getChildrenWithIllegalName(YAMLPsiElement yamlPsiElement) {
        return yamlPsiElement.getYAMLElements().stream()
                .filter(e -> e instanceof YAMLKeyValue)
                .map(YAMLKeyValue.class::cast)
                .filter(e -> !allowedChildren.contains(e.getKeyText()))
                .collect(toList());
    }

    private boolean childExists(YAMLPsiElement yamlPsiElement, String childName) {
        List<YAMLPsiElement> children = yamlPsiElement.getYAMLElements();
        return children.stream().anyMatch(p -> p.getText().startsWith(childName));
    }

    private YAMLPsiElement getValue(YAMLPsiElement keyValueNode) {
        List<YAMLPsiElement> yamlElements = keyValueNode.getYAMLElements();
        if (yamlElements.isEmpty()) {
            return null;
        } else {
            //else, return the only YAMLPsiElement it contains
            //It will contain only an element of this type, since it is a key-value pair
            return yamlElements.get(0);
        }
    }
}
