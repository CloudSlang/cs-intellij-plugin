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

package io.cloudslang.intellij.project.module;

import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import io.cloudslang.intellij.lang.CloudSlangIcons;
import io.cloudslang.intellij.project.wizard.CloudSlangModuleBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class CloudSlangModuleType extends ModuleType<CloudSlangModuleBuilder> {

    private static final String ID = "CLOUDSLANG_MODULE";

    public CloudSlangModuleType() {
        super(ID);
    }

    public static CloudSlangModuleType getInstance() {
        return (CloudSlangModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public CloudSlangModuleBuilder createModuleBuilder() {
        return new CloudSlangModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "CloudSlang";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Create a new project for CloudSlang content";
    }

    @Override
    public Icon getBigIcon() {
        return CloudSlangIcons.RUN_ICON;
    }

    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return CloudSlangIcons.RUN_ICON;
    }
}
