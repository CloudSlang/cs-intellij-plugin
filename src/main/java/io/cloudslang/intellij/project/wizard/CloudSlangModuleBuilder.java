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

package io.cloudslang.intellij.project.wizard;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ModifiableRootModel;
import io.cloudslang.intellij.project.module.CloudSlangModuleType;


public class CloudSlangModuleBuilder extends ModuleBuilder {

    @Override
    public void setupRootModel(ModifiableRootModel modifiableRootModel) throws ConfigurationException {
        doAddContentEntry(modifiableRootModel);
    }

    @Override
    public ModuleType getModuleType() {
        return CloudSlangModuleType.getInstance();
    }

    @Override
    public String getPresentableName() {
        return "CloudSlang";
    }

    @Override
    public String getGroupName() {
        return getPresentableName();
    }

    @Override
    public boolean isTemplateBased() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Create an empty CloudSlang project.";
    }
}
