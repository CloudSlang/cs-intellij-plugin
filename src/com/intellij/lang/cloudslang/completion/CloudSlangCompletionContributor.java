package com.intellij.lang.cloudslang.completion;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CloudSlangCompletionContributor extends CompletionContributor {

    public static final String TEMPLATES = "templates";

    public CloudSlangCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement().withLanguage(YAMLLanguage.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        addSlangKeywords(resultSet);

                        try {
                            addTemplates(resultSet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        addYamlKeywordsUsedByCloudSlang(resultSet);

                    }
                }
        );
    }

    private void addSlangKeywords(CompletionResultSet resultSet) {
        for (Field field : SlangTextualKeys.class.getFields()) {
            try {
                String value = field.get(null).toString();
                LookupElementBuilder builder = LookupElementBuilder.create(value + ":")
                        .withPresentableText(value);
                resultSet.addElement(builder);
            } catch (IllegalAccessException ignored) {
            }
        }
    }


    private void addTemplates(CompletionResultSet resultSet) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader != null) {
            Enumeration<URL> en = classLoader.getResources(TEMPLATES);
            if (en.hasMoreElements()) {
                URL url = en.nextElement();
                JarURLConnection urlcon = (JarURLConnection) (url.openConnection());
                try (JarFile jar = urlcon.getJarFile()) {
                    addTemplatesFromJar(resultSet, classLoader, jar);
                }
            }
        }
    }

    private void addTemplatesFromJar(CompletionResultSet resultSet, ClassLoader classLoader, JarFile jar) throws IOException {
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().startsWith(TEMPLATES)) {
                InputStream inputStream = classLoader.getResourceAsStream(entry.getName());
                if (inputStream != null) {
                    String template = getTemplateString(inputStream);
                    addCompletion(resultSet, entry.getName().substring(TEMPLATES.length() + 1), template);
                }
            }
        }
    }

    private void addCompletion(CompletionResultSet resultSet, String name, String template) {
        LookupElementBuilder templateBuilder = LookupElementBuilder.create(template)
                .withPresentableText(name)
                /*.withInsertHandler((context, item) -> {
                                            final Editor topLevelEditor = InjectedLanguageUtil.getTopLevelEditor(context.getEditor());
                                            int offset = topLevelEditor.getCaretModel().getOffset();
                                            topLevelEditor.getDocument().replaceString(offset, offset + template.length(), template);
                                        })*/;
        resultSet.addElement(templateBuilder);
    }

    private String getTemplateString(InputStream file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line)
                        .append("\n");
            }
        }
        return stringBuilder.toString();
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
