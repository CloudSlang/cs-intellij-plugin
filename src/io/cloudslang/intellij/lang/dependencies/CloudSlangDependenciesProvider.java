package io.cloudslang.intellij.lang.dependencies;

import io.cloudslang.lang.compiler.MetadataExtractor;
import io.cloudslang.lang.compiler.MetadataExtractorImpl;
import io.cloudslang.lang.compiler.SlangCompiler;
import io.cloudslang.lang.compiler.SlangCompilerImpl;
import io.cloudslang.lang.compiler.caching.CachedPrecompileService;
import io.cloudslang.lang.compiler.caching.CachedPrecompileServiceImpl;
import io.cloudslang.lang.compiler.modeller.DependenciesHelper;
import io.cloudslang.lang.compiler.modeller.ExecutableBuilder;
import io.cloudslang.lang.compiler.modeller.MetadataModeller;
import io.cloudslang.lang.compiler.modeller.MetadataModellerImpl;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.SlangModellerImpl;
import io.cloudslang.lang.compiler.modeller.TransformersHandler;
import io.cloudslang.lang.compiler.modeller.transformers.AbstractForTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.AbstractInputsTransformer;
import io.cloudslang.lang.compiler.modeller.transformers.AbstractOutputsTransformer;
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
import io.cloudslang.lang.compiler.parser.MetadataParser;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.utils.ParserExceptionHandler;
import io.cloudslang.lang.compiler.scorecompiler.ExecutionPlanBuilder;
import io.cloudslang.lang.compiler.scorecompiler.ExecutionStepFactory;
import io.cloudslang.lang.compiler.scorecompiler.ScoreCompiler;
import io.cloudslang.lang.compiler.scorecompiler.ScoreCompilerImpl;
import io.cloudslang.lang.compiler.validator.CompileValidator;
import io.cloudslang.lang.compiler.validator.CompileValidatorImpl;
import io.cloudslang.lang.compiler.validator.ExecutableValidator;
import io.cloudslang.lang.compiler.validator.ExecutableValidatorImpl;
import io.cloudslang.lang.compiler.validator.PreCompileValidator;
import io.cloudslang.lang.compiler.validator.PreCompileValidatorImpl;
import io.cloudslang.lang.compiler.validator.SystemPropertyValidator;
import io.cloudslang.lang.compiler.validator.SystemPropertyValidatorImpl;
import io.cloudslang.lang.entities.encryption.DummyEncryptor;
import java.util.ArrayList;
import java.util.List;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;


public class CloudSlangDependenciesProvider {

    public Yaml yaml() {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml;
    }


    public DummyEncryptor dummyEncryptor() {
        return new DummyEncryptor();
    }


    public YamlParser yamlParser() {
        YamlParser yamlParser = new YamlParser() {
            @Override
            public Yaml getYaml() {
                return yaml();
            }
        };
        yamlParser.setExecutableValidator(executableValidator());
        yamlParser.setParserExceptionHandler(parserExceptionHandler());
        return yamlParser;
    }


    public CachedPrecompileService cachedPrecompileService() {
        return new CachedPrecompileServiceImpl();
    }


    public PreCompileValidator precompileValidator() {
        PreCompileValidatorImpl preCompileValidator = new PreCompileValidatorImpl();

        preCompileValidator.setExecutableValidator(executableValidator());
        return preCompileValidator;
    }


    public ExecutableValidator executableValidator() {
        ExecutableValidatorImpl executableValidator = new ExecutableValidatorImpl();
        executableValidator.setSystemPropertyValidator(systemPropertyValidator());

        return executableValidator;
    }


    public ExecutionPlanBuilder executionPlanBuilder() {
        ExecutionPlanBuilder executionPlanBuilder = new ExecutionPlanBuilder();
        executionPlanBuilder.setStepFactory(stepFactory());

        return executionPlanBuilder;
    }


    public ExecutionStepFactory stepFactory() {
        return new ExecutionStepFactory();
    }


    public SystemPropertyValidator systemPropertyValidator() {
        return new SystemPropertyValidatorImpl();
    }


    public CompileValidator compileValidator() {
        return new CompileValidatorImpl();
    }


    public DependenciesHelper dependenciesHelper() {
        DependenciesHelper dependenciesHelper = new DependenciesHelper();

        dependenciesHelper.setPublishTransformer(publishTransformer());
        return dependenciesHelper;
    }


    public DependencyFormatValidator dependencyFormatValidator() {
        return new DependencyFormatValidator();
    }


    public MetadataExtractor metadataExtractor() {
        MetadataExtractorImpl metadataExtractor = new MetadataExtractorImpl();
        metadataExtractor.setMetadataModeller(metadataModeller());
        metadataExtractor.setMetadataParser(metadataParser());
        return metadataExtractor;
    }


    public MetadataParser metadataParser() {
        MetadataParser metadataParser = new MetadataParser();
        metadataParser.setParserExceptionHandler(parserExceptionHandler());
        return metadataParser;
    }


    public ParserExceptionHandler parserExceptionHandler() {
        return new ParserExceptionHandler();
    }


    public MetadataModeller metadataModeller() {
        return new MetadataModellerImpl();
    }


    public ScoreCompiler scoreCompiler() {
        ScoreCompilerImpl scoreCompiler = new ScoreCompilerImpl();
        scoreCompiler.setCompileValidator(compileValidator());
        scoreCompiler.setDependenciesHelper(dependenciesHelper());
        scoreCompiler.setExecutionPlanBuilder(executionPlanBuilder());

        return scoreCompiler;
    }


