csci_201_final
==============

HOW TO USE GIT WITH ECLIPSE

1. Create a brand new project in your workspace with default settings
2. Open your Git terminal and cd into your newly made project folder. (When you ls you should just see 'src' and 'bin' folders)
3. Now clone the repo
4. Right click on your project folder in eclipse and select Refresh
5. Right click on the csci_201_final folder that just popped up, arrow over Build Path and select Use as Source folder.
6. Now you're working out of your cloned repo and everything will be tracked. If you pull give eclipse a second and it should update the code right in front of you, but if not just do a manual refresh.

To get images working

1. Right click on the images folder under csci_201_final in eclipse and select copy.
2. Right click on the src folder right above it and select paste.


We should change the image file paths in the code so that we don't have to do that ^

GIT BRANCHING

  Create new branch:
  
    git checkout -b "branchname"
    
      e.g. git checkout -b "db_user_login" !!! please preface with your initials !!!
      
  Switching branches:
  
    git checkout branchname
    
      e.g. git checkout db_user_login
      
           git checkout master
           
  Pushing:
  
    git push origin branchname
    
      e.g. git push origin db_user_login
      
  Pulling:
  
    First switch to desired branch then:
    
    git pull
    
    e.g. git checkout master
    
         git pull


MYSQL SET UP

1. Follow lab 9 instruction for the most part. Keep the password blank
2. Make sure your SQL service is running
2. When you create your schema name it 'group_db' and set it as the default
3. In the workbench go to File->Open SQL Script and select migration.sql from the repo
4. Click lightning bolt execute button

