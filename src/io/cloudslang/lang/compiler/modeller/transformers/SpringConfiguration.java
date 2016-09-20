package io.cloudslang.lang.compiler.modeller.transformers;

import io.cloudslang.lang.compiler.modeller.DependenciesHelper;
import io.cloudslang.lang.compiler.modeller.ExecutableBuilder;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.SlangModellerImpl;
import io.cloudslang.lang.compiler.modeller.TransformersHandler;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.utils.ParserExceptionHandler;
import io.cloudslang.lang.compiler.validator.ExecutableValidatorImpl;
import io.cloudslang.lang.compiler.validator.PreCompileValidatorImpl;
import io.cloudslang.lang.compiler.validator.SystemPropertyValidatorImpl;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

/**
 * Created by Ligia Centea
 * Date: 9/7/2016.
 * <p>
 * This class was temporary moved under io.cloudslang.lang.compiler.modeller.transformers just because
 * io.cloudslang.lang.compiler.modeller.transformers.DependencyFormatValidator is package protected and cannot use it otherwise
 * <p>
 * Luci will make that class public, then we can move this class back under com.intellij.lang.cloudslang.configuration
 */
public class SpringConfiguration {

//    @Bean
//    @Lazy
//    public YamlParser yamlParser() {
//        return new YamlParser();
//    }
//
//    @Bean
//    @Lazy
//    public ParserExceptionHandler parserExceptionHandler() {
//        return new ParserExceptionHandler();
//    }
//
//    @Bean
//    public Yaml yaml() {
//        Yaml yaml = new Yaml();
//        yaml.setBeanAccess(BeanAccess.FIELD);
//        return yaml;
//    }
//
//    @Bean
//    @Lazy
//    public SlangModeller slangModeller() {
//        return new SlangModellerImpl();
//    }
//
//    @Bean
//    @Lazy
//    public ExecutableBuilder executableBuilder() {
//        return new ExecutableBuilder();
//    }
//
//    @Bean
//    @Lazy
//    public TransformersHandler transformersHandler() {
//        return new TransformersHandler();
//    }
//
//    @Bean
//    @Lazy
//    public DependenciesHelper dependenciesHelper() {
//        return new DependenciesHelper();
//    }
//
//    @Bean
//    @Lazy
//    public PreCompileValidator preCompileValidator() {
//        return new PreCompileValidatorImpl();
//    }
//
//    @Bean
//    @Lazy
//    public List<Transformer> transformers() {
//        List<Transformer> transformers = new ArrayList<>();
//        transformers.add(javaActionTransformer());
//        transformers.add(doTransformer());
//        transformers.add(resultsTransformer());
//        transformers.add(forTransformer());
//        transformers.add(pythonActionTransformer());
//        transformers.add(publishTransformer());
//        transformers.add(parallelLoopForTransformer());
//        transformers.add(breakTransformer());
//        transformers.add(outputsTransformer());
//        transformers.add(inputsTransformer());
//        transformers.add(workFlowTransformer());
//        transformers.add(navigateTransformer());
//        return transformers;
//    }
//
//    @Bean
//    @Lazy
//    public NavigateTransformer navigateTransformer() {
//        return new NavigateTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public BreakTransformer breakTransformer() {
//        return new BreakTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public ParallelLoopForTransformer parallelLoopForTransformer() {
//        return new ParallelLoopForTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public PythonActionTransformer pythonActionTransformer() {
//        return new PythonActionTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public ForTransformer forTransformer() {
//        return new ForTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public DoTransformer doTransformer() {
//        return new DoTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public JavaActionTransformer javaActionTransformer() {
//        return new JavaActionTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public InputsTransformer inputsTransformer() {
//        return new InputsTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public OutputsTransformer outputsTransformer() {
//        return new OutputsTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public WorkFlowTransformer workFlowTransformer() {
//        return new WorkFlowTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public ResultsTransformer resultsTransformer() {
//        return new ResultsTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public PublishTransformer publishTransformer() {
//        return new PublishTransformer();
//    }
//
//    @Bean
//    @Lazy
//    public DependencyFormatValidator dependencyFormatValidator() {
//        return new DependencyFormatValidator();
//    }
//
//    @Bean
//    public DummyEncryptor dummyEncryptor() {
//        return new DummyEncryptor();
//    }
//
//    @Bean
//    public ApplicationContextProvider applicationContextProvider() {
//        return new ApplicationContextProvider();
//    }

