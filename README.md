<a href="http://cloudslang.io/">
    <img src="https://camo.githubusercontent.com/ece898cfb3a9cc55353e7ab5d9014cc314af0234/687474703a2f2f692e696d6775722e636f6d2f696849353630562e706e67" alt="CloudSlang logo" title="CloudSlang" align="right" height="60"/>
</a>

CloudSlang Plugin for IntelliJ  
================================
  
  
This repository contains an IntelliJ plugin that provides support for [CloudSlang](http://cloudslang.io/) in *IntelliJ IDEA* IDE.  
  
![CloudSlang Plugin demo](/resources/images/plugin-overview.png)

##Table of Contents

1. [Features](#features)  
2. [Installation](#installation)  
3. [Getting Started](#getting Started)
4. Contribution Guidelines
  1. Environment Setup
  2. Run and Debug the Code
  3. Package and Deploy the Plugin
<br />


##Features

* CloudSlang file type support
* Live templates e.g: flow, operation, input, output, step, for, java_action etc.
* Completion support for CloudSlang keywords
* Syntax highlighting
* CloudSlang file validation and error highlighting

##Installation

Install the CloudSlang IntelliJ Plugin using the __Plugin Installation Wizard__ from __File > Settings > Plugins > Install JetBrains Plugin...__.  
In the __Browse JetBrains Plugins__ window, browse __CloudSlang__ to find the plugin.  
Install the plugin by pressing the __Install__ button from the right panel.

> You will have to restart IntelliJ for changes to take effect.


##Getting Started

######CloudSlang file type support

All files with the supported CloudSlang extensions **(.sl, .sl.yaml, .sl.yml, .prop.sl)** will be assigned a CloudSlang icon.

[![CloudSlang File Type](/resources/images/file-type.png)

######Live templates e.g: flow, operation, input, output, step, for, java_action etc.

Live templates let you insert frequently-used or custom code constructs into your source code file quickly, efficiently, and accurately.  
They can contain plain text and variables that enable user input.  
After a template is expanded, variables appear in the editor as __input fields__ whose values can be either filled in by the user or calculated by IntelliJ IDEA automatically.
  
**How to**: To create a flow or operation based on a template, open a CloudSlang file and press **Ctrl + J**. This will display a list of all available live templates. You can navigate up and down inside this list and select the desired template, that will be extended in the editor.

[![CloudSlang File Type](/resources/images/all-live-templates.png)

Also, when you start typing a word that matches the name of a template, that template will appear in the suggestions list.
For example, when typing the word **operation**, the following suggestion will apperr:

[![CloudSlang File Type](/resources/images/live-template-example.png)

After the template is expended in IntellJ, you may be required to provide values for some input fields. You can navigate between input fields by pressing __Tab__. 

[![CloudSlang File Type](/resources/images/template-input-fields.png)

######Completion support for CloudSlang keywords

[![CloudSlang File Type](/resources/images/live-template-example.png)

> In order to differentiate between keywords and live templates in the suggestions list, note that keywords have a small CloudSlang icon at left, while live templates have the **CloudSlang Template** description at right 

######Syntax highlighting

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


