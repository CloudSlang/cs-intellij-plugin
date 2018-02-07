/*
 * (c) Copyright 2016-2018 Micro Focus, L.P.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0 which accompany this distribution.
 *
 * The Apache License is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.cloudslang.intellij.lang.dependencies;

import com.google.common.collect.Lists;
import io.cloudslang.lang.compiler.MetadataExtractor;
import io.cloudslang.lang.compiler.MetadataExtractorImpl;
import io.cloudslang.lang.compiler.SlangCompiler;
import io.cloudslang.lang.compiler.SlangCompilerImpl;
import io.cloudslang.lang.compiler.caching.CachedPrecompileService;
import io.cloudslang.lang.compiler.caching.CachedPrecompileServiceImpl;
import io.cloudslang.lang.compiler.modeller.*;
import io.cloudslang.lang.compiler.modeller.transformers.*;
import io.cloudslang.lang.compiler.parser.MetadataParser;
import io.cloudslang.lang.compiler.parser.YamlParser;
import io.cloudslang.lang.compiler.parser.utils.MetadataValidator;
import io.cloudslang.lang.compiler.parser.utils.MetadataValidatorImpl;
import io.cloudslang.lang.compiler.parser.utils.ParserExceptionHandler;
import io.cloudslang.lang.compiler.scorecompiler.*;
import io.cloudslang.lang.compiler.validator.*;
import io.cloudslang.lang.entities.encryption.DummyEncryptor;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.util.List;


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

    public PreCompileValidator externalPrecompileValidator() {
        PreCompileValidatorImpl preCompileValidator = new PreCompileValidatorImpl();
        preCompileValidator.setExecutableValidator(externalExecutableValidator());
        return preCompileValidator;
    }

    public ExecutableValidator externalExecutableValidator() {
        return new DefaultExternalExecutableValidator();
    }

    public ExecutionPlanBuilder executionPlanBuilder() {
        ExecutionPlanBuilder executionPlanBuilder = new ExecutionPlanBuilder();
        executionPlanBuilder.setStepFactory(stepFactory());
        executionPlanBuilder.setExternalStepFactory(externalStepFactory());
        return executionPlanBuilder;
    }

    public ExecutionStepFactory stepFactory() {
        return new ExecutionStepFactory();
    }

    public ExternalExecutionStepFactory externalStepFactory() {
        return new DefaultExternalExecutionStepFactory();
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
        metadataExtractor.setMetadataValidator(metadataValidator());
        return metadataExtractor;
    }

    public MetadataParser metadataParser() {
        MetadataParser metadataParser = new MetadataParser();
        metadataParser.setParserExceptionHandler(parserExceptionHandler());
        return metadataParser;
    }

    public MetadataValidator metadataValidator() {
        MetadataValidatorImpl metadataValidator = new MetadataValidatorImpl();
        metadataValidator.setMetadataParser(metadataParser());
        return metadataValidator;
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

    public PublishTransformer externalPublishTransformer() {
        PublishTransformer externalPublish = new PublishTransformer();
        setAbstractOutputTransformerDependencies(externalPublish);
        externalPublish.setType(Transformer.Type.EXTERNAL);

        return externalPublish;
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
        final NavigateTransformer navigateTransformer = new NavigateTransformer();
        navigateTransformer.setExecutableValidator(executableValidator());
        return navigateTransformer;
    }

    public NavigateTransformer externalNavigateTransformer() {
        final NavigateTransformer externalNavigate = new NavigateTransformer();
        externalNavigate.setExecutableValidator(externalExecutableValidator());
        externalNavigate.setType(Transformer.Type.EXTERNAL);
        return externalNavigate;
    }

    public DoTransformer doTransformer() {
        DoTransformer doTransformer = new DoTransformer();
        doTransformer.setPreCompileValidator(precompileValidator());
        doTransformer.setExecutableValidator(executableValidator());

        return doTransformer;
    }

    public DoExternalTransformer doExternalTransformer() {
        DoExternalTransformer doExternalTransformer = new DoExternalTransformer();
        doExternalTransformer.setPreCompileValidator(externalPrecompileValidator());
        doExternalTransformer.setExecutableValidator(externalExecutableValidator());

        return doExternalTransformer;
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
        return Lists.newArrayList(
                pythonActionTransformer(),
                parallelLoopForTransformer(),
                publishTransformer(),
                externalPublishTransformer(),
                navigateTransformer(),
                externalNavigateTransformer(),
                inputsTransformer(),
                workFlowTransformer(),
                resultsTransformer(),
                doTransformer(),
                doExternalTransformer(),
                outputsTransformer(),
                javaActionTransformer(),
                forTransformer(),
                breakTransformer());
    }

    private void setAbstractOutputTransformerDependencies(AbstractOutputsTransformer abstractOutputsTransformer) {
        abstractOutputsTransformer.setPreCompileValidator(precompileValidator());
        abstractOutputsTransformer.setExecutableValidator(executableValidator());
    }

    private void setExecutableValidator(AbstractForTransformer abstractForTransformer) {
        abstractForTransformer.setExecutableValidator(executableValidator());
    }

}
