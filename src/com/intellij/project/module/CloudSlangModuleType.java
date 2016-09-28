package com.intellij.project.module;

import com.intellij.lang.cloudslang.CloudSlangIcons;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.project.wizard.CloudSlangModuleBuilder;
import javax.swing.Icon;
import org.jetbrains.annotations.NotNull;


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
