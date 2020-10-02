# User Guide

Hello there! 
```
    ____        _        
   |  _ \ _   _| | _____ 
   | | | | | | | |/ / _ \
   | |_| | |_| |   <  __/
   |____/ \__,_|_|\_\___|
```
This guide will show you how to use Duke. Duke is a task management program
that helps the User keep track of his/her tasks.

## Setting up

* Just ensure that the ip.jar file is in a folder.
* Use the Folder location to execute the ip.jar program in Powershell.
* For example, a typical command could look like this:
````
cd C:\Users\anon\Desktop\java_jar> java - jar ip.jar
````

## Duke's Functions
### A. Adding a Task
##### Simply key in a command containing the following keywords:
````todo````, ````deadline````,````event````
* A Todo Task should preferably be inputted in the following format:
````todo something````
* This will result in Duke recording your Task. Duke will respond with a message like this:
````
____________________________________________________________
    Got it. I've added this task: 
      [T][✘] homework
    Now you have 1 tasks in the list.
____________________________________________________________
````
##### For Event and Deadline Tasks, the format is a little bit more specific

* These Task types should be inputted in the following manner:
````deadline CS2113T finals /by dd/MM/yyyy additional text````
* Duke will also record down this Task and provide a message to show that the Task has been added.
````
____________________________________________________________
    Got it. I've added this task: 
      [D][✘] cs2113t finals (by 30/11/2020  additional text)
    Now you have 2 tasks in the list.
____________________________________________________________
````
* There are no strict rules as to what you can include in place of
_"by"_ and _"additional text"_.

##### The List and Find Functions

* Type ````list```` in your input to get a list of all the tasks stored in Duke
* The command should show something like this:
````
____________________________________________________________
1.[T][✘] homework
2.[D][✘] cs2113t finals (by 2020/30/11   additional text)
____________________________________________________________
````
* Note that the date format is different in the list!
* If you wish to find a particular Task, type in ````find stuff````, something like this:
````
Enter an input!
find finals
____________________________________________________________
1.[D][✘] cs2113t finals (by 30/11/2020   additional text)
____________________________________________________________
````

##### Marking Tasks as done
* If you have completed a Task, you might wish to indicate as such in the list.
* Simply evoke the list function and type in ````done index_of_Task_completed````, like this:
````
Enter an input!
done 2
____________________________________________________________
Nice! I've marked this task as done:
  [D][✓] cs2113t finals (by 30/11/2020   additional text)
____________________________________________________________
````

##### Deleting Tasks

* You might wish to remove Tasks from your list. Just as with
````done````, simply type in ````delete```` followed by the index 
of the Task you want to delete
* Like this:
````
delete 1
____________________________________________________________
Noted. I've removed this task:
  [T][✘] homework
Now you have 1 tasks in the list.
____________________________________________________________

Enter an input!
list
____________________________________________________________
1.[D][✓] cs2113t finals (by 2020/30/11     additional text)
____________________________________________________________
Enter an input!
````

##### Exiting Duke
* Type in ````bye```` to leave the program!