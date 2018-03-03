<a href="http://cloudslang.io/">
    <img src="https://camo.githubusercontent.com/ece898cfb3a9cc55353e7ab5d9014cc314af0234/687474703a2f2f692e696d6775722e636f6d2f696849353630562e706e67" alt="CloudSlang logo" title="CloudSlang" align="right" height="60"/>
</a>

Contribution Guidelines
=======================

## Table of Contents

  1. [Environment Setup](#environment-setup)
  2. [Run and Debug the Code](#run-and-debug-the-code)
  3. [Package and Deploy the Plugin](#package-and-deploy-the-plugin)

<a name="environment-setup"/>

## Environment Setup

The plugin is built using [Gradle v4.6](https://gradle.org/releases)

**Requirements:** Java 8 JDK installed and set as Environment Variable.
   
   ***To check that you have Java installed, run java -version:***
    
    $ java -version
    java version "1.8.0_60"  
   
There is no need to download any other prerequisites as the project will build itself using the Gradle Wrapper.

    For linux:
        ./gradlew
    
    For Windows:
        gradlew.bat
        
After the project has finished downloading and setting itself up, you can start testing it.

For manual gradle installation, see the guide [here](https://gradle.org/install#manually)
        
##### Note: Using gradle behind a proxy. 

Run the gradle wrapper using this command (replace host and port):

    gradlew *taskName -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=3128 -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=3129

    *taskName = runIde, others.

<a name="run-and-debug-the-code"/> 

## Run and Debug the Code

To run and debug the plugin:

    gradlew runIde
    
This task will download and run **Intellij IDEA 2016.3.4 IC**, which will open in a new window, with the plugin loaded. 

After debugging, close the window and the task will end. 

<a name="package-and-deploy-the-plugin"/> 

## Package and Deploy the Plugin

Read the [Deploy a  Plugin](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/deploying_plugin.html) section from __IntelliJ SDK DevGuide__.  
> You can also install the plugin using the __Plugin installation wizard__ from IntelliJ. Navigate to __File > Settings > Plugins > Install Plugin from disk...__