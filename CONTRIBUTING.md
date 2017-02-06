##Contribution Guidelines

  1. [Environment Setup](#environment-setup)
  2. [Run and Debug the Code](#run-and-debug-the-code)
  3. [Package and Deploy the Plugin](#package-and-deploy-the-plugin)

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
  
8. CloudSlang IntelliJ Plugin depends on YAML plugin, delivered with IntelliJ. In order to be able to compile the code, you have to import the YAML plugin to your SDK classpath. Navigate to __File > Project Structure > SDKs__.  
  
9. Select your IntelliJ SDK from the list of available SDKs.  
  
10. Make sure the __Classpath__ tab is selected. Add a new jar to sdk classpath.  
![plugin](/screenshots/dev/add-to-sdk.png)  

12. Select the following jar and add it to classpath: __*IntelliJ_Installation_Dir*/plugins/yaml/lib/yaml.jar__  
  
13. CloudSlang IntelliJ Plugin depends on CloudSlang compiler. To be able to compile the code you have to add cloudslang-compiler to classpath. Navigate to __File > Project Structure > Libraries__.  
  
14. Locate the __lib__ directory inside __cloudslang-idea-plugin__ and add it to the classpath.  
**You can add the entire lib folder to the classpath, you don't have to manually add each jar from inside.**
  
Now, you should be able to successfully compile and run the code.
<br />


###Run and Debug the Code

The [Running and Debugging Plugins](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/running_and_debugging_a_plugin.html) section from __IntelliJ SDK DevGuide__ provides all information you need to debug and run the plugin.
<br />


###Package and Deploy the Plugin

Read the [Deploy a  Plugin](http://www.jetbrains.org/intellij/sdk/docs/basics/getting_started/deploying_plugin.html) section from __IntelliJ SDK DevGuide__.  
> You can also install the plugin using the __Plugin installation wizard__ from IntelliJ. Navigate to __File > Settings > Plugins > Install Plugin from disk...__