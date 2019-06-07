# ![Magis](https://res.cloudinary.com/ianspryn/image/upload/v1/Magis/magis-color-small.png)
A Computer Aided Instruction Program that teaches Introduction to Java based on Big Java textbook by Cay Horstmann

## Prerequisites
Install the latest [Java JRE](https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

## Getting Started
Note these instructions apply to Intellij.

1. Download the repository.
2. Open it in Intellij

**At this point, you may either click the play button to build the app within Intellij, or you may proceed with the following steps to build the JAR.**

3. Go to File > Project Structure (or Ctrl + Alt + Shift + S)
4. Under Artifacts, add a JAR from modules with dependeices. The Output should include the compile output of the project along with all of the dependecy JARs extracted
5. Set the Main Class at the bottom to `com.magis.app.Main`
6. Click OK
7. Go to Build > Build Artifacts > Magis and Build. The JAR will now build.
8. Navigate to `./out/artifacts/Magis`

The new newly built JAR will live in there. When you execute the program, a second file called students.xml will appear. This file MUST be in the same directory as Magis.jar, otherwise a new xml file will be created.

# Features

### Support for multiple users

![Sign in window](https://i.imgur.com/4qmmv8d.png)

![Sign up window](https://i.imgur.com/crze5zD.png)


### Rich customization and settings

![Settings Page](https://i.imgur.com/baYU76f.png)


### Home Page

![Home Page](https://i.imgur.com/IRRJZ36.png)


### Dark Mode and Theming

![Dark Mode Home Page](https://i.imgur.com/5d9yMRp.png)

### Lesson Page

![Lesson Page](https://i.imgur.com/5V2ky5M.png)

### Quizzes and Tests
In additon to multiple choice questions, Magis also supports fill-in-the-blank questions. It will grade the answer appropriately and provide feedback by diffing the user's answer against the correct answer.

![Written question #1](https://i.imgur.com/DuGfYb1.png)
![Written question #1](https://i.imgur.com/V9ZbZ6l.png)
![Written question #1](https://i.imgur.com/NbKYYPZ.png)

There are sometimes many ways to correctly fill in the blank, such as `int myVar=1;` vs. `int myVar = 1`. For scenarios like these, Magis will still give full credit.

### Homepage contains intelligent suggestions to assist the user through their learning

![Ready to take a quiz](https://i.imgur.com/XZW1wiF.png)

![Not ready to take a quiz](https://i.imgur.com/2OKBjZU.png)

### Statistics and Insights Page

Overall insights
![Insights Page](https://i.imgur.com/B6YgCkV.png)

Chapter-specific insights
![Chapter-specific insights](https://i.imgur.com/m03T4mJ.png)

Review previous quiz and test attempts
![Previous quiz](https://i.imgur.com/DQtQHuO.png)


# Known Issues
Sometimes (have yet to find a way to reproduce the bug) when loading a quiz/test page, the Page Content background remains white even with darkmode activated.
 - 2 possible reasons for this bug:
   - The theming isn't taking effect or being updated for the background of Page Content on a quiz/test
   - The Stackpane that acts as the background (so that the background can be themed) isn't being loaded at all into the GUI
 - A simple fix is to restart Magis and load the quiz/test again


