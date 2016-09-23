package com.intellij.lang.cloudslang.dependencies;

import io.cloudslang.lang.compiler.modeller.DependenciesHelper;
import io.cloudslang.lang.compiler.modeller.ExecutableBuilder;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.SlangModellerImpl;
import io.cloudslang.lang.compiler.modeller.TransformersHandler;
import io.cloudslang.lang.compiler.modeller.transformers.BreakTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.DependencyFormatValidator;
import io.cloudslang.lang.compiler.modeller.transformers.DoTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.ForTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.InputsTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.JavaActionTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.NavigateTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.OutputsTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.ParallelLoopForTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.PublishTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.PythonActionTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.ResultsTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.Transformer;
import io.cloudslang.lang.compiler.modeller.transformers.WorkFlowTransformer;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.utils.ParserExceptionHandler;
import io.cloudslang.lang.compiler.validator.ExecutableValidatorImpl;
import io.cloudslang.lang.compiler.validator.PreCompileValidatorImpl;
import io.cloudslang.lang.compiler.validator.SystemPropertyValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;


public class CloudSlangDependenciesProvider {
    private static final YamlParser yamlParser = yamlParser();
    private static final SlangModeller slangModeller = slangModeller();

    public static YamlParser getYamlParser() {
        return yamlParser;
    }

    public static SlangModeller getSlangModeller() {
        return slangModeller;
    }

    private static YamlParser yamlParser() {
        YamlParser yamlParser = new YamlParser();
        yamlParser.setYaml(yaml());
        yamlParser.setParserExceptionHandler(new ParserExceptionHandler());
        yamlParser.setExecutableValidator(executableValidator());
        return yamlParser;
    }

    private static SlangModeller slangModeller() {
        SlangModellerImpl slangModeller = new SlangModellerImpl();
        slangModeller.setExecutableBuilder(executableBuilder());
        return slangModeller;
    }

    private static ExecutableBuilder executableBuilder() {
        ExecutableBuilder executableBuilder = new ExecutableBuilder();
        executableBuilder.setTransformersHandler(new TransformersHandler());
        executableBuilder.setDependenciesHelper(dependenciesHelper());
        executableBuilder.setPreCompileValidator(preCompileValidator());
        executableBuilder.setResultsTransformer(resultsTransformer());
        executableBuilder.setTransformers(transformers());
        executableBuilder.setExecutableValidator(executableValidator());
        executableBuilder.initScopedTransformersAndKeys();
        return executableBuilder;
    }

    private static PreCompileValidatorImpl preCompileValidator() {
        PreCompileValidatorImpl preCompileValidator = new PreCompileValidatorImpl();
        preCompileValidator.setExecutableValidator(executableValidator());
        return preCompileValidator;
    }

    private static DependenciesHelper dependenciesHelper() {
        DependenciesHelper dependenciesHelper = new DependenciesHelper();
        dependenciesHelper.setPublishTransformer(publishTransformer());
        return dependenciesHelper;
    }

    private static ResultsTransformer resultsTransformer() {
        ResultsTransformer resultsTransformer = new ResultsTransformer();
        resultsTransformer.setPreCompileValidator(preCompileValidator());
        resultsTransformer.setExecutableValidator(executableValidator());
        return resultsTransformer;
    }

    private static ExecutableValidatorImpl executableValidator() {
        ExecutableValidatorImpl executableValidator = new ExecutableValidatorImpl();
        executableValidator.setSystemPropertyValidator(new SystemPropertyValidatorImpl());
        return executableValidator;
    }

    private static Yaml yaml() {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml;
    }

    private static List<Transformer> transformers() {
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(javaActionTransformer());
        transformers.add(doTransformer());
        transformers.add(resultsTransformer());
        transformers.add(forTransformer());
        transformers.add(pythonActionTransformer());
        transformers.add(publishTransformer());
        transformers.add(parallelLoopForTransformer());
        transformers.add(breakTransformer());
        transformers.add(outputsTransformer());
        transformers.add(inputsTransformer());
        transformers.add(workFlowTransformer());
        transformers.add(navigateTransformer());
        return transformers;
    }

    private static NavigateTransformer navigateTransformer() {
        return new NavigateTransformer();
    }

    private static BreakTransformer breakTransformer() {
        return new BreakTransformer();
    }

    private static ParallelLoopForTransformer parallelLoopForTransformer() {
        return new ParallelLoopForTransformer();
    }

    private static PythonActionTransformer pythonActionTransformer() {
        return new PythonActionTransformer();
    }

    private static ForTransformer forTransformer() {
        return new ForTransformer();
    }

    private static DoTransformer doTransformer() {
        DoTransformer doTransformer = new DoTransformer();
        doTransformer.setPreCompileValidator(preCompileValidator());
        return doTransformer;
    }

    private static JavaActionTransformer javaActionTransformer() {
        JavaActionTransformer javaActionTransformer = new JavaActionTransformer();
        javaActionTransformer.setDependencyFormatValidator(dependencyFormatValidator());
        return javaActionTransformer;
    }

    private static InputsTransformer inputsTransformer() {
        InputsTransformer inputsTransformer = new InputsTransformer();
        inputsTransformer.setPreCompileValidator(preCompileValidator());
        inputsTransformer.setExecutableValidator(executableValidator());
        return inputsTransformer;
    }

    private static OutputsTransformer outputsTransformer() {
        OutputsTransformer outputsTransformer = new OutputsTransformer();
        outputsTransformer.setPreCompileValidator(preCompileValidator());
        outputsTransformer.setExecutableValidator(executableValidator());
        return outputsTransformer;
    }

    private static WorkFlowTransformer workFlowTransformer() {
        return new WorkFlowTransformer();
    }

    private static PublishTransformer publishTransformer() {
        PublishTransformer publishTransformer = new PublishTransformer();
        publishTransformer.setPreCompileValidator(preCompileValidator());
        publishTransformer.setExecutableValidator(executableValidator());
        return publishTransformer;
    }

    private static DependencyFormatValidator dependencyFormatValidator() {
        return new DependencyFormatValidator();
    }
}
