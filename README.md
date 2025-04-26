# Disclaimer

**This one is something from my good old days**.
The code style even has prefixes - they were very useful with the SVN from the code review prospective...

Of course, I would reconsider a lot of things done here :)

This is an old style Spring Boot Web application with external Tomcat and Angular UI.

I.e. jar should be deployed to the already running Tomcat - properties for the application will be taken from the servlet container context
entry -
this approach was popular in the old days, when admins wanted to have more control over deployable artifact

# Requirements

- java 8
- Tomcat server where to run an application