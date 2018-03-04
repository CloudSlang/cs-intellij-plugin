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

package io.cloudslang.intellij.lang.highlighter;

import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import io.cloudslang.intellij.lang.CloudSlangFileType;
import io.cloudslang.intellij.lang.CloudSlangFileUtils;


public class CloudSlangProblemFileHighlightFilter implements Condition<VirtualFile> {
    @Override
    public boolean value(VirtualFile virtualFile) {
        return (virtualFile.getFileType() == CloudSlangFileType.INSTANCE)
                || (CloudSlangFileUtils.isCloudSlangFile(virtualFile.getName()));
    }
}
