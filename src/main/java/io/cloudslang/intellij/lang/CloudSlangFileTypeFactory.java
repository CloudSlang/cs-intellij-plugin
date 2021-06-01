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

package io.cloudslang.intellij.lang;

import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.fileTypes.FileTypeFactory;


public class CloudSlangFileTypeFactory extends FileTypeFactory {

    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(CloudSlangFileType.INSTANCE, new FileNameMatcher() {
            @Override
            public boolean accept(@NonNls @NotNull String s) {
                return CloudSlangFileUtils.isCloudSlangFile(s);
            }

            @NotNull
            @Override
            public String getPresentableString() {
                return "sl;sl.yaml;sl.yml;prop.sl";
            }
        });
    }
}
