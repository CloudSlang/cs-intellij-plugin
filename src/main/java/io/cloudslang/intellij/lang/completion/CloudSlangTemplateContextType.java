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

import com.intellij.codeInsight.template.FileTypeBasedContextType;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLFileType;

import static io.cloudslang.intellij.lang.CloudSlangFileUtils.isCloudSlangFile;


public class CloudSlangTemplateContextType extends FileTypeBasedContextType {

    private static final String CLOUD_SLANG = "CloudSlang";

    public CloudSlangTemplateContextType() {
        super(CLOUD_SLANG, CLOUD_SLANG, YAMLFileType.YML);
    }

    @Override
    public boolean isInContext(@NotNull PsiFile psiFile, int i) {
        return isCloudSlangFile(psiFile);
    }

}