    public SlangCompiler slangCompiler() {
        SlangCompilerImpl slangCompiler = new SlangCompilerImpl();

        slangCompiler.setCachedPrecompileService(cachedPrecompileService());
        slangCompiler.setCompileValidator(compileValidator());
        slangCompiler.setScoreCompiler(scoreCompiler());
        slangCompiler.setSlangModeller(slangModeller());
        slangCompiler.setSystemPropertyValidator(systemPropertyValidator());
        slangCompiler.setYamlParser(yamlParser());

        return slangCompiler;
    }


    public SlangModeller slangModeller() {
        SlangModellerImpl slangModeller = new SlangModellerImpl();
        slangModeller.setExecutableBuilder(executableBuilder());
        return slangModeller;
    }


    public PublishTransformer publishTransformer() {
        PublishTransformer publishTransformer = new PublishTransformer();
        setAbstractOutputTransformerDependencies(publishTransformer);

        return publishTransformer;
    }


    public OutputsTransformer outputsTransformer() {
        OutputsTransformer outputsTransformer = new OutputsTransformer();
        setAbstractOutputTransformerDependencies(outputsTransformer);

        return outputsTransformer;
    }


    public PythonActionTransformer pythonActionTransformer() {
        PythonActionTransformer pythonActionTransformer = new PythonActionTransformer();
        pythonActionTransformer.setDependencyFormatValidator(dependencyFormatValidator());

        return pythonActionTransformer;
    }


    public BreakTransformer breakTransformer() {
        BreakTransformer breakTransformer = new BreakTransformer();
        breakTransformer.setExecutableValidator(executableValidator());

        return breakTransformer;
    }


    public NavigateTransformer navigateTransformer() {
        return new NavigateTransformer();
    }


    public DoTransformer doTransformer() {
        DoTransformer doTransformer = new DoTransformer();
        doTransformer.setPreCompileValidator(precompileValidator());

        return doTransformer;
    }


    public ResultsTransformer resultsTransformer() {
        ResultsTransformer resultsTransformer = new ResultsTransformer();
        resultsTransformer.setPreCompileValidator(precompileValidator());
        resultsTransformer.setExecutableValidator(executableValidator());

        return resultsTransformer;
    }


    public WorkFlowTransformer workFlowTransformer() {
        return new WorkFlowTransformer();
    }


    public InputsTransformer inputsTransformer() {
        InputsTransformer inputsTransformer = new InputsTransformer();
        setAbstractInputTransformerDependencies(inputsTransformer);

        return inputsTransformer;
    }


    public JavaActionTransformer javaActionTransformer() {
        JavaActionTransformer javaActionTransformer = new JavaActionTransformer();
        javaActionTransformer.setDependencyFormatValidator(dependencyFormatValidator());
        return javaActionTransformer;
    }


    public ParallelLoopForTransformer parallelLoopForTransformer() {
        ParallelLoopForTransformer parallelLoopForTransformer = new ParallelLoopForTransformer();

        setExecutableValidator(parallelLoopForTransformer);
        return parallelLoopForTransformer;
    }


    public ForTransformer forTransformer() {
        ForTransformer forTransformer = new ForTransformer();
        setExecutableValidator(forTransformer);

        return forTransformer;
    }


    public ExecutableBuilder executableBuilder() {
        ExecutableBuilder executableBuilder = new ExecutableBuilder();
        executableBuilder.setTransformers(transformers());
        executableBuilder.setTransformersHandler(transformersHandler());
        executableBuilder.setDependenciesHelper(dependenciesHelper());
        executableBuilder.setPreCompileValidator(precompileValidator());
        executableBuilder.setResultsTransformer(resultsTransformer());
        executableBuilder.setExecutableValidator(executableValidator());

        executableBuilder.initScopedTransformersAndKeys();
        return executableBuilder;
    }

    private void setAbstractInputTransformerDependencies(AbstractInputsTransformer abstractInputsTransformer) {
        abstractInputsTransformer.setExecutableValidator(executableValidator());
        abstractInputsTransformer.setPreCompileValidator(precompileValidator());
    }


    public TransformersHandler transformersHandler() {
        return new TransformersHandler();
    }


    public List<Transformer> transformers() {
        List<Transformer> transformers = new ArrayList<>();

        transformers.add(pythonActionTransformer());
        transformers.add(parallelLoopForTransformer());
        transformers.add(publishTransformer());
        transformers.add(navigateTransformer());
        transformers.add(inputsTransformer());
        transformers.add(workFlowTransformer());
        transformers.add(resultsTransformer());
        transformers.add(doTransformer());
        transformers.add(outputsTransformer());
        transformers.add(javaActionTransformer());
        transformers.add(forTransformer());
        transformers.add(breakTransformer());

        return transformers;
    }

    private void setAbstractOutputTransformerDependencies(AbstractOutputsTransformer abstractOutputsTransformer) {
        abstractOutputsTransformer.setPreCompileValidator(precompileValidator());
        abstractOutputsTransformer.setExecutableValidator(executableValidator());
    }

    private void setExecutableValidator(AbstractForTransformer abstractForTransformer) {
        abstractForTransformer.setExecutableValidator(executableValidator());
    }

}
