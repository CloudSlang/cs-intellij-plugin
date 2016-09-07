package com.intellij.lang.cloudslang.configuration;

import io.cloudslang.lang.compiler.modeller.DependenciesHelper;
import io.cloudslang.lang.compiler.modeller.ExecutableBuilder;
import io.cloudslang.lang.compiler.modeller.SlangModeller;
import io.cloudslang.lang.compiler.modeller.SlangModellerImpl;
import io.cloudslang.lang.compiler.modeller.TransformersHandler;
import io.cloudslang.lang.compiler.modeller.transformers.BreakTransformer;
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
import io.cloudslang.lang.compiler.validator.PreCompileValidator;
import io.cloudslang.lang.compiler.validator.PreCompileValidatorImpl;
import io.cloudslang.lang.entities.encryption.DummyEncryptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

/**
 * Created by Ligia Centea
 * Date: 9/7/2016.
 */
public class SpringConfiguration {

    @Bean
    @Lazy
    public YamlParser yamlParser() {
        return new YamlParser();
    }

    @Bean
    @Lazy
    public ParserExceptionHandler parserExceptionHandler() {
        return new ParserExceptionHandler();
    }

    @Bean
    public Yaml yaml() {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml;
    }

    @Bean
    @Lazy
    public SlangModeller slangModeller() {
        return new SlangModellerImpl();
    }

    @Bean
    @Lazy
    public ExecutableBuilder executableBuilder() {
        return new ExecutableBuilder();
    }

    @Bean
    @Lazy
    public TransformersHandler transformersHandler() {
        return new TransformersHandler();
    }

    @Bean
    @Lazy
    public DependenciesHelper dependenciesHelper() {
        return new DependenciesHelper();
    }

    @Bean
    @Lazy
    public PreCompileValidator preCompileValidator() {
        return new PreCompileValidatorImpl();
    }

    @Bean
    @Lazy
    public ResultsTransformer resultsTransformer() {
        return new ResultsTransformer();
    }


    @Bean
    @Lazy
    public PublishTransformer publishTransformer() {
        return new PublishTransformer();
    }

    @Bean
    @Lazy
    public List<Transformer> transformers() {
        List<Transformer> transformers = new ArrayList<>();
        transformers.add(new JavaActionTransformer());
        transformers.add(new DoTransformer());
        transformers.add(new ResultsTransformer());
        transformers.add(new ForTransformer());
        transformers.add(new PythonActionTransformer());
        transformers.add(new PublishTransformer());
        transformers.add(new ParallelLoopForTransformer());
        transformers.add(new BreakTransformer());
        transformers.add(new OutputsTransformer());
        transformers.add(new InputsTransformer());
        transformers.add(new WorkFlowTransformer());
        transformers.add(new NavigateTransformer());
        return transformers;
    }

    @Bean
    @Lazy
    public DummyEncryptor dummyEncryptor() {
        return new DummyEncryptor();
    }
}
