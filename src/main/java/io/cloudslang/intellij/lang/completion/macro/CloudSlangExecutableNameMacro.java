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

package io.cloudslang.intellij.lang.completion.macro;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Macro;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.codeInsight.template.TextResult;
import io.cloudslang.intellij.lang.CloudSlangFileUtils;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;


public class CloudSlangExecutableNameMacro extends Macro {

    @Override
    public String getName() {
        return "cloudSlangExecutableName";
    }

    @Override
    public String getPresentableName() {
        return "cloudSlangExecutableName()";
    }

    @Override
    public Result calculateResult(@NotNull Expression[] params, ExpressionContext context) {
        final Project project = context.getProject();
        if (context.getEditor() == null) {
            return new TextResult("");
        }
        final PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(context.getEditor().getDocument());
        if (file == null) {
            return new TextResult("");
        }
        return new TextResult(CloudSlangFileUtils.getNameWithoutExtension(file.getName()));
    }

    @Override
    public Result calculateQuickResult(@NotNull Expression[] params, ExpressionContext context) {
        return calculateResult(params, context);
    }

    @Override
    public boolean isAcceptableInContext(TemplateContextType context) {
        return true;
    }

}

