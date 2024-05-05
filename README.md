How to create a new repository and push the code
========================================================

STEP-1:
=======
Login to https://github.com/ and create your repository ( Note the name of the repositor must be the same as your project name on your local machine).

Now Go to your code base (project) on your local machine and run below commands:

STEP-2:
=======
D:\IntelliJProjects\autiscareBE>git status
fatal: not a git repository (or any of the parent directories): .git

STEP-3:
=======
D:\IntelliJProjects\autiscareBE>git init
Initialized empty Git repository in D:/IntelliJProjects/autiscareBE/.git/

STEP-4:
=======
D:\IntelliJProjects\autiscareBE>git remote add origin git@github.com:divyeshstudy/autiscareBE.git

STEP-5:
=======
D:\IntelliJProjects\autiscareBE>git add .

STEP-6:
=======
D:\IntelliJProjects\autiscareBE>git status
On branch master

No commits yet

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        .idea/
        .mvn/
        HELP.md
        README.md
        autiscareBE.iml
        mvnw
        mvnw.cmd
        pom.xml
        src/
        target/

nothing added to commit but untracked files present (use "git add" to track)

STEP-7:
=======
D:\IntelliJProjects\autiscareBE>git commit -m "Initial commit"

STEP-8:
=======
D:\IntelliJProjects\autiscareBE>git push -u origin master
git@github.com: Permission denied (publickey).
fatal: Could not read from remote repository.

Please make sure you have the correct access rights
and the repository exists.

NOTE:
=======
If someone face this issue then have to follow the below steps:

First you'll want to cd into your .ssh directory. Open up the terminal and run:
cd ~/.ssh && ssh-keygen

Or you can refer this link to create the ssh key: https://stackoverflow.com/questions/2643502/git-how-to-solve-permission-denied-publickey-error-when-using-git

RETRY STEP-8:
=======
D:\IntelliJProjects\autiscareBE>git push -u origin master
Enumerating objects: 252, done.
Counting objects: 100% (252/252), done.

STEP-9: Go to your repository in https://github.com/ and refresh the page. You will find the pushed code
