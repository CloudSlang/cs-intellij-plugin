package com.intellij.project.wizard;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.project.module.CloudSlangModuleType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CloudSlangModuleBuilder extends ModuleBuilder {

    @Override
    public void setupRootModel(ModifiableRootModel modifiableRootModel) throws ConfigurationException {
        ContentEntry contentEntry = doAddContentEntry(modifiableRootModel);
        Path libraryPath = Paths.get(getContentEntryPath(), "Content", "Library");
        Path configurationPath = Paths.get(getContentEntryPath(), "Content", "Configuration");

        createDirectories(libraryPath);
        createDirectories(configurationPath);
        VirtualFile content = LocalFileSystem.getInstance().refreshAndFindFileByPath(FileUtil.toSystemIndependentName(libraryPath.getParent().toString()));
        if (content != null) {
            contentEntry.addSourceFolder(content, false);
        }
    }

    private void createDirectories(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //do nothing
            }
        }
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
        return "Create a standard project structure for grouping CloudSlang content.";
    }
}
