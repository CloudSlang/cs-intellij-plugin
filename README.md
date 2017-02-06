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
 
  
##Features

* CloudSlang file type support (.sl, .sl.yaml, .sl.yml, .prop.sl)
* Live templates (e.g: flow, operation, input, output, step, for, java_action etc.)
* Completion support for CloudSlang keywords
* Syntax highlighting
* CloudSlang file validation and error highlighting

  
##Installation

1. Install the CloudSlang IntelliJ Plugin in IntelliJ using the **Plugin Installation Wizard** from **File** > **Settings** > **Plugins** > **Browse repositories...** and search for: **CloudSlang plugin.** 
3. Click __Apply__ button of the __Settings__ dialog.

> Following the system prompt that appears, restart IntelliJ IDEA to activate the installed plugin, or postpone it, at your choice.


##Getting Started

This is a short tutorial showing how to start developing CloudSlang content.

#####1. Create a new Project 
  
From the **New Project** dialog, select the **CloudSlang** project type.

![CloudSlang File Type](/screenshots/new-project.png)
  
<br/>
#####2. Create a namespace
  
Under the new project, create a structure of directories for items grouping:

![CloudSlang File Type](/screenshots/new-namespace.png)

You can notice the following directory structure under your project:
    
![CloudSlang File Type](/screenshots/new-namespace-structure.png)

<br/>    
#####3. Create a new CloudSlang file 

Under the **base** directory, create a new file with one of the following extensions: **.sl, .sl.yaml, .sl.yml, .prop.sl**  
When a file with one of these extensions is created, a CloudSlang icon is associated to it:

![CloudSlang File Type](/screenshots/file-type.png)

> By default, empty CloudSlang files are marked as invalid. 

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

You can easily modify the flow using CloudSlang keywords support. Whenever you start typing, a suggestion list with CloudSlang keywords will appear:

![CloudSlang File Type](/screenshots/completion-example.png)

To see the complete list of CloudSlang keywords, press **Ctrl + Space**

> In order to differentiate between keywords and live templates in the suggestions list, note that keywords have a small CloudSlang icon at left, while live templates have the **CloudSlang Template** description at right 

<br />
#####5. Use error highlighting to ensure that your flow is correct

Errors are marked with a red underline inside files. Hover over the underlined item to see the error messages:

![CloudSlang File Type](/screenshots/error-highlighting.png)

<br />

If you would like to contribute to the plugin, please read the [Contributing Guideline](/CONTRIBUTING.md) here.


