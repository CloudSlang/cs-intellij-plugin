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


import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.YAMLParserDefinition;
import org.jetbrains.yaml.YAMLTokenTypes;
import org.jetbrains.yaml.lexer.YAMLFlexLexer;
import org.jetbrains.yaml.parser.YAMLParser;
import org.jetbrains.yaml.psi.impl.YAMLFileImpl;

public class CloudSlangParserDefinition extends YAMLParserDefinition {

    private static final TokenSet myCommentTokens = TokenSet.create(YAMLTokenTypes.COMMENT);

    @NotNull
    public Lexer createLexer(final Project project) {
        return new YAMLFlexLexer();
    }

    @Nullable
    public PsiParser createParser(final Project project) {
        return new YAMLParser();
    }

    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(YAMLTokenTypes.WHITESPACE);
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return myCommentTokens;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.create(YAMLTokenTypes.SCALAR_STRING, YAMLTokenTypes.SCALAR_DSTRING, YAMLTokenTypes.TEXT);
    }

    public PsiFile createFile(final FileViewProvider viewProvider) {
        return new YAMLFileImpl(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(final ASTNode left, final ASTNode right) {
        return SpaceRequirements.MAY;
    }
}


