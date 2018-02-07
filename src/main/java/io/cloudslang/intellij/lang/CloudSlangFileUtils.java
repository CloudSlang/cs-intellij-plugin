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


import com.intellij.psi.PsiFile;
import org.jetbrains.yaml.YAMLFileType;

import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;

public class CloudSlangFileUtils {

    private static final String[] CLOUD_SLANG_EXTENSIONS = {".sl", ".sl.yaml", ".sl.yml", ".prop.sl"};

    private CloudSlangFileUtils() {
    }

    public static boolean isCloudSlangFile(PsiFile psiFile) {
        return (psiFile.getFileType() == YAMLFileType.YML) && isCloudSlangFile(psiFile.getName());
    }

    public static boolean isCloudSlangFile(String fileName) {
        for (String extension : CLOUD_SLANG_EXTENSIONS) {
            if (endsWithIgnoreCase(fileName, extension)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCloudSlangSystemPropertiesFile(String fileName) {
        return endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[3]);
    }

    public static String getNameWithoutExtension(final String fileName) {
        // This code is to prevent order of array to affect this algorithm
        if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[3])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[3]));
        } else if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[0])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[0]));
        } else if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[1])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[1]));
        } else if (endsWithIgnoreCase(fileName, CLOUD_SLANG_EXTENSIONS[2])) {
            return fileName.substring(0, fileName.indexOf(CLOUD_SLANG_EXTENSIONS[2]));
        } else {
            return removeExtension(fileName);
        }
    }

}
