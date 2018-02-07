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

package io.cloudslang.intellij.lang.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import io.cloudslang.lang.compiler.SlangTextualKeys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;

import java.lang.reflect.Field;

import static io.cloudslang.intellij.lang.CloudSlangIcons.STANDARD;

public class CloudSlangCompletionContributor extends CompletionContributor {

    public CloudSlangCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(YAMLLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
                        addSlangKeywords(resultSet);
                        addYamlKeywordsUsedByCloudSlang(resultSet);
                    }
                }
        );
    }

    private void addSlangKeywords(CompletionResultSet resultSet) {
        for (Field field : SlangTextualKeys.class.getFields()) {
            try {
                String value = field.get(null).toString();
                addCompletion(resultSet, value, value + ":");
            } catch (IllegalAccessException ignored) {
            }
        }
    }

    private void addCompletion(CompletionResultSet resultSet, String name, String template) {
        LookupElementBuilder templateBuilder = LookupElementBuilder.create(template)
                .withPresentableText(name)
                .withIcon(STANDARD);
        resultSet.addElement(templateBuilder);
    }

    private void addYamlKeywordsUsedByCloudSlang(CompletionResultSet resultSet) {
        String namespace = "namespace";
        addCompletion(resultSet, namespace, namespace + ":");

        String imports = "imports";
        addCompletion(resultSet, imports, imports + ":");

        String extensions = "extensions";
        addCompletion(resultSet, extensions, extensions + ":");
    }

}
