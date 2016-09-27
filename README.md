<a href="http://cloudslang.io/">
    <img src="https://camo.githubusercontent.com/ece898cfb3a9cc55353e7ab5d9014cc314af0234/687474703a2f2f692e696d6775722e636f6d2f696849353630562e706e67" alt="CloudSlang logo" title="CloudSlang" align="right" height="60"/>
</a>

CloudSlang Plugin for IntelliJ  
================================
  
  <br/>
This repository contains an IntelliJ plugin that provides support for [CloudSlang](http://cloudslang.io/) in *IntelliJ IDEA* IDE.  
  
![CloudSlang Plugin demo](/screenshots/plugin-overview.png)

  
##Table of Contents

1. [Features](#features)  
2. [Installation](#installation)  
3. [Getting Started](#getting-started)
4. [Contribution Guidelines](#contribution-guidelines)
  1. [Environment Setup](#environment-setup)
  2. [Run and Debug the Code](#run-and-debug-the-code)
  3. [Package and Deploy the Plugin](#package-and-deploy-the-plugin)
  
  
##Features

* CloudSlang file type support (.sl, .sl.yaml, .sl.yml, .prop.sl)
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

This is a short tutorial showing how to start developing CloudSlang content.

#####1. Create a new Project 
  
From the **New Project** dialog, select the type of project you desire, for example, **Java Project**

![CloudSlang File Type](/screenshots/new-project.png)
  
<br/>
#####2. Create a namespace
  
Under the new project, create a structure of directories for items grouping:

![CloudSlang File Type](/screenshots/new-namespace.png)

You can notice the following directory structure under your project:
    
![CloudSlang File Type](/screenshots/new-namespace-structure.png)

<br/>    
#####3. Create a new CloudSlang file 

Under the **base** directory, create a new file with one of the following extenssions: **.sl, .sl.yaml, .sl.yml, .prop.sl**  
When a file with one of these extensions is created, a CloudSlang icon is associated to it:

![CloudSlang File Type](/screenshots/file-type.png)

> By default, empty files are marked as invalid. 

<br/>    
#####4. Create a new CloudSlang flow from a live template

Open the __first_flow.sl__ file and place the cursor inside the editor.  
  
You can insert a **flow** template in two ways:  
* Press __Ctrl + J__ to display a list of all available live templates. You can navigate up and down inside this list and select the desired template, that will be extended in the editor.  

![CloudSlang File Type](/screenshots/all-live-templates.png)

* Start typing the _flow_ word and the template will appear in the suggestions list

![CloudSlang File Type](/screenshots/live-template-example.png)

> You can always identify CloudSlang templates in the suggestions list by the **CloudSlang Template** description at right.

After the template is expended in IntellJ, you may be required to provide values for some input fields. You can navigate between input fields by pressing __Tab__. 

![CloudSlang File Type](/screenshots/template-input-fields.png)

<br />
#####5. Use completion support for CloudSlang keywords

You can easily modify the flow using CloudSlang keywords support. Whenever you start typing word, a suggestion list with CloudSlang keywords will appear:

![CloudSlang File Type](/screenshots/completion-example.png)

To see the complete list of CloudSlang keywords, press **Ctrl + Space**

> In order to differentiate between keywords and live templates in the suggestions list, note that keywords have a small CloudSlang icon at left, while live templates have the **CloudSlang Template** description at right 

<br />

##Contribution Guidelines

###Environment Setup

These are the prerequisite steps that you have to follow to successfully set up your development environment:  
  
1. Clone this reposiotry using a Git client.  
  
2. Open IntelliJ and configure IntelliJ Platform SDK as described in the [Intellij SDK DevGuide] (http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/setting_up_environment.html).  
*__Cloning IntelliJ CE edition Source Code is optional.__*  
  
3. In IntelliJ, navigate to __File > New > Project__  
  
4. From the __New Project__ dialog, select __IntelliJ Platform Plugin__ as the type of the project and set the __Project SDK__ to the one configured in __step 2__.  
![plugin](/screenshots/dev/new-plugin.png)
  
5. Click __Next__  
  
6. In the __Project location__ textbox, copy the absolute path of the repository cloned at __step 1__. 
![plugin](/screenshots/dev/open-plugin.png)
  
7. Click __Finish__. The __cloudslang-intellij-plugin__ sources will be imported into your IDE.  
  
8. __cloudslang-intellij-plugin__ depends on YAML plugin, delivered with IntelliJ. In order to be able to compile the code, you have to import the YAML plugin to your SDK classpath. Navigate to __File > Project Structure > SDKs__.  
  
9. Select your IntelliJ SDK from the list of available SDKs.  
  
10. Make sure the __Classpath__ tab is selected. Add a new jar to sdk classpath.  
![plugin](/screenshots/dev/add-to-sdk.png)  

12. Select the following jar and add it to classpath: __IntelliJ_Installation_Dir/plugins/yaml/lib/yaml.jar__  
  
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


