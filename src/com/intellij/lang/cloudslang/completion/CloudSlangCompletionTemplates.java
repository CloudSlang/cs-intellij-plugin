package com.intellij.lang.cloudslang.completion;


import java.util.Arrays;
import java.util.List;

public class CloudSlangCompletionTemplates {

    public static final String FLOW_TEMPLATE = "flow_template";
    public static final String FOR_TEMPLATE = "for_template";
    public static final String INPUT_PROPERTIES_TEMPLATE = "input_with_properties_template";
    public static final String JAVA_ACTION_TEMPLATE = "java_action_template";
    public static final String ON_FAILURE_TEMPLATE = "on_failure_template";
    public static final String OPERATION_TEMPLATE = "operation_template";
    public static final String OUTPUT_PROPERTIES_TEMPLATE = "output_with_properties_template";
    public static final String PARALLEL_LOOP_TEMPLATE = "parallel_loop_template";
    public static final String PROPERTIES_TEMPLATE = "properties_template";
    public static final String PYTHON_ACTION_TEMPLATE = "python_action_template";
    public static final String STEP_TEMPLATE = "step_template";
    public static final String SYSTEM_PROPERTY_TEMPLATE = "system_property_template";

    private static final String[] ALL_TEMPLATES = new String[]{
            FLOW_TEMPLATE,
            FOR_TEMPLATE,
            INPUT_PROPERTIES_TEMPLATE,
            JAVA_ACTION_TEMPLATE,
            ON_FAILURE_TEMPLATE,
            OPERATION_TEMPLATE,
            OUTPUT_PROPERTIES_TEMPLATE,
            PARALLEL_LOOP_TEMPLATE,
            PROPERTIES_TEMPLATE,
            PYTHON_ACTION_TEMPLATE,
            STEP_TEMPLATE, SYSTEM_PROPERTY_TEMPLATE
    };

    static List<String> getAllTemplateNames() {
        return Arrays.asList(ALL_TEMPLATES);
    }
}
