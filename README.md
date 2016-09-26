<a href="https://aimeos.org/">
    <img src="https://aimeos.org/fileadmin/template/icons/logo.png" alt="CloudSlang logo" title="CloudSlang" align="right" height="60" />
</a>

CloudSlang Plugin for IntelliJ  
================================
  
This plugin allows you to easily develop and run CloudSlang content, using Intellij IDE.  
<br />

###Table of Contents

1. Features
2. Environment Setup 
3. Run and Debug the Code
4. Package and Deploy the Plugin
<br />


###Features

The purpose of this section is to visualize and track the progress of all features planed for developent as part of the IntelliJ Plugin.

| Position | Feature | Developer | Status |
| -------- | ------- | --------- | ------ |
| 1 | Register file type | | Done
| 2 | Syntax/Error highlighting | | Done |
| 3 | Code Completion - flow | Dorinel | In Progress |
| 4 | Code Completion - java/python operation | Dorinel | In Progress |
| 5 | Code Completion - decision | Dorinel | In Progress |
| 6 | Code Completion - description | Dorinel | In Progress |
| 7 | Code Completion - python expression (${}) | Dorinel | In Progress |
| 8 | Running Configuration | | Planned |
| 9 | New executable (operation, flow, decision) template file | | Planned |
| 10 | How to write unit tests? (validate that .yaml files are ignored) | | Planned |
| 11 | How to publish plugin in intellij reposiotry | | Planned |
| 12 | Investigate if we can remove spring from the dependencies | Luci | Planned |
| 13 | Go to class, go to symbol | | Unplanned |
| 14 | Find References (navigation) | | Unplanned |
| 15 | Best practices warnings | | Unplanned |
| 16 | New project structure | | Unplanned |
| 17 | Python syntax/error highlighting (validator) | | Unplanned |
| 18 | Create test file | | Unplanned |
| 19 | SF: generate dependency graph | | Unplanned |  
<br />


###Environment Setup

These are the prerequisite steps that you have to follow to successfully set up your development environment:  
  
1. Clone the reposiotry  
  
2. Open IntelliJ and configure IntelliJ Platform SDK as described in the [Intellij SDK DevGuide] (http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/setting_up_environment.html).  
*__You don't have to clone IntelliJ CE edition Source Code, as mentioned in the documentation above__*  
  
3. In intellij, navigate to __File > New > Project__  
  
4. From the __New Project__ dialog, select __IntelliJ Platform Plugin__ as the type of the project and set the __Project SDK__ to the one configured in __step 2__.  
![alt text](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/img/new_project_wizard.png, "Create New Project Wizard")
  
5. Click __Next__  
  
6. In the __Project location__ textbox, copy the absolute path of the repository cloned at __step 1__. (The absolute path of the __cloudslang-idea-plugin__ directory)  
  
7. Click __Finish__. The __cloudslang-intellij-plugin__ sources will be imported into your IDE.  
  
8. __cloudslang-intellij-plugin__ depends on YAML plugin, delivered with IntelliJ. In order to be able to compile the code, you have to import the YAML plugin to your SDK classpath. Navigate to __File > Project Structure > SDKs__.  
  
9. Select your IntelliJ SDK from the list of available SDKs.  
  
10. Make sure the __Classpath__ tab is selected. Click on the green *+* button to add a new jar to sdk classpath.  
  
12. Select the following jar and add it to classpath: __<IntelliJ_Installation_Dir>/plugins/yaml/lib/yaml.jar__  
  
13. __cloudslang-intellij-plugin__ depends on CloudSlang compiler. To be able to compile the code you have to add cloudslang-compiler to classpath. Navigate to __File > Project Structure > Libraries__.  
  
14. Click the green *+* sign to add a new project library.  
  
15. Locate and select the __lib__ directory inside __cloudslang-idea-plugin__.  
  
Now, you should be able to successfully compile and run the code.
<br />


###Run and Debug the Code

The [Running and Debugging Plugins](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/running_and_debugging_a_plugin.html) section from __IntelliJ SDK DevGuide__ provides all information you need to debug and run the plugin.
<br />


###Package and Deploy the Plugin

Read [Deploy a  Plugin](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/deploying_plugin.html) section from __IntelliJ SDK DevGuide__.  
> You can also install the plugin using the __Plugin installation wizard__ from IntelliJ. Navigate to __File > Settings > Plugins > Install Plugin from disk...__


