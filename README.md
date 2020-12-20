# Clean Code Contest 2020

Welcome to clean code contest 2020! This is template for java project.
A default project is already created with a preset docker environment in order to execute CI.
Below software stacks are prepared in that docker image:
- maven :3.6.0
- JDK: jdk-11
- junit: 4.8.1
- pmd


### CI/CD
Gitlab CI automation is already set for this repository, so **any commit** would trigger prebuilt pipleines automatically, and you can check those jobs' status from gitlab page.
- Build
- Unit test/Integration test
- Quality check

>Please do NOT change the .gitlab-ci.yml file.

>**For university students:**
>The access to gitlab is not available for non-NOKIA users. The results will be sent in other ways.

### Local develop:
You can verify it with the below command before you push your codes to repository on your local PC.
You have to install maven and JDK beforehand in your local envrionment. Your can also use the virtual box image provided by contest committee.

compile          phase:   `mvn compile `  
unit test        phase:   `mvn test`  
integration-test phase:   `mvn integration-test`


### Virtual Machine Based Environment
There is also a prebuilt virtual machine created (Ubuntu 20.04.1 LTS based) that prepares almost everything for you. You can download it from the link in mail of contest committee.