    public YamlParser yamlParser() {
        YamlParser yamlParser = new YamlParser();
        yamlParser.setYaml(yaml());
        yamlParser.setParserExceptionHandler(new ParserExceptionHandler());
        yamlParser.setExecutableValidator(executableValidator());
        return yamlParser;
    }

    public SlangModeller slangModeller() {
        SlangModellerImpl slangModeller = new SlangModellerImpl();
        slangModeller.setExecutableBuilder(executableBuilder());
        return slangModeller;
    }

    private ExecutableBuilder executableBuilder() {
        ExecutableBuilder executableBuilder = new ExecutableBuilder();
        executableBuilder.setTransformersHandler(new TransformersHandler());
        executableBuilder.setDependenciesHelper(dependenciesHelper());
        executableBuilder.setPreCompileValidator(preCompileValidator());
        executableBuilder.setResultsTransformer(resultsTransformer());
        executableBuilder.setTransformers(transformers());
        executableBuilder.initScopedTransformersAndKeys();
        return executableBuilder;
    }

    @NotNull
    private PreCompileValidatorImpl preCompileValidator() {
        PreCompileValidatorImpl preCompileValidator = new PreCompileValidatorImpl();
        preCompileValidator.setExecutableValidator(executableValidator());
        return preCompileValidator;
    }

    @NotNull
    private DependenciesHelper dependenciesHelper() {
        DependenciesHelper dependenciesHelper = new DependenciesHelper();
        dependenciesHelper.setPublishTransformer(publishTransformer());
        return dependenciesHelper;
    }

    @NotNull
    private ResultsTransformer resultsTransformer() {
        ResultsTransformer resultsTransformer = new ResultsTransformer();
        resultsTransformer.setPreCompileValidator(preCompileValidator());
        resultsTransformer.setExecutableValidator(executableValidator());
        return resultsTransformer;
    }

    @NotNull
    private ExecutableValidatorImpl executableValidator() {
        ExecutableValidatorImpl executableValidator = new ExecutableValidatorImpl();
        executableValidator.setSystemPropertyValidator(new SystemPropertyValidatorImpl());
        return executableValidator;
    }

    public Yaml yaml() {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml;
    }

    public List<Transformer> transformers() {
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

    public NavigateTransformer navigateTransformer() {
        return new NavigateTransformer();
    }

    public BreakTransformer breakTransformer() {
        return new BreakTransformer();
    }

    public ParallelLoopForTransformer parallelLoopForTransformer() {
        return new ParallelLoopForTransformer();
    }

    public PythonActionTransformer pythonActionTransformer() {
        return new PythonActionTransformer();
    }

    public ForTransformer forTransformer() {
        return new ForTransformer();
    }

    public DoTransformer doTransformer() {
        return new DoTransformer();
    }

    public JavaActionTransformer javaActionTransformer() {
        JavaActionTransformer javaActionTransformer = new JavaActionTransformer();
        javaActionTransformer.setDependencyFormatValidator(dependencyFormatValidator());
        return javaActionTransformer;
    }

    public InputsTransformer inputsTransformer() {
        InputsTransformer inputsTransformer = new InputsTransformer();
        inputsTransformer.setPreCompileValidator(preCompileValidator());
        inputsTransformer.setExecutableValidator(executableValidator());
        return inputsTransformer;
    }

    public OutputsTransformer outputsTransformer() {
        OutputsTransformer outputsTransformer = new OutputsTransformer();
        outputsTransformer.setPreCompileValidator(preCompileValidator());
        outputsTransformer.setExecutableValidator(executableValidator());
        return outputsTransformer;
    }

    public WorkFlowTransformer workFlowTransformer() {
        return new WorkFlowTransformer();
    }

    public PublishTransformer publishTransformer() {
        PublishTransformer publishTransformer = new PublishTransformer();
        publishTransformer.setPreCompileValidator(preCompileValidator());
        publishTransformer.setExecutableValidator(executableValidator());
        return publishTransformer;
    }

    public DependencyFormatValidator dependencyFormatValidator() {
        return new DependencyFormatValidator();
    }
}
